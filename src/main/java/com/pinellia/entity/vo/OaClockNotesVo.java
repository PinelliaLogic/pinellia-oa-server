package com.pinellia.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.pinellia.entity.OaClockNotes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaClockNotesVo extends OaClockNotes implements Serializable {

    //员工用户名
    private String username;
    //员工姓名
    private String name;
    //所属部门
    @TableField("department_name")
    private String departmentName;
}
