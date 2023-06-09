package com.pinellia.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.entity.OaResources;
import com.pinellia.entity.OaRole;
import com.pinellia.service.OaRoleService;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.date.DateTime.now;


/**
 * (OaRole)表控制层
 */
@RestController
@RequestMapping("/role")
public class OaRoleController {

    @Autowired
    private OaRoleService oaRoleService;

    /**
     * 添加
     * @param oaRole
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('role:add')")
    @PostMapping
    public R add(@RequestBody OaRole oaRole) {
        if (oaRole == null) {
            return R.error(500, "输入字段不为空!");
        }
        //设置创建时间
        oaRole.setCreateTime(now());
        oaRoleService.save(oaRole);
        return R.success("成功添加");
    }

    //查询所有角色
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('role:list')")
    @GetMapping("/getAll")
    public R getRoleAll() {
        List<OaRole> list = oaRoleService.list();
        return R.success("", list);
    }

    /**
     * 删除
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('role:delete')")
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable Long id) {
        if (id == null) {
            return R.error(500, "删除失败");
        }
        oaRoleService.removeById(id);
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('role:delete')")
    public R delete(@RequestBody Long[] ids) {
        boolean b = oaRoleService.removeByIds(Arrays.asList(ids));
        if (b) {
            return R.success("批量删除成功!");
        }
        return R.error(500, "批量删除失败!");
    }

    /**
     * 修改
     * @param role
     * @return
     **/
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('role:update')")
    @PutMapping
    public R updateById(@RequestBody OaRole role) {
        if (role == null) {
            return R.error(500, "修改失败");
        }
        boolean b = oaRoleService.updateById(role);
        if (b) {
            //设置修改时间
            UpdateWrapper<OaRole> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", role.getId()).set("update_time", now());
            oaRoleService.update(wrapper);
            return R.success("修改成功");
        }
        return R.error(500, "修改失败");
    }

    /**
     * 分页
     * @param pageCount
     * @param: pageSize
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('role:list')")
    @GetMapping
    public R page(
            @RequestParam(defaultValue = "1") Integer pageCount,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        IPage<OaRole> page = oaRoleService.page(new Page<>(pageCount, pageSize), null);
        return R.success("", page);
    }

}

