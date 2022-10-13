package com.jdl.basic.provider.core.service.boxFlow;


import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowDirectionConfReq;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowDirectionConfResp;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowFinishBoxReq;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowFinishBoxResp;
import com.jdl.basic.common.utils.Result;


public interface ICollectBoxFlowDirectionVerifyService {

    Result<CollectBoxFlowDirectionConf> verifyBoxFlowDirectionConf(CollectBoxFlowDirectionConf collectBoxFlowDirectionConf);
    /**
     * 流向校验
     * @param req
     * @return
     */
    Result<CollectBoxFlowDirectionConfResp> verifyBoxFlowDirectionConfs(CollectBoxFlowDirectionConfReq req);


    /**
     * 校验是否为成品包
     *
     * @param req
     * @return true是成品包，false 不是成品包
     */
    Result<CollectBoxFlowFinishBoxResp> ifBoxesWasFinishBox(CollectBoxFlowFinishBoxReq req);
}
