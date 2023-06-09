package com.pinellia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.OaRole;
import com.pinellia.entity.OaUser;
import java.util.List;


/**
 * (OaUser)表服务接口
 *
 * @author pinellia
 * @since 2023-03-20 15:22:10
 */
public interface OaUserService extends IService<OaUser>{

    OaUser loadUserByUsername(String username);

    String getUserAuthority(Long id);

    List<String> getAllUsername();

    void deleteUser(Long id);

    void updateAge();

    void divideDepartment(Long id);

    void divideJob(Long id);

    void divideRole(Long id);

}

