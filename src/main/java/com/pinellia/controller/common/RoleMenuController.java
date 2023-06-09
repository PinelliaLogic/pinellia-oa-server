package com.pinellia.controller.common;

import com.pinellia.entity.OaMenu;
import com.pinellia.service.OaRoleService;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 给角色分配权限
 **/
@RequestMapping("/roleMenu")
@RestController
public class RoleMenuController {

    @Autowired
    private OaRoleService oaRoleService;

    /**
     * 角色权限分配
     * @param roleId
     * @param: menuList
     * @return com.pinellia.util.R
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('role:part')")
    @PostMapping("/{roleId}")
    public R addMenuByRole(@PathVariable Long roleId, @RequestBody List<OaMenu> menuList) {
        if (menuList == null || roleId == null) {
            return R.error(500, "分配失败！");
        }
        //删除原来的菜单
        oaRoleService.deleteMenuByRoleId(roleId);

        //添加角色菜单权限
        for (OaMenu menu : menuList) {
            oaRoleService.addMenuByRole(roleId, menu.getId());
        }
        return R.success("分配菜单成功！");
    }

    /**
     * 获取角色已有菜单
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('role:part')")
    @GetMapping("/{id}")
    public R getMenuByRoleId(@PathVariable Long id) {
        List<OaMenu> menuList = oaRoleService.getMenuByRoleId(id);
        return R.success("", menuList);
    }

}
