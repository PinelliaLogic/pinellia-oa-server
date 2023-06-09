package com.pinellia.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.common.QueryPassword;

public interface QueryPasswordService extends IService<QueryPassword> {

    String getPasswordById(Long id);

    boolean updatePasswordById(String password, Long id);

    void updateStatusById(Long id);

}
