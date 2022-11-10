package com.jdl.basic.api.domain.cross;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

/**
 * @author liwenji
 * @date 2022-11-09 10:24
 */
@Data
public class SortCrossQuery extends BasePagerCondition {

    private Integer dmsId;

    private Integer orgId;

    private Integer siteType;

    private Integer subType;

    private Integer thirdType;

    private String tabletrolleyCode;

    private String crossCode;

    private String siteCode;

    private Integer pageSize;
    
}
