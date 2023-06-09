package com.pinellia.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.pinellia.entity.OaResources;
import com.pinellia.entity.OaUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (OaResources)表数据库访问层
 */
public interface OaResourcesDao extends BaseMapper<OaResources> {

    //将资源信息添加到关联表
    @Insert("INSERT INTO oa_user_resources (user_id, resources) VALUES (#{uid}, #{id})")
    public boolean addResources(Long uid, Long id);

    //从关联表中删除资源信息
    @Delete("DELETE FROM oa_user_resources WHERE resources = #{id}")
    public boolean deleteResourcesById(Long id);

    //用户查询资料分页
    @Select("SELECT\n" +
            "\toa_resources.* \n" +
            "FROM\n" +
            "\toa_user_download\n" +
            "\tINNER JOIN oa_resources ON oa_user_download.resources_id = oa_resources.id\n" +
            "\tINNER JOIN oa_user ON oa_user.id = oa_user_download.user_id \n" +
            "WHERE\n" +
            "\toa_user.id = #{id}")
    IPage<OaResources> selectMyPage(Long id, IPage<OaResources> page, @Param(Constants.WRAPPER) Wrapper<OaResources> queryWrapper);

    @Select("SELECT\n" +
            "\toa_resources.* \n" +
            "FROM\n" +
            "\toa_user_download\n" +
            "\tINNER JOIN oa_resources ON oa_user_download.resources_id = oa_resources.id\n" +
            "\tINNER JOIN oa_user ON oa_user.id = oa_user_download.user_id \n" +
            "WHERE\n" +
            "\toa_user.id = #{id}")
    List<OaResources> getAll(Long id);

    @Select("SELECT\n" +
            "\toa_user.* \n" +
            "FROM\n" +
            "\toa_user_download\n" +
            "\tINNER JOIN oa_resources ON oa_user_download.resources_id = oa_resources.id\n" +
            "\tINNER JOIN oa_user ON oa_user.id = oa_user_download.user_id \n" +
            "WHERE\n" +
            "\toa_resources.id = #{id}")
    public List<OaUser> getDivideUser(Long id);

    //删除指定资源的拥有者
    @Delete("DELETE FROM oa_user_download WHERE resources_id = #{id}")
    public boolean removeResourcesById(Long id);

    //通过资源id分配给指定人员
    @Insert("INSERT INTO oa_user_download(user_id, resources_id) VALUES(#{uid}, #{rid})")
    public boolean divideResources(Long uid, Long rid);

}

