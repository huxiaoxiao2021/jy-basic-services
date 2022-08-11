package com.jdl.basic.provider.core.provider.boxFlow;


import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.service.boxFlow.CollectBoxFlowDirectionConfJsfService;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.boxFlow.impl.CollectBoxFlowDirectionConfServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectBoxFlowDirectionConfJsfServiceImpl implements CollectBoxFlowDirectionConfJsfService {


    @Autowired
    private CollectBoxFlowDirectionConfServiceImpl collectBoxFlowDirectionConfService;

    @Override
    public CollectBoxFlowDirectionConf selectById(Long id) {
        return collectBoxFlowDirectionConfService.selectById(id);
    }

    @Override
    public Result<Boolean> newConfig(CollectBoxFlowDirectionConf conf) {
        return collectBoxFlowDirectionConfService.newConfig(conf);
    }

    @Override
    public Result<Boolean> updateConfig(CollectBoxFlowDirectionConf conf) {
        return collectBoxFlowDirectionConfService.updateConfig(conf);
    }

    @Override
    public Result<Pager<CollectBoxFlowDirectionConf>> listByParamAndWhetherConfiged(Pager<CollectBoxFlowDirectionConf> pager, Boolean configed) {
        return collectBoxFlowDirectionConfService.listByParamAndWhetherConfiged(pager,configed);
    }
}
