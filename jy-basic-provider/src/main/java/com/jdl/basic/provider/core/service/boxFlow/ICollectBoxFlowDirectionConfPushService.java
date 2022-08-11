package com.jdl.basic.provider.core.service.boxFlow;


import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionPushConfDto;
import com.jdl.basic.common.utils.Result;

/**
 * 大数据推集包规则接口
 */
public interface ICollectBoxFlowDirectionConfPushService {

    Result<Void> updateOrNewConfig(CollectBoxFlowDirectionPushConfDto conf);

}
