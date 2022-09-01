package com.jdl.basic.provider.core.provider.boxFlow;


import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.service.boxFlow.CollectBoxFlowDirectionConfJsfService;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionConfService;
import com.jdl.basic.provider.core.service.boxFlow.impl.CollectBoxFlowDirectionConfServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CollectBoxFlowDirectionConfJsfServiceImpl implements CollectBoxFlowDirectionConfJsfService {


    @Autowired
    private ICollectBoxFlowDirectionConfService collectBoxFlowDirectionConfService;

    @Override
    public Result<CollectBoxFlowDirectionConf> selectById(Long id) {
        if(log.isInfoEnabled()){

        }
        log.info("集包规则配置 selectById -{}",id);
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
}
