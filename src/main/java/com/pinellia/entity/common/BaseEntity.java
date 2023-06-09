package com.pinellia.entity.common;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装创建时间和更新时间
 **/
@Data
public class BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //创建时间
    @TableField("create_time")
    private Date createTime;
    //更新时间
    @TableField("update_time")
    private Date updateTime;

}
