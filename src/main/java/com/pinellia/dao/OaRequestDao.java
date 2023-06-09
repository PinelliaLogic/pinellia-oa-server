package com.pinellia.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.pinellia.entity.OaRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (OaRequest)表数据库访问层
 *
 * @author pinellia
 * @since 2023-04-24 20:18:05
 */
public interface OaRequestDao extends BaseMapper<OaRequest> {

    @Insert("INSERT INTO oa_uq (user_id, request_id) VALUES (#{userId}, #{requestId})")
    public boolean add(Long userId, Long requestId);

    @Select("SELECT\n" +
            "\toa_user.`name` \n" +
            "FROM\n" +
            "\toa_uq\n" +
            "\tINNER JOIN oa_request ON oa_uq.request_id = oa_request.id\n" +
            "\tINNER JOIN oa_user ON oa_uq.user_id = oa_user.id \n" +
            "WHERE\n" +
            "\toa_request.id = #{id}")
    public String getNameByRequestId(Long id);

    //用户分页查询
    @Select("SELECT\n" +
            "\toa_request.* \n" +
            "FROM\n" +
            "\toa_uq\n" +
            "\tINNER JOIN oa_request ON oa_uq.request_id = oa_request.id \n" +
            "WHERE\n" +
            "\toa_uq.user_id = #{id}")
    IPage<OaRequest> selectMyPage(Long id, IPage<OaRequest> page, @Param(Constants.WRAPPER) Wrapper<OaRequest> queryWrapper);


}

