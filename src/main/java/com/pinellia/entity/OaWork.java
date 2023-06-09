package com.pinellia.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pinellia.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * (OaWork)表实体类
 *
 * @author pinellia
 * @since 2023-04-25 13:20:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaWork extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //id
    @TableId(type = IdType.AUTO)
    private Long id;
    //内容
    private String content;
    //开始时间
    @TableField("begin_time")
    private Date beginTime;
    //结束时间
    @TableField("end_time")
    private Date endTime;
    //状态(0 已创建，1 已发布，2 超时未提交，3 已提交）
    private Character status;
    //文件
    private String cover;
    //备注
    private String remark;

    //执行人的姓名
    @TableField(exist = false)
    private List<String> uname;
    //发布人的姓名
    @TableField(exist = false)
    private String name;
}



