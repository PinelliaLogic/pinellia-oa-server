package com.pinellia.common.security.service;

import com.pinellia.common.exception.UserLockException;
import com.pinellia.common.security.AccountUser;
import com.pinellia.entity.OaUser;
import com.pinellia.service.OaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义UserDetails
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private OaUserService oaUserService;

    /**
     * 获取用户信息
     * @param username
     * @return
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.error("到达====>UserDetailServiceImpl");
        //查询用户信息
        OaUser oaUser = oaUserService.loadUserByUsername(username);
//        log.error("oaUser====>" + oaUser);
        if (oaUser == null) {
            //用户名不存在
            throw new UsernameNotFoundException("用户名或密码错误!!!");
        } else if ("1".equals(oaUser.getStatus())) {
            throw new UserLockException("该账号已被锁定，请联系管理员!!!");
        }
        return new AccountUser(oaUser.getUsername(), oaUser.getPassword(), getUserAuthority(oaUser.getId()));
    }

    /**
     * 获取用户权限信息（角色、菜单权限）
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(Long userId) {
        // 实际以数据表结构为准
        // 角色(比如ROLE_admin)，菜单操作权限(比如sys:user:list)
        String authority = oaUserService.getUserAuthority(userId);     // 比如ROLE_admin,ROLE_normal,sys:user:list,...

        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}

