package com.jdl.basic.provider.core.provider.boxFlow;


import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionPushConfDto;
import com.jdl.basic.api.service.boxFlow.CollectBoxFlowDirectionConfPushJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionConfPushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 大数据推集包规则接口
 */
@Slf4j
@Service
public class CollectBoxFlowDirectionConfPushJsfServiceImpl implements CollectBoxFlowDirectionConfPushJsfService {

    @Autowired
    private ICollectBoxFlowDirectionConfPushService collectBoxFlowDirectionConfPushService;

    @Override
    public Result<Void> updateOrNewConfig(CollectBoxFlowDirectionPushConfDto conf) {
        if(log.isInfoEnabled()){
            log.info("大数据推集包规则接口 updateOrNewConfig -{}", JSON.toJSONString(conf));
        }
        return collectBoxFlowDirectionConfPushService.updateOrNewConfig(conf);
    }
}
