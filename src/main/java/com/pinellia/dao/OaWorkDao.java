package com.pinellia.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.pinellia.entity.OaUser;
import com.pinellia.entity.OaWork;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (OaWork)表数据库访问层
 *
 * @author pinellia
 * @since 2023-04-25 13:20:24
 */
public interface OaWorkDao extends BaseMapper<OaWork> {

    //插入到关联表oa_uw,设置发布人
    @Insert("INSERT INTO oa_wu(user_id, work_id) values(#{uid}, #{wid})")
    public boolean add(Long uid, Long wid);

    //用户工作分页
    @Select("SELECT\n" +
            "\toa_work.* \n" +
            "FROM\n" +
            "\toa_uw\n" +
            "\tINNER JOIN oa_work ON oa_uw.work_id = oa_work.id\n" +
            "\tINNER JOIN oa_user ON oa_user.id = oa_uw.user_id \n" +
            "WHERE\n" +
            "\toa_user.id = #{id}")
    IPage<OaWork> selectMyPage(Long id, IPage<OaWork> page, @Param(Constants.WRAPPER) Wrapper<OaWork> queryWrapper);

    //查询发布人的姓名
    @Select("SELECT\n" +
            "\toa_user.`name` \n" +
            "FROM\n" +
            "\toa_wu\n" +
            "\tINNER JOIN oa_user ON oa_wu.user_id = oa_user.id\n" +
            "\tINNER JOIN oa_work ON oa_work.id = oa_wu.work_id \n" +
            "WHERE\n" +
            "\toa_work.id = #{id}")
    public String getNameById(Long id);

    //查询执行人的姓名
    @Select("SELECT\n" +
            "\toa_user.`name` \n" +
            "FROM\n" +
            "\toa_uw\n" +
            "\tINNER JOIN oa_user ON oa_uw.user_id = oa_user.id\n" +
            "\tINNER JOIN oa_work ON oa_work.id = oa_uw.work_id \n" +
            "WHERE\n" +
            "\toa_work.id = #{id}")
    public List<String> getUNameById(Long id);

    //获取该工作已分配的人员信息
    @Select("SELECT\n" +
            "\toa_user.id,\n" +
            "\toa_user.username,\n" +
            "\toa_user.`name` \n" +
            "FROM\n" +
            "\toa_uw\n" +
            "\tINNER JOIN oa_user ON oa_uw.user_id = oa_user.id\n" +
            "\tINNER JOIN oa_work ON oa_uw.work_id = oa_work.id \n" +
            "WHERE\n" +
            "\toa_work.id = #{id}")
    public List<OaUser> getUserByWorkId(Long id);

    //根据用户id查询工作
    @Select("SELECT\n" +
            "\toa_work.* \n" +
            "FROM\n" +
            "\toa_uw\n" +
            "\tINNER JOIN oa_work ON oa_uw.work_id = oa_work.id\n" +
            "\tINNER JOIN oa_user ON oa_user.id = oa_uw.user_id \n" +
            "WHERE\n" +
            "\toa_user.id = #{id}")
    public List<OaWork> getMyWork(Long id);

    //查询未完成的工作数
    @Select("SELECT\n" +
            "\tcount(*) \n" +
            "FROM\n" +
            "\toa_uw\n" +
            "\tINNER JOIN oa_user ON oa_uw.user_id = oa_user.id\n" +
            "\tINNER JOIN oa_work ON oa_work.id = oa_uw.work_id \n" +
            "WHERE\n" +
            "\toa_user.id = #{id} \n" +
            "\tAND oa_work.`status` = '1'" +
            "\tOR oa_work.`status` = '0'")
    public Integer getIncomplete(Long id);

    //查询超时未提交的工作数
    @Select("SELECT\n" +
            "\tcount(*) \n" +
            "FROM\n" +
            "\toa_uw\n" +
            "\tINNER JOIN oa_user ON oa_uw.user_id = oa_user.id\n" +
            "\tINNER JOIN oa_work ON oa_work.id = oa_uw.work_id \n" +
            "WHERE\n" +
            "\toa_user.id = #{id} \n" +
            "\tAND oa_work.`status` = '2'")
    public Integer getTimeout(Long id);

    //查询已提交的工作数
    @Select("SELECT\n" +
            "\tcount(*) \n" +
            "FROM\n" +
            "\toa_uw\n" +
            "\tINNER JOIN oa_user ON oa_uw.user_id = oa_user.id\n" +
            "\tINNER JOIN oa_work ON oa_work.id = oa_uw.work_id \n" +
            "WHERE\n" +
            "\toa_user.id = #{id} \n" +
            "\tAND oa_work.`status` = '3'")
    public Integer getSubmit(Long id);

    //查询被打回的工作数
    @Select("SELECT\n" +
            "\tcount(*) \n" +
            "FROM\n" +
            "\toa_uw\n" +
            "\tINNER JOIN oa_work ON oa_uw.work_id = oa_work.id\n" +
            "\tINNER JOIN oa_user ON oa_user.id = oa_uw.user_id \n" +
            "WHERE\n" +
            "\toa_user.id = #{id} \n" +
            "\tAND oa_work.`status` = '4'")
    public Integer getReturn(Long id);

    //删除该工作的执行人员id
    @Delete("DELETE FROM oa_uw WHERE work_id = #{id}")
    public boolean removeId(Long id);

    //设置该工作的执行人员
    @Insert("INSERT INTO oa_uw(user_id, work_id) VALUES(#{uid}, #{wid})")
    public boolean divideWork(Long uid, Long wid);

}

