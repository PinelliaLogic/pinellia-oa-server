package com.pinellia.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.entity.*;
import com.pinellia.entity.vo.OaRecoverVo;
import com.pinellia.service.*;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (OaRecover)表控制层
 */
@RestController
@RequestMapping("/recover")
public class OaRecoverController {

    @Autowired
    private OaRecoverService oaRecoverService;

    @Autowired
    private OaRoleService oaRoleService;

    @Autowired
    private OaDepartmentService oaDepartmentService;

    @Autowired
    private OaJobService oaJobService;

    @Autowired
    private OaWorkService oaWorkService;

    /**
     * 恢复信息
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('recover:add')")
    @PostMapping("/{id}")
    @Transactional
    public R recover(@PathVariable Long id) {
        boolean b = oaRecoverService.updateDelete(id);
        if (b) {
            //恢复到用户表
            boolean recover = oaRecoverService.recover(id);
            if (recover) {
                //从回收站删除
                oaRecoverService.removeById(id);
            }
            return R.success("数据恢复成功！");
        }
        return R.error(500, "恢复失败！");
    }

    /**
     * 分页
     * @param pageCount
     * @param: pageSize
     * @param: search
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('recover:list', 'recover:query')")
    @GetMapping
    public R findPage(@RequestParam(defaultValue = "1") Integer pageCount,
                      @RequestParam(defaultValue = "15") Integer pageSize,
                      @RequestParam(defaultValue = "") String search) {

        LambdaQueryWrapper<OaRecover> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaRecover::getName, search);        //通过姓名查询
        }
        Page<OaRecover> pageResult = oaRecoverService.page(new Page<>(pageCount, pageSize), wrapper);
        //非空判断，不然下面获取部门方法报错
        List<OaRecover> userList = pageResult.getRecords();
        for (OaRecover user : userList) {
            //获取角色权限
            List<OaRole> roleList = oaRoleService.getRoleList(user.getId());
            user.setRoleList(roleList);
            //获取岗位
            List<OaJob> userJob = oaRecoverService.getJobByUserId(user.getId());
            user.setJobList(userJob);
            //获取部门
            OaDepartment userDepartment = oaRecoverService.getDepartmentByUserId(user.getId());
            user.setDepartment(userDepartment.getDepartmentName());
        }
        //封装分页信息
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("userList",userList);
        resultMap.put("total",pageResult.getTotal());

        return R.success("", resultMap);
    }

    /**
     * 彻底删除用户
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('recover:update')")
    @DeleteMapping("/{id}")
    public R deleteInfo(@PathVariable Long id) {
        boolean b = oaRecoverService.removeById(id);
        if (b) {
            //部门中删除该用户
            oaDepartmentService.deleteUserDepartmentByUserId(id);
            //删除对应的角色
            oaRoleService.deleteRoleByUser(id);
            //删除对用的岗位
            oaJobService.deleteUserJobByUserId(id);
            //删除对应的工作信息
            oaWorkService.removeId(id);
            return R.success("彻底删除成功!");
        }
        return R.error(500, "删除失败!");
    }
}

