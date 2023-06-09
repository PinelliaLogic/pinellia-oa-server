package com.pinellia.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (OaClockNotes)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaClockNotes implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //考勤统计表id
    @TableId(type = IdType.AUTO)
    private Long id;
    //员工id
    @TableField("user_id")
    private Long userId;
    //(0 未打卡 ， 1 已打卡)
    @TableField("is_punch")
    private String isPunch;
    //打卡时间
    @TableField("punch_time")
    private Date punchTime;
}

