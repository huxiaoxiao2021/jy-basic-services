package com.jdl.basic.provider.core.provider.boxFlow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.lsb.task.domain.Request;
import com.jd.lsb.task.domain.Response;
import com.jd.lsb.task.handler.Handler;
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

import java.util.concurrent.*;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/8/11 16:39
 * @Description:
 */
@Slf4j
@Service
public class CollectBoxFlowDirectionVerifyJsfServiceImpl implements CollectBoxFlowDirectionVerifyJsfService, Handler {
    private ExecutorService checkAllMixableRouteExecutorService= new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1));

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

    /**
     * 混包校验路由
     * 只校验可混包
     * @param request
     * @param response
     * @throws Throwable
     */
    @Override
    public void handle(Request request, Response response) throws Throwable {
        try {
            checkAllMixableRouteExecutorService.execute(() -> collectBoxFlowDirectionVerifyService.checkAllMixableRoute());
        } catch (RejectedExecutionException e) {
            log.warn("checkAllMixableRoute 正在运行，拒绝");
        }
    }
}
