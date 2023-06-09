package com.pinellia.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.pinellia.entity.OaDepartment;
import com.pinellia.entity.OaUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * (OaDepartment)表数据库访问层
 */
public interface OaDepartmentDao extends BaseMapper<OaDepartment> {

    //更新部门人数
    @Update("UPDATE oa_department SET people_num = (SELECT count(*) FROM oa_ud WHERE department_id = oa_department.id)")
    public void updateCount();

    //查询用户信息通过部门id,分页
    @Select("SELECT\n" +
            "\toa_user.id,\n" +
            "\toa_user.username,\n" +
            "\toa_user.`name` \n" +
            "FROM\n" +
            "\toa_user\n" +
            "\tINNER JOIN oa_ud ON oa_user.id = oa_ud.user_id\n" +
            "\tINNER JOIN oa_department ON oa_ud.department_id = oa_department.id \n" +
            "WHERE\n" +
            "\toa_department.id = #{id}")
    IPage<OaUser> getUserByDepartmentIdPage(Long id, IPage<OaUser> page, @Param(Constants.WRAPPER) Wrapper<OaUser> queryWrapper);

    @Delete("DELETE FROM oa_ud WHERE user_id = #{userId}")
    boolean deleteUserDepartmentByUserId(Long userId);

    //通过用户id查询部门名称
    @Select("SELECT\n" +
            "\toa_department.department_name \n" +
            "FROM\n" +
            "\toa_user\n" +
            "\tINNER JOIN oa_ud ON oa_user.id = oa_ud.user_id\n" +
            "\tINNER JOIN oa_department ON oa_ud.department_id = oa_department.id \n" +
            "WHERE\n" +
            "\toa_user.id = #{userId}")
    public OaDepartment getUserDepartment(Long userId);

    //从部门里移除用户
    @Update("UPDATE oa_ud SET department_id = '400' WHERE user_id = #{id}")
    public boolean removeUser(Long id);

    //分配部门
    @Update("UPDATE oa_ud SET department_id = #{did} WHERE user_id = #{uid}")
    public boolean divideUser(Long did, Long uid);

    //通过部门id查询用户信息
    @Select("SELECT\n" +
            "\toa_user.id,\n" +
            "\toa_user.username,\n" +
            "\toa_user.`name` \n" +
            "FROM\n" +
            "\toa_user\n" +
            "\tINNER JOIN oa_ud ON oa_user.id = oa_ud.user_id\n" +
            "\tINNER JOIN oa_department ON oa_ud.department_id = oa_department.id \n" +
            "WHERE\n" +
            "\toa_department.id = #{id}")
    public List<OaUser> getUserByDepartmentId(Long id);

}

