package com.jdl.basic.api.service.boxFlow;


import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionPushConfDto;
import com.jdl.basic.common.utils.Result;

/**
 * 大数据推集包规则接口
 */
public interface CollectBoxFlowDirectionConfPushJsfService {

    Result<Void> updateOrNewConfig(CollectBoxFlowDirectionPushConfDto conf);

}
