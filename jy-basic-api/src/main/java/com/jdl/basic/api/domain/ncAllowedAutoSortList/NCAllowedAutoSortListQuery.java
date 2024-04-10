package com.jdl.basic.api.domain.ncAllowedAutoSortList;

import lombok.Data;

@Data
public class NCAllowedAutoSortListQuery {

    private Integer id;

    private String venderId;

    private String keyword;

    private Integer pageNo;

    private Integer pageSize;

    private Integer offset;

    public void setOffset() {
        offset = (pageNo - 1) * pageSize;
    }
}
