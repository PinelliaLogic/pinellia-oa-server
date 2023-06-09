package com.pinellia.entity.common;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePassword implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    private String oldPassword;

    private String newPassword;

}
