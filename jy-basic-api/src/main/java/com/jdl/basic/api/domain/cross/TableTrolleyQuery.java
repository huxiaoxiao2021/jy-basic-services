package com.jdl.basic.api.domain.cross;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

/**
 * @author liwenji
 * @date 2022-11-15 17:43
 */
@Data
public class TableTrolleyQuery extends BasePagerCondition {

    /**
     * 笼车编号
     */
    private String tabletrolleyCode;
    
    /**
     * 滑道编号
     */
    private String crossCode;

    /**
     * 始发场地ID
     */
    private Integer dmsId;

    /**
     * 目的地场地ID
     */
    private Integer siteCode;
}
