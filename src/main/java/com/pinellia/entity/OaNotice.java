package com.pinellia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pinellia.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (OaNotice)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaNotice extends BaseEntity implements Serializable{

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //公告ID
    @TableId(type = IdType.AUTO)
    private Long id;
    //标题
    private String title;
    //内容
    private String content;
    //发布人
    private String user;
    //备注
    private String remark;
    //状态 0 未发布， 1 发布
    private Character status;

}