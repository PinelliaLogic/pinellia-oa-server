package com.pinellia.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.common.UpdateUserInfo;

public interface UpdateUserInfoService extends IService<UpdateUserInfo> {

    boolean updateUserInfo(String phone, String email, Long id);
}
