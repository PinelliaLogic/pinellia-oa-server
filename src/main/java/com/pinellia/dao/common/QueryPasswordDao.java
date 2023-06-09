package com.pinellia.dao.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pinellia.entity.common.QueryPassword;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface QueryPasswordDao extends BaseMapper<QueryPassword> {

    @Select("SELECT\n" +
            "\toa_user.`password` \n" +
            "FROM\n" +
            "\toa_user \n" +
            "WHERE\n" +
            "\toa_user.id = #{id}")
    String getPasswordById(Long id);

    @Update("UPDATE oa_user SET password = #{password} WHERE id = #{id}")
    boolean updatePasswordById(String password, Long id);

    @Update("UPDATE oa_user SET status = '1' WHERE id = #{id}")
    void updateStatusById(Long id);

}
