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
 * (OaResources)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaResources extends BaseEntity implements Serializable{

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //文件id
    @TableId(type = IdType.AUTO)
    private Long id;
    //文件名称
    @TableField("resources_name")
    private String resourcesName;
    //状态
    private Character status;
    //文件类型
    private String type;
    //文件大小
    private String size;
    //文件地址
    private String cover;
    //备注
    private String remark;
}

