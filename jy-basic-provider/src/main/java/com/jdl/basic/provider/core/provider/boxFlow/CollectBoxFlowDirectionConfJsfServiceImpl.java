package com.jdl.basic.provider.core.provider.boxFlow;


import com.alibaba.fastjson.JSON;
import com.jd.jim.cli.Cluster;
import com.jd.lsb.task.domain.Request;
import com.jd.lsb.task.domain.Response;
import com.jd.lsb.task.handler.Handler;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.service.boxFlow.CollectBoxFlowDirectionConfJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionConfService;
import com.jdl.basic.provider.core.service.boxFlow.impl.CollectBoxFlowDirectionConfServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CollectBoxFlowDirectionConfJsfServiceImpl implements CollectBoxFlowDirectionConfJsfService, Handler {

    public static String SWITCH_NEW_VERSION_KEY = "SWITCH-NEW-VERSION-KEY";
    
    @Autowired
    private ICollectBoxFlowDirectionConfService collectBoxFlowDirectionConfService;

    @Autowired
    Cluster cluster;

    @Override
    public Result<CollectBoxFlowDirectionConf> selectById(Long id) {
        if(log.isInfoEnabled()){
            log.info("集包规则配置 selectById -{}",id);
        }
        return collectBoxFlowDirectionConfService.selectById(id);
    }

    @Override
    public Result<Boolean> newConfig(CollectBoxFlowDirectionConf conf) {
        log.info("集包规则配置 newConfig -{}", JSON.toJSONString(conf));
        return collectBoxFlowDirectionConfService.newConfig(conf);
    }

    @Override
    public Result<Boolean> updateConfig(CollectBoxFlowDirectionConf conf) {
        log.info("集包规则配置 updateConfig -{}",JSON.toJSONString(conf));
        return collectBoxFlowDirectionConfService.updateConfig(conf);
    }

    @Override
    public Result<Pager<CollectBoxFlowDirectionConf>> listByParamAndWhetherConfiged(Pager<CollectBoxFlowDirectionConf> pager, Boolean configed) {
        if(log.isInfoEnabled()){
            log.info("集包规则配置 listByParamAndWhetherConfiged {} ,{}",JSON.toJSONString(pager),configed);
        }
        return collectBoxFlowDirectionConfService.listByParamAndWhetherConfiged(pager,configed);
    }

    @Override
    public Result<Boolean> rollbackVersion() {
        Result<Boolean> result = new Result<>();
        result.toSuccess();
        try {
            if(cluster.set(SWITCH_NEW_VERSION_KEY, "1", 20, TimeUnit.MINUTES, false)){
                result = collectBoxFlowDirectionConfService.rollbackVersion();
            }else {
                result.toFail("版本正在切换，不能频繁操作，请稍后");
            }
        }catch (Exception e){
            log.error("定时切换集包版本异常", e);
            result.toFail("服务异常请联系值班研发查看");
            throw e;
        }finally {
            cluster.del(SWITCH_NEW_VERSION_KEY);
        }
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionConfJsfServiceImpl.handle", mState = {JProEnum.TP, JProEnum.FunctionError})
    public void handle(Request request, Response response) throws Throwable {
        try {
            if(cluster.set(SWITCH_NEW_VERSION_KEY, "1", 20, TimeUnit.MINUTES, false)){
                collectBoxFlowDirectionConfService.switchNewVersion();
            }
        }catch (Exception e){
            log.error("定时切换集包版本异常", e);
            throw e;
        }finally {
            cluster.del(SWITCH_NEW_VERSION_KEY);
        }
        
    }
    
}
