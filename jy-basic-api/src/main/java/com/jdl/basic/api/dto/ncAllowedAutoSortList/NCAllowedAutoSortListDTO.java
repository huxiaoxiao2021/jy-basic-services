package com.jdl.basic.api.dto.ncAllowedAutoSortList;

import lombok.Data;

import java.util.List;

@Data
public class NCAllowedAutoSortListDTO {

    private Integer id;

    private String keyword;

    private String ruleDetail;

    private String updateTime;

    private String operatorErp;

    private List<NCAllowedAutoSortRuleDTO> rules;

}
