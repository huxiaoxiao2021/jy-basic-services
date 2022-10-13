package com.jdl.basic.api.domain.boxFlow.dto;

import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 集包规则校验返回，错误的流向信息
 */
@Data
public class CollectBoxFlowDirectionConfResp {

    Integer result;//结果
    private List<CollectBoxFlowDirectionConf> wrongConfs = new ArrayList<>();
}