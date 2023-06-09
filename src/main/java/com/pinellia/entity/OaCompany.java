package com.pinellia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (OaCompany)表实体类
 *
 * @author pinellia
 * @since 2023-04-11 17:06:56
 */
@Data
public class OaCompany implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //id
    @TableId(type = IdType.AUTO)
    private Long id;
    //名称
    private String title;
    //地址
    private String address;
    //发布人
    private String user;
    //日期
    private Date time;
    //备注
    private String remark;
    //内容
    private String content;
}

