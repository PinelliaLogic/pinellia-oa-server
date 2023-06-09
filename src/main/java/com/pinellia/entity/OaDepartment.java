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
 * (OaDepartment)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaDepartment extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //部门id
    @TableId(type = IdType.AUTO)
    private Long id;
    //部门名称
    @TableField("department_name")
    private String departmentName;
    //部门收入
    private Long income;
    //资金投入
    private Long invest;
    //员工数
    @TableField("people_num")
    private Integer peopleNum;
    //备注
    private String remark;

}

