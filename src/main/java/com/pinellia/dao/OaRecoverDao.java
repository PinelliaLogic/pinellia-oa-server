package com.pinellia.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.pinellia.entity.OaDepartment;
import com.pinellia.entity.OaJob;
import com.pinellia.entity.OaRecover;
import com.pinellia.entity.vo.OaRecoverVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * (OaRecover)表数据库访问层
 */
public interface OaRecoverDao extends BaseMapper<OaRecover> {

    //回收数据到oa_recover表
    @Insert("insert into oa_recover (select * from oa_user where id = #{id})")
    public boolean recoverInfo(Long id);

    //恢复数据到oa_user表
    @Insert("insert into oa_user (select * from oa_recover where id = #{id})")
    public boolean recover(Long id);

    //修改为正常，未删除状态
    @Update("UPDATE oa_recover SET is_delete = '0' WHERE id = #{id}")
    boolean updateDelete(Long id);

    //根据用户id查询部门
    @Select("SELECT\n" +
            "\toa_department.department_name \n" +
            "FROM\n" +
            "\toa_ud\n" +
            "\tINNER JOIN oa_department ON oa_ud.department_id = oa_department.id \n" +
            "WHERE\n" +
            "\toa_ud.user_id = #{userId}")
    public OaDepartment getDepartmentByUserId(Long userId);

    @Select("SELECT\n" +
            "\toa_job.* \n" +
            "FROM\n" +
            "\toa_uj\n" +
            "\tINNER JOIN oa_job ON oa_uj.job_id = oa_job.id \n" +
            "WHERE\n" +
            "\toa_uj.user_id = #{userId}")
    public List<OaJob> getJobByUserId(Long userId);

}

