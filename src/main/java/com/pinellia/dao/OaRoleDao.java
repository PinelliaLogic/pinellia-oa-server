package com.pinellia.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pinellia.entity.OaMenu;
import com.pinellia.entity.OaRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (OaRole)表数据库访问层
 */
public interface OaRoleDao extends BaseMapper<OaRole> {

    //获取用户角色
    @Select("SELECT * FROM oa_role WHERE id IN (SELECT role_id FROM oa_ur WHERE user_id = #{id})")
    List<OaRole> getRoleList(Long id);

    //删除角色权限
    @Delete("DELETE FROM oa_rm WHERE role_id = #{roleid}")
    public boolean deleteMenuByRoleId(Long roleId);

    //分配角色权限
    @Insert("INSERT INTO oa_rm (role_id, menu_id) VALUES (#{roleId}, #{menuId})")
    public boolean addMenuByRole(Long roleId, Long menuId);

    //分配用户角色
    @Insert("INSERT INTO oa_ur (user_id, role_id) VALUES (#{userId}, #{roleId})")
    public boolean addRoleByUser(Long userId, Long roleId);

    //删除用户角色权限
    @Delete("DELETE FROM oa_ur WHERE user_id = #{userId}")
    public boolean deleteRoleByUser(Long userId);

    //获取角色菜单权限
    @Select("SELECT\n" +
            "\toa_menu.* \n" +
            "FROM\n" +
            "\toa_role\n" +
            "\tINNER JOIN oa_rm ON oa_role.id = oa_rm.role_id\n" +
            "\tINNER JOIN oa_menu ON oa_rm.menu_id = oa_menu.id \n" +
            "WHERE\n" +
            "\toa_role.id = #{id}")
    List<OaMenu> getMenuByRoleId(Long id);

}

