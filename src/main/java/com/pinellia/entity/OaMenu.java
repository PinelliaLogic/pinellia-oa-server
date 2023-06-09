package com.pinellia.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pinellia.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * (OaMenu)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaMenu extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //菜单id
    @TableId(type = IdType.AUTO)
    private Long id;
    //菜单名称
    private String name;
    //菜单图标
    private String icon;
    //父菜单id
    @TableField("parent_id")
    private Long parentId;
    //显示顺序
    @TableField("order_num")
    private Integer orderNum;
    //路由地址
    private String path;
    //组件路径
    private String component;
    //菜单类型(M 目录,C 菜单,F 按钮)
    @TableField("menu_type")
    private String menuType;
    //权限标识
    private String perms;
    //备注
    private String remark;

    //子菜单
    @TableField(exist = false)
    private List<OaMenu> children = new ArrayList<>();
}

