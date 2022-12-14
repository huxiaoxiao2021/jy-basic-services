package com.jdl.basic.api.domain.workStation;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

@Data
public class SiteWaveScheduleQuery extends BasePagerCondition {

    /**
     * 区域编码
     */
    private Integer orgCode;

    /**
     * 场地编码
     */
    private Integer siteCode;
    
    /**
     * 分页大小
     */
    private Integer pageSize;
}
