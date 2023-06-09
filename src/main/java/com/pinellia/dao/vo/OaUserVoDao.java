package com.pinellia.dao.vo;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.pinellia.entity.vo.OaUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface OaUserVoDao extends BaseMapper<OaUserVo> {

    //自定义分页
    @Select("SELECT\n" +
            "\toa_role.`names`,\n" +
            "\toa_department.department_name,\n" +
            "\toa_job.job_name,\n" +
            "\toa_user.id,\n" +
            "\toa_user.username,\n" +
            "\toa_user.`name`,\n" +
            "\toa_user.avatar,\n" +
            "\toa_user.salary,\n" +
            "\toa_user.sex,\n" +
            "\toa_user.age,\n" +
            "\toa_user.email,\n" +
            "\toa_user.birthday,\n" +
            "\toa_user.id_card,\n" +
            "\toa_user.phone,\n" +
            "\toa_user.address,\n" +
            "\toa_user.login_date,\n" +
            "\toa_user.`status`,\n" +
            "\toa_user.create_time,\n" +
            "\toa_user.update_time,\n" +
            "\toa_user.remark,\n" +
            "\toa_user.is_delete \n" +
            "FROM\n" +
            "\toa_user\n" +
            "\tINNER JOIN oa_ur ON oa_user.id = oa_ur.user_id\n" +
            "\tINNER JOIN oa_uj ON oa_user.id = oa_uj.user_id\n" +
            "\tINNER JOIN oa_ud ON oa_user.id = oa_ud.user_id\n" +
            "\tINNER JOIN oa_role ON oa_ur.role_id = oa_role.id\n" +
            "\tINNER JOIN oa_department ON oa_ud.department_id = oa_department.id\n" +
            "\tINNER JOIN oa_job ON oa_uj.job_id = oa_job.id")
    IPage<OaUserVo> pages(IPage<OaUserVo> userVoIPage, @Param(Constants.WRAPPER) Wrapper wrapper);

    //获取单个用户的完整信息
    @Select("SELECT\n" +
            "\toa_department.department_name,\n" +
            "\toa_job.job_name,\n" +
            "\toa_user.id,\n" +
            "\toa_user.username,\n" +
            "\toa_user.`name`,\n" +
            "\toa_user.avatar,\n" +
            "\toa_user.salary,\n" +
            "\toa_user.sex,\n" +
            "\toa_user.age,\n" +
            "\toa_user.email,\n" +
            "\toa_user.birthday,\n" +
            "\toa_user.id_card,\n" +
            "\toa_user.phone,\n" +
            "\toa_user.address,\n" +
            "\toa_user.login_date,\n" +
            "\toa_user.`status`,\n" +
            "\toa_user.create_time,\n" +
            "\toa_user.update_time,\n" +
            "\toa_user.remark,\n" +
            "\toa_user.is_delete \n" +
            "FROM\n" +
            "\toa_user\n" +
            "\tINNER JOIN oa_uj ON oa_user.id = oa_uj.user_id\n" +
            "\tINNER JOIN oa_ud ON oa_user.id = oa_ud.user_id\n" +
            "\tINNER JOIN oa_department ON oa_ud.department_id = oa_department.id\n" +
            "\tINNER JOIN oa_job ON oa_uj.job_id = oa_job.id \n" +
            "WHERE\n" +
            "\toa_user.id = #{id}")
    OaUserVo getUserById(Long id);

}
