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
 * (OaCalendar)表实体类
 *
 * @author pinellia
 * @since 2023-04-11 17:06:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaCalendar extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //日程id
    @TableId(type = IdType.AUTO)
    private Long id;
    //日程名称
    private String title;
    //开始时间
    @TableField("begin_time")
    private Date beginTime;
    //结束时间
    @TableField("end_time")
    private Date endTime;
    //备注
    private String remark;
}

