package com.jdl.basic.provider.core.service.boxFlow;


import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.common.utils.Result;


public interface ICollectBoxFlowDirectionVerifyService {

    Result<CollectBoxFlowDirectionConf> verifyBoxFlowDirectionConf(CollectBoxFlowDirectionConf collectBoxFlowDirectionConf);
}
