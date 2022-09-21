package com.jdl.basic.api.domain.boxFlow.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

/**
 * 成品包，请求
 */
@Data
public class CollectBoxFlowFinishBoxReq {

    /**
     * 始发站点id
     */
    private Integer startSiteId;


    /**
     * 目的站点id
     */
    private ArrayList<Integer> endSiteId;


    /**
     * 承运类型，1 公路 2 航空
     */
    private Integer transportType;

    /**
     * 规则，1 进港 2 出港
     */
    private Integer flowType;


}