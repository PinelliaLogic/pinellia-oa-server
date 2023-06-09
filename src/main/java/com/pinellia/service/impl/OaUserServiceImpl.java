package com.pinellia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.OaMenuDao;
import com.pinellia.dao.OaRoleDao;
import com.pinellia.dao.OaUserDao;
import com.pinellia.entity.OaMenu;
import com.pinellia.entity.OaRole;
import com.pinellia.entity.OaUser;
import com.pinellia.service.OaUserService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (OaUser)表服务实现类
 *
 * @author pinellia
 * @since 2023-03-20 15:22:10
 */
@Service
@Transactional
public class OaUserServiceImpl extends ServiceImpl<OaUserDao, OaUser> implements OaUserService {

    @Autowired
    private OaUserDao oaUserDao;

    @Autowired
    private OaRoleDao oaRoleDao;

    @Autowired
    private OaMenuDao oaMenuDao;

    //获取用户信息
    @Override
    public OaUser loadUserByUsername(String username) {
        return oaUserDao.loadUserByUsername(username);
    }

    //获取用户角色权限
    @Override
    public String getUserAuthority(Long id) {
        StringBuilder authority = new StringBuilder();
        //根据用户id获取所有角色信息
        List<OaRole> roleList = oaRoleDao.getRoleList(id);
        if (roleList.size() > 0) {
            //获取角色并拼接
            String roleStr = roleList.stream().map(r -> "ROLE_" + r.getRoleName()).collect(Collectors.joining(","));
            authority.append(roleStr);
        }
        //遍历所有的角色，获取所有菜单权限且不重复
        Set<String> menuSet = new HashSet<>();
        for (OaRole oaRole : roleList) {
            //获取菜单
            List<OaMenu> menuList = oaMenuDao.getMenu(oaRole.getId());
            for (OaMenu oaMenu : menuList) {
                //获取权限标识
                String perms = oaMenu.getPerms();
                //判断不为空
                if (!StringUtil.isNullOrEmpty(perms)) {
                    menuSet.add(perms);
                }
            }
        }
        //判断是否有权限
        if (menuSet.size() > 0) {
            authority.append(",");
            String menuStr = String.join(",", menuSet);
            authority.append(menuStr);
        }
        System.out.println("authority:" + authority);

        return authority.toString();
    }

    //获取所有用户名
    @Override
    public List<String> getAllUsername() {
        return oaUserDao.getAllUsername();
    }

    @Override
    public void deleteUser(Long id) {
        oaUserDao.deleteUser(id);
    }

    @Override
    public void updateAge() {
        oaUserDao.updateAge();
    }

    @Override
    public void divideDepartment(Long id) {
        oaUserDao.divideDepartment(id);
    }

    @Override
    public void divideJob(Long id) {
        oaUserDao.divideJob(id);
    }

    @Override
    public void divideRole(Long id) {
        oaUserDao.divideRole(id);
    }

}

