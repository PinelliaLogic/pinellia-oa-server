package com.pinellia.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.entity.OaDepartment;
import com.pinellia.entity.OaRole;
import com.pinellia.entity.OaUser;
import com.pinellia.service.OaDepartmentService;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static cn.hutool.core.date.DateTime.now;

/**
 * (OaDepartment)表控制层
 */
@RestController
@RequestMapping("/department")
public class OaDepartmentController {

    @Autowired
    private OaDepartmentService oaDepartmentService;

    /**
     * 添加
     * @param oaDepartment
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('department:add')")
    @PostMapping
    public R add(@RequestBody OaDepartment oaDepartment) {
        if (oaDepartment == null) {
            return R.error(500, "添加失败!");
        }
        //设置创建时间
        oaDepartment.setCreateTime(now());

        oaDepartmentService.save(oaDepartment);
        return R.success("成功添加!");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('department:delete')")
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable Long id) {
        if (id == null) {
            return R.error(500, "删除失败!");
        }
        OaDepartment department = oaDepartmentService.getById(id);
        if (department.getPeopleNum() != 0) {
            return R.error(500, "部门人数不为0，删除失败!");
        }
        oaDepartmentService.removeById(id);
        return R.success("删除成功");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     **/
    @Transactional
    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('menu:delete')")
    public R delete(@RequestBody Long[] ids) {
        if (ids == null) {
            return R.error(500, "批量删除失败!");
        }
        for (Long id : ids) {
            OaDepartment department = oaDepartmentService.getById(id);
            //判断部门 人数是否为0
            if (department.getPeopleNum() == 0) {
                oaDepartmentService.removeById(id);
            }
        }
        return R.success("批量删除成功!");
    }

    /**
     * 修改
     * @param department
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('department:update')")
    @PutMapping
    public R update(@RequestBody OaDepartment department) {
        if (department == null) {
            return R.error(500, "修改失败");
        }
        //设置修改时间
        UpdateWrapper<OaDepartment> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", department.getId()).set("update_time", now());
        oaDepartmentService.update(wrapper);

        boolean b = oaDepartmentService.updateById(department);
        if (!b) {
            return R.error(500, "修改失败");
        }
        return R.success("修改成功");
    }

    /**
     * 分页
     * @param pageCount
     * @param: pageSize
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('department:list')")
    @GetMapping
    public R page(
            @RequestParam(defaultValue = "1") Integer pageCount,
            @RequestParam(defaultValue = "15") Integer pageSize
    ) {
        //更新部门人数
        oaDepartmentService.updateCount();

        IPage<OaDepartment> page = oaDepartmentService.page(new Page<>(pageCount, pageSize), null);
        return R.success("", page);
    }

    /**
     * 根据部门ID查询该部门的员工,分页
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('department:list', 'department:query')")
    @GetMapping("/{id}")
    public R getUser(@PathVariable Long id,
                     @RequestParam(defaultValue = "1") Integer pageCount,
                     @RequestParam(defaultValue = "15") Integer pageSize,
                     @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<OaUser> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaUser::getName, search);        //通过姓名查询
        }

        IPage<OaUser> userList = oaDepartmentService.getUserByDepartmentIdPage(id, new Page<>(pageCount, pageSize), wrapper);
        return R.success("", userList);
    }

    /**
     * 从部门中移除员工
     * @param id
     * @return com.pinellia.util.R
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('department:list')")
    @PostMapping("/remove/{id}")
    public R removeUser(@PathVariable Long id) {
        boolean b = oaDepartmentService.removeUser(id);
        if (b) {
            return R.success("移除成功!");
        }
        return R.error(500, "移除失败！");
    }

    /**
     * 分配员工
     * @param uid
     * @param: did
     * @return com.pinellia.util.R
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('department:list')")
    @PostMapping("/divide/{uid}/{did}")
    public R divideDepartment(@PathVariable Long uid, @PathVariable Long did) {
        if (uid != null && did != null) {
            boolean b = oaDepartmentService.divideUser(did, uid);
            if (b) {
                return R.success("分配成功!");
            }
        }
        return R.error(500, "操作失败！");
    }
}

