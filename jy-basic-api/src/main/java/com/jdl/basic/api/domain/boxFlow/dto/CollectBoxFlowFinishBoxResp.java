package com.jdl.basic.api.domain.boxFlow.dto;

import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import lombok.Data;

import java.util.ArrayList;

/**
 * 成品包，请求
 */
@Data
public class CollectBoxFlowFinishBoxResp {

    //true 全都是是成品包，false 有可混包
    private boolean result;

    //成品包
    private ArrayList<CollectBoxFlowDirectionConf> finishSites;

    //可混包
    private ArrayList<CollectBoxFlowDirectionConf> mixSites;

}