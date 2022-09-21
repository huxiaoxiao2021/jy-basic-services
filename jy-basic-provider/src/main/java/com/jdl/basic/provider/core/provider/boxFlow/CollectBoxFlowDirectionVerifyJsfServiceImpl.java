package com.jdl.basic.provider.core.provider.boxFlow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowDirectionConfReq;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowDirectionConfResp;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowFinishBoxReq;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowFinishBoxResp;
import com.jdl.basic.api.service.boxFlow.CollectBoxFlowDirectionVerifyJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/8/11 16:39
 * @Description:
 */
@Slf4j
@Service
public class CollectBoxFlowDirectionVerifyJsfServiceImpl implements CollectBoxFlowDirectionVerifyJsfService {

    @Autowired
    private ICollectBoxFlowDirectionVerifyService collectBoxFlowDirectionVerifyService;

    @Override
    public Result<CollectBoxFlowDirectionConf> verifyBoxFlowDirectionConf(CollectBoxFlowDirectionConf collectBoxFlowDirectionConf) {
        if (log.isInfoEnabled()) {
            log.info("集包规则校验接口 verifyBoxFlowDirectionConf -{}", JSON.toJSONString(collectBoxFlowDirectionConf));
        }
        return collectBoxFlowDirectionVerifyService.verifyBoxFlowDirectionConf(collectBoxFlowDirectionConf);
    }

    @Override
    public Result<CollectBoxFlowDirectionConfResp> verifyBoxFlowDirectionConfs(CollectBoxFlowDirectionConfReq req) {

        Result<CollectBoxFlowDirectionConfResp> r = collectBoxFlowDirectionVerifyService.verifyBoxFlowDirectionConfs(req);
        if (log.isInfoEnabled()) {
            log.info("流向校验校验接口 verifyBoxFlowDirectionConfs -{},返回：{}", JSON.toJSONString(req), JSONObject.toJSONString(r));
        }
        return r;
    }

    @Override
    public Result<CollectBoxFlowFinishBoxResp> ifBoxesWasFinishBox(CollectBoxFlowFinishBoxReq req) {

        Result<CollectBoxFlowFinishBoxResp> r = collectBoxFlowDirectionVerifyService.ifBoxesWasFinishBox(req);

        if (log.isInfoEnabled()) {
            log.info("校验是否为成品包 ifBoxesWasFinishBox -{},返回：{}", JSON.toJSONString(req), JSONObject.toJSONString(r));
        }

        return r;
    }
}
