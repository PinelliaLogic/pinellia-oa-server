package com.pinellia.dao.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pinellia.entity.common.UpdateUserInfo;
import org.apache.ibatis.annotations.Update;

public interface UpdateUserInfoDao extends BaseMapper<UpdateUserInfo> {

    @Update("UPDATE oa_user SET phone = #{phone}, email = #{email} WHERE id = #{id}")
    boolean updateUserInfo(String phone, String email, Long id);

}
