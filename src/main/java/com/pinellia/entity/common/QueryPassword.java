package com.pinellia.entity.common;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.UUID;

@Data
public class QueryPassword implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id = UUID.randomUUID();

    private String password;

}
