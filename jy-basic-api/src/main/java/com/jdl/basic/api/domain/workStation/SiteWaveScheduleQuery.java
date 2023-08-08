package com.jdl.basic.api.domain.workStation;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

@Data
public class SiteWaveScheduleQuery extends BasePagerCondition {

    private static final long serialVersionUID = 954334359703842447L;
    
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

    /**
     * 省区编码
     */
    private String provinceAgencyCode;
    /**
     * 枢纽编码
     */
    private String areaHubCode;
}
