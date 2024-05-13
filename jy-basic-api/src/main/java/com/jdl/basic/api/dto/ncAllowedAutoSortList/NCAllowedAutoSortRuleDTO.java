package com.jdl.basic.api.dto.ncAllowedAutoSortList;

import lombok.Data;

@Data
public class NCAllowedAutoSortRuleDTO {

    private Integer id;

    private Integer refId;

    private String quotaName;

    private Integer gt;

    private Integer gte;

    private Integer lt;

    private Integer lte;

    private Double gtValue;

    private Double ltValue;

    private String unit;

}
