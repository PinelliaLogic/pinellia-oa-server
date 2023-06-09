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
 * (OaUser)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaUser extends BaseEntity implements Serializable{

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //工号
    @TableId(type = IdType.AUTO)
    private Long id;
    //用户名
    private String username;
    //密码
    @TableField(select = false)
    private String password;
    //姓名
    private String name;
    //头像地址
    private String avatar;
    //薪资
    private Double salary;
    //性别
    private String sex;
    //年龄
    private Integer age;
    //邮箱
    private String email;
    //出生日期
    private Date birthday;
    //身份证号码
    @TableField("id_card")
    private String idCard;
    //手机号码
    private String phone;
    //家庭住址
    private String address;
    //最后登录时间
    @TableField("login_date")
    private Date loginDate;
    //账号状态（0正常 1停用）
    private String status;
    //备注
    private String remark;
    //是否已经删除（0 可用，1 删除)
    @TableField("is_delete")
    private String isDelete;

    //用户角色列表
    @TableField(exist = false)
    private List<OaRole> roleList;
    //用户岗位列表
    @TableField(exist = false)
    private List<OaJob> jobList;
    //用户部门列表
    @TableField(exist = false)
    private String department;
}