package com.jdl.basic.api.domain.ncWhiteList;

import lombok.Data;

@Data
public class NCWhiteListQuery {

    private Integer id;

    private Integer ruleType;

    private String class1;

    private String keyword;

    private Integer pageNo;

    private Integer pageSize;

    private Integer offset;

    public void setOffset() {
        offset = (pageNo - 1) * pageSize;
    }
}
