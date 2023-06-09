package com.pinellia.entity.vo;

import com.pinellia.entity.OaUser;
import lombok.Data;

@Data
public class OaUserVo extends OaUser {

    private String departmentName;

    private String jobName;

    private String names;
}
