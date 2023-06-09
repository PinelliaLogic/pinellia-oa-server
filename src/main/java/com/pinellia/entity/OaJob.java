package com.pinellia.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pinellia.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * (OaJob)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaJob extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //岗位id
    @TableId(type = IdType.AUTO)
    private Long id;
    //岗位名称
    @TableField("job_name")
    private String jobName;
    //岗位人数
    private Integer count;
    //备注
    private String remark;
}

