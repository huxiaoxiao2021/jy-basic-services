package com.jdl.basic.api.dto.ncWhiteList;

import lombok.Data;

@Data
public class NCWhiteRuleDTO {

    private Integer id;

    private Integer refId;

    private Integer quotaName;

    private Integer gt;

    private Integer gte;

    private Integer lt;

    private Integer lte;

    private Integer gtValue;

    private Integer ltValue;

    private String unit;

}
