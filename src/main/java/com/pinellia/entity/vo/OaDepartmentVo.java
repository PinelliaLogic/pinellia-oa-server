package com.pinellia.entity.vo;

import com.pinellia.entity.OaUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Pinellia
 * @version 1.0
 * @date 2023/4/26 20:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaDepartmentVo {

    private Long id;

    private String name;

    private List<OaUser> children;

}
