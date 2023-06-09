package com.pinellia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pinellia.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (OaRole)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaRole extends BaseEntity implements Serializable{

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //权限ID
    @TableId(type = IdType.AUTO)
    private Long id;
    //权限代码
    @TableField("role_name")
    private String roleName;
    //权限名称
    private String names;
    //权限描述
    private String note;
}

