package com.pinellia.service.impl.common;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.common.UpdateUserInfoDao;
import com.pinellia.entity.common.UpdateUserInfo;
import com.pinellia.service.common.UpdateUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserInfoServiceImpl extends ServiceImpl<UpdateUserInfoDao, UpdateUserInfo> implements UpdateUserInfoService {

    @Autowired
    private UpdateUserInfoDao updateUserInfoDao;

    @Override
    public boolean updateUserInfo(String phone, String email, Long id) {
        return updateUserInfoDao.updateUserInfo(phone, email, id);
    }
}
