package com.jdl.basic.provider.core.provider.boxFlow;

import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.service.boxFlow.CollectBoxFlowDirectionVerifyJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/8/11 16:39
 * @Description:
 */
@Service
public class CollectBoxFlowDirectionVerifyJsfServiceImpl implements CollectBoxFlowDirectionVerifyJsfService {

    @Autowired
    private ICollectBoxFlowDirectionVerifyService collectBoxFlowDirectionVerifyService;

    @Override
    public Result<CollectBoxFlowDirectionConf> verifyBoxFlowDirectionConf(CollectBoxFlowDirectionConf collectBoxFlowDirectionConf) {
        return collectBoxFlowDirectionVerifyService.verifyBoxFlowDirectionConf(collectBoxFlowDirectionConf);
    }
}
