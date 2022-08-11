package com.jdl.basic.provider.core.provider.boxFlow;


import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionPushConfDto;
import com.jdl.basic.api.service.boxFlow.CollectBoxFlowDirectionConfPushJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionConfPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 大数据推集包规则接口
 */
@Service
public class CollectBoxFlowDirectionConfPushJsfServiceImpl implements CollectBoxFlowDirectionConfPushJsfService {

    @Autowired
    private ICollectBoxFlowDirectionConfPushService collectBoxFlowDirectionConfPushService;

    @Override
    public Result<Void> updateOrNewConfig(CollectBoxFlowDirectionPushConfDto conf) {
        return collectBoxFlowDirectionConfPushService.updateOrNewConfig(conf);
    }
}
