package com.pinellia.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pinellia.entity.OaMenu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (OaMenu)表数据库访问层
 */
public interface OaMenuDao extends BaseMapper<OaMenu> {

    //获取用户菜单权限信息
    @Select("SELECT * FROM oa_menu WHERE id IN (SELECT menu_id FROM oa_rm WHERE role_id = #{id})")
    List<OaMenu> getMenu(Long id);

}

