package com.pinellia.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pinellia.entity.OaRole;
import com.pinellia.entity.OaUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * (OaUser)表数据库访问层
 */
public interface OaUserDao extends BaseMapper<OaUser> {

    @Select("SELECT * FROM oa_user WHERE username = #{username}")
    public OaUser loadUserByUsername(String username);

    @Select("SELECT username FROM oa_user")
    List<String> getAllUsername();

    @Update("UPDATE oa_user SET is_delete = 1 WHERE id = #{id}")
    void deleteUser(Long id);

    //根据身份证号码动态更新年龄
    @Update("UPDATE oa_user SET age = timestampdiff(year, substring(id_card, 7, 8), now())")
    public void updateAge();

    //分配默认部门
    @Insert("INSERT INTO oa_ud(user_id, department_id) values (#{id}, 400)")
    void divideDepartment(Long id);

    //分配默认工作
    @Insert("INSERT INTO oa_uj(user_id, job_id) values (#{id}, 300)")
    void divideJob(Long id);

    //分配默认角色
    @Insert("INSERT INTO oa_ur(user_id, role_id) values (#{id}, 710)")
    void divideRole(Long id);
}

