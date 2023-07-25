package com.jdl.basic.api.dto.ncWhiteList;

import lombok.Data;

import java.util.List;

@Data
public class NCWhiteListDTO {

    private Integer id;

    private Integer ruleType;

    private String class1;

    private String keyword;

    private String ruleDetail;

    private String updateTime;

    private String operatorErp;

    private List<NCWhiteRuleDTO> rules;

}
