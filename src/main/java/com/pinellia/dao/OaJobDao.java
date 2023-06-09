package com.pinellia.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.pinellia.entity.OaJob;
import com.pinellia.entity.OaUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * (OaJob)表数据库访问层
 */
public interface OaJobDao extends BaseMapper<OaJob> {

    //更新岗位人数
    @Update("UPDATE oa_job SET count = (SELECT count(*) FROM oa_uj WHERE job_id = oa_job.id)")
    public void updateCount();

    //通过job id查询员工
    @Select("SELECT\n" +
            "\toa_user.id,\n" +
            "\toa_user.username,\n" +
            "\toa_user.`name` \n" +
            "FROM\n" +
            "\toa_user\n" +
            "\tINNER JOIN oa_uj ON oa_user.id = oa_uj.user_id\n" +
            "\tINNER JOIN oa_job ON oa_uj.job_id = oa_job.id \n" +
            "WHERE\n" +
            "\toa_job.id = #{id}")
    List<OaUser> getUserByJobId(Long id);

    //根据用户id删除工作
    @Delete("DELETE FROM oa_uj WHERE user_id = #{userId}")
    boolean deleteUserJobByUserId(Long userId);

    //根据用户id查询岗位
    @Select("SELECT\n" +
            "\toa_job.* \n" +
            "FROM\n" +
            "\toa_uj\n" +
            "\tINNER JOIN oa_user ON oa_uj.user_id = oa_user.id\n" +
            "\tINNER JOIN oa_job ON oa_job.id = oa_uj.job_id \n" +
            "WHERE\n" +
            "\toa_user.id = #{userId}")
    public List<OaJob> getUserJob(Long userId);

    //根据岗位id查询用户并分页
    @Select("SELECT\n" +
            "\toa_user.id,\n" +
            "\toa_user.username,\n" +
            "\toa_user.`name` \n" +
            "FROM\n" +
            "\toa_user\n" +
            "\tINNER JOIN oa_uj ON oa_user.id = oa_uj.user_id\n" +
            "\tINNER JOIN oa_job ON oa_uj.job_id = oa_job.id \n" +
            "WHERE\n" +
            "\toa_job.id = #{id}")
    IPage<OaUser> getUserByJobPage(Long id, IPage<OaUser> page, @Param(Constants.WRAPPER) Wrapper<OaUser> queryWrapper);

    //从岗位中移除员工
    @Update("UPDATE oa_uj SET job_id = '300' WHERE user_id = #{id}")
    public boolean removeUser(Long id);

    //分配岗位
    @Update("UPDATE oa_uj SET job_id = #{jid} WHERE user_id = #{uid}")
    public boolean divideUser(Long jid, Long uid);
}

