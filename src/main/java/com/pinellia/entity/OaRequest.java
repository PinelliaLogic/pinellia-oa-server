package com.pinellia.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.pinellia.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (OaRequest)表实体类
 *
 * @author pinellia
 * @since 2023-04-24 20:18:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaRequest extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //id
    @TableId(type = IdType.AUTO)
    private Long id;
    //内容
    private String content;
    //申请时间
    private Date time;
    //(0 未申请,1 已撤回,2已申请,3 已批准,4已拒绝）
    private Character status;
    //备注
    private String remark;

    //申请人
    @TableField(exist = false)
    private String name;
}

