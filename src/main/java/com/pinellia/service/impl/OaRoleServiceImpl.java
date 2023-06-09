package com.pinellia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.OaRoleDao;
import com.pinellia.entity.OaMenu;
import com.pinellia.entity.OaRole;
import com.pinellia.service.OaRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (OaRole)表服务实现类
 *
 * @author pinellia
 * @since 2023-03-20 15:22:10
 */
@Service
public class OaRoleServiceImpl extends ServiceImpl<OaRoleDao, OaRole> implements OaRoleService {

    @Autowired
    private OaRoleDao oaRoleDao;

    @Override
    public List<OaRole> getRoleList(Long id) {
        return oaRoleDao.getRoleList(id);
    }

    @Override
    public boolean deleteMenuByRoleId(Long roleId) {
        return oaRoleDao.deleteMenuByRoleId(roleId);
    }

    @Override
    public boolean addMenuByRole(Long roleId, Long menuId) {
        return oaRoleDao.addMenuByRole(roleId, menuId);
    }

    @Override
    public boolean addRoleByUser(Long userId, Long roleId) {
        return oaRoleDao.addRoleByUser(userId, roleId);
    }

    @Override
    public boolean deleteRoleByUser(Long userId) {
        return oaRoleDao.deleteRoleByUser(userId);
    }

    @Override
    public List<OaMenu> getMenuByRoleId(Long id) {
        return oaRoleDao.getMenuByRoleId(id);
    }
}

