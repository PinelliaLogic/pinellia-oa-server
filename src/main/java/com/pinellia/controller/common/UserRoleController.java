package com.pinellia.controller.common;

import com.pinellia.entity.OaRole;
import com.pinellia.service.OaRoleService;
import com.pinellia.service.OaUserService;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户角色分配
 **/
@RestController
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    private OaRoleService oaRoleService;

    //用户角色分配
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('user:part')")
    @PostMapping("/{userId}")
    @Transactional
    public R addRoleByUser(@PathVariable Long userId, @RequestBody List<Long> roles) {
        if (roles == null || userId == null) {
            return R.error(500, "分配失败！");
        }
        //删除原来所有的角色
        oaRoleService.deleteRoleByUser(userId);

        //分配新的角色
        for (Long role : roles) {
            oaRoleService.addRoleByUser(userId, role);
        }

        return R.success("分配角色成功!");
    }

    //获取用户已有角色
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('user:part')")
    @GetMapping("/{id}")
    public R getRoleByUserId(@PathVariable Long id) {
        List<OaRole> roleList = oaRoleService.getRoleList(id);
        return R.success("", roleList);
    }

}
