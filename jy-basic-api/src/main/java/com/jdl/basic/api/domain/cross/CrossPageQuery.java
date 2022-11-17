package com.jdl.basic.api.domain.cross;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

/**
 * @author liwenji
 * @date 2022-11-15 16:14
 */
@Data
public class CrossPageQuery extends BasePagerCondition {

    /**
     * 场地ID
     */
    private Integer dmsId;
}
