package com.jdl.basic.api.domain.work;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

import java.io.Serializable;

@Data
public class WorkGridCandidateQuery extends BasePagerCondition implements Serializable {

    private Integer siteCode;
    private Integer siteType;
    private String erp;
    /**
     * 分页-pageSize
     */
    private Integer pageSize;
}
