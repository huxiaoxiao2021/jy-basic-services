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
     * 滑道编号
     */
    private String crossCode;

    /**
     * 场地ID
     */
    private Integer dmsId;
}
