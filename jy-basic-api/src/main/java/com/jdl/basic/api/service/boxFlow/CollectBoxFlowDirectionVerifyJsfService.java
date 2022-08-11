package com.jdl.basic.api.service.boxFlow;


import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.common.utils.Result;


public interface CollectBoxFlowDirectionVerifyJsfService {

    Result<CollectBoxFlowDirectionConf> verifyBoxFlowDirectionConf(CollectBoxFlowDirectionConf collectBoxFlowDirectionConf);
}
