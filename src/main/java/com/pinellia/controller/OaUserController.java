package com.pinellia.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.entity.OaDepartment;
import com.pinellia.entity.OaJob;
import com.pinellia.entity.OaRole;
import com.pinellia.entity.OaUser;
import com.pinellia.service.*;
import com.pinellia.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static cn.hutool.core.date.DateTime.now;

/**
 * (OaUser)表控制层
 */
@RestController
@RequestMapping("/user")
public class OaUserController {

    @Autowired
    private OaUserService oaUserService;

    @Autowired
    private OaRecoverService oaRecoverService;

    @Autowired
    private OaRoleService oaRoleService;

    @Autowired
    private OaJobService oaJobService;

    @Autowired
    private OaDepartmentService oaDepartmentService;

    public static final String pattern = "^\\d{17}[0-9X]$"; // 正则表达式

    /**
     * 添加员工
     *
     * @param user
     * @return
     **/
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('user:add')")
    @PostMapping
    @Transactional
    public R save(@RequestBody OaUser user) throws ParseException {
        //查询用户名是否重复
        List<String> list = oaUserService.getAllUsername();
        for (String s : list) {
            if (s.equals(user.getUsername())) {
                return R.error(500, "用户名已存在");
            }
        }
        boolean matches = Pattern.matches(pattern, user.getIdCard());
        if (!matches) {
            return R.error(500, "身份证号码格式有误");
        }
        //验证身份证号码生日，不能大于当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date birthday = sdf.parse(user.getIdCard().substring(6, 14));
        if (user.getIdCard().length() != 18 || birthday.getTime() > now().getTime()) {
            return R.error(500, "身份证号码格式有误");
        } else if (user.getPhone().length() != 11) {
            return R.error(500, "手机号码格式有误");
        }

        String idCard = user.getIdCard();
        //设置默认密码为身份证后六位
        String p = idCard.substring(12);
        //加密后存到数据库
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(p);

        user.setPassword(encode);

        //设置出生日期
        String birth = idCard.substring(7, 14);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = formatter.parse(birth);
        user.setBirthday(date);
        //记录创建的时间
        user.setCreateTime(now());
        //添加
        oaUserService.save(user);

        //设置默认角色
        oaUserService.divideRole(user.getId());
        //设置默认部门
        oaUserService.divideDepartment(user.getId());
        //设置工作分配情况
        oaUserService.divideJob(user.getId());

        return R.success("添加成功");
    }

    /**
     * 删除员工
     *
     * @param id
     * @return
     **/
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public R deleteUser(@PathVariable Long id) {
        if (id == null) {
            return R.error(500, "删除失败！");
        }
        //修改为已删除状态
        oaUserService.deleteUser(id);
        //放入回收站
        oaRecoverService.recoverInfo(id);
        //从员工表中删除
        oaUserService.removeById(id);

        return R.success("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     **/
    @Transactional
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('user:delete')")
    public R delete(@RequestBody List<Long> ids){
        if (ids.size() <= 0) {
            return R.error(500, "删除失败");
        }
        for (Long id : ids) {
            //修改为已删除状态
            oaUserService.deleteUser(id);
            //放入回收站
            oaRecoverService.recoverInfo(id);
        }
        //从员工表中删除
        oaUserService.removeByIds(ids);
        return R.success("批量删除成功!");
    }

    /**
     * 修改信息
     *
     * @param user
     * @return
     **/
    @PreAuthorize("hasAuthority('user:update') OR hasAnyRole('ROLE_ADMIN')")
    @PutMapping
    public R updateById(@RequestBody OaUser user) {
        if (user != null) {
            oaUserService.updateById(user);
            //设置修改时间
            UpdateWrapper<OaUser> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", user.getId()).set("update_time", now());
            oaUserService.update(null, wrapper);
            return R.success("修改成功");
        }
        return R.error(500, "修改失败");
    }

    /**
     * 分页查询
     *
     * @param pageCount, pageSize, search
     * @return
     **/
    @PreAuthorize("hasAuthority('user:list') OR hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public R findPage(@RequestParam(defaultValue = "1") Integer pageCount,
                      @RequestParam(defaultValue = "15") Integer pageSize,
                      @RequestParam(defaultValue = "") String search) {
        //更新年龄
        oaUserService.updateAge();

        LambdaQueryWrapper<OaUser> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaUser::getName, search);        //通过姓名查询
        }
        Page<OaUser> pageResult = oaUserService.page(new Page<>(pageCount, pageSize), wrapper);
        List<OaUser> userList = pageResult.getRecords();
        for (OaUser user : userList) {
            //获取角色权限
            List<OaRole> roleList = oaRoleService.getRoleList(user.getId());
            user.setRoleList(roleList);
            //获取岗位
            List<OaJob> userJob = oaJobService.getUserJob(user.getId());
            user.setJobList(userJob);
            //获取部门
            OaDepartment userDepartment = oaDepartmentService.getUserDepartment(user.getId());
            user.setDepartment(userDepartment.getDepartmentName());
        }
        //封装分页信息
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("userList",userList);
        resultMap.put("total",pageResult.getTotal());

        return R.success("", resultMap);
    }

    /**
     * 启用、禁用功能
     *
     * @param id
     * @return
     **/
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('user:add')")
    @PostMapping("/status/{id}")
    public R updateForbidden(@PathVariable Long id) {
        OaUser user = oaUserService.getById(id);
        if (user == null) {
            return R.error(500, "用户不存在！");
        }

        //修改位启用0
        if (Objects.equals(user.getStatus(), "1")) {
            UpdateWrapper<OaUser> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", id).set("status", "0");
            oaUserService.update(null, wrapper);
            return R.success("启用成功!");
        }
        //修改为禁用1
        UpdateWrapper<OaUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("status", "1");
        oaUserService.update(null, wrapper);
        return R.success("禁用成功!");
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     **/
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('user:reset', 'user:query')")
    @PostMapping("/{id}")
    public R resetPassword(@PathVariable Long id) {
        OaUser user = oaUserService.getById(id);
        if (user == null) {
            return R.error(500, "不存在该用户!");
        }

        String idCard = user.getIdCard();
        //截取身份证后六位
        String p = idCard.substring(12);
        //加密后存到数据库
        String encode = new BCryptPasswordEncoder().encode(p);
        //设置密码
        UpdateWrapper<OaUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("password", encode);
        oaUserService.update(null, wrapper);

        return R.success("重置成功");
    }

}
