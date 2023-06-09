package com.pinellia.entity.vo;

import com.pinellia.entity.OaResources;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OaRecoverVo extends OaResources {

    private String name;

    private String departName;

}
