package com.pinellia.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pinellia.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (OaClock)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaClock extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //id
    @TableId(type = IdType.AUTO)
    private Long id;
    //标题
    private String title;
    //开始时间
    @TableField("begin_time")
    private Date beginTime;
    //结束时间
    @TableField("end_time")
    private Date endTime;
    //当前签到人数
    private Integer count;
    //状态(0 未开始 1进行中 2 已结束)
    private Character status;
}

