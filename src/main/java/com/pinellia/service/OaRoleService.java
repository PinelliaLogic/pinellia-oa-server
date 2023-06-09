package com.pinellia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.OaMenu;
import com.pinellia.entity.OaRole;

import java.util.List;

/**
 * (OaRole)表服务接口
 *
 * @author pinellia
 * @since 2023-03-20 15:22:10
 */
public interface OaRoleService extends IService<OaRole> {

    List<OaRole> getRoleList(Long id);

    public boolean deleteMenuByRoleId(Long roleId);

    boolean addMenuByRole(Long roleId, Long menuId);

    public boolean addRoleByUser(Long userId, Long roleId);

    public boolean deleteRoleByUser(Long userId);

    List<OaMenu> getMenuByRoleId(Long id);

}

