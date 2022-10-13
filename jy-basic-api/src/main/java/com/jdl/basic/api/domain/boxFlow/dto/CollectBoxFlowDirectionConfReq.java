package com.jdl.basic.api.domain.boxFlow.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 集包规则校验请求
 */
@Data
public class CollectBoxFlowDirectionConfReq {


    /**
     * 始发站点id
     */
    private Integer startSiteId;

    /**
     * 目的站点id
     */
    private List<Integer> endSiteId;

    /**
     * 建包流向id
     */
    private Integer boxReceiveId;

    /**
     * 承运类型，1 公路 2 航空
     */
    private Integer transportType;

    /**
     * 集包要求 1成品包2可混包
     */
    private Integer collectClaim;
    /**
     * 规则，1 进港 2 出港
     */
    private Integer flowType;


}