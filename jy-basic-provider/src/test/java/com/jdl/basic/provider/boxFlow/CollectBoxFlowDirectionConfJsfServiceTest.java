package com.jdl.basic.provider.boxFlow;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowDirectionConfReq;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowDirectionConfResp;
import com.jdl.basic.api.service.boxFlow.CollectBoxFlowDirectionConfJsfService;
import com.jdl.basic.api.service.boxFlow.CollectBoxFlowDirectionVerifyJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/9/1 15:41
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class CollectBoxFlowDirectionConfJsfServiceTest {

    @Autowired
    private CollectBoxFlowDirectionConfJsfService collectBoxFlowDirectionConfJsfService;


    @Autowired
    CollectBoxFlowDirectionVerifyJsfService verifyJsfService;

    @Test
    public void selectByIdTest() {
        Result<CollectBoxFlowDirectionConf> result = collectBoxFlowDirectionConfJsfService.selectById(1L);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void ifBoxesWasFinishBox() {

    }

    @Test
    public void verifyBoxFlowDirectionConfs() {
        CollectBoxFlowDirectionConfReq req = new CollectBoxFlowDirectionConfReq();
        req.setStartSiteId(1);
        req.setEndSiteId(Arrays.asList(2, 3));
        req.setTransportType(CollectBoxFlowDirectionConf.TRANSPORT_TYPE_HIGHWAY);
        req.setFlowType(CollectBoxFlowDirectionConf.FLOW_TYPE_OUT);
        req.setBoxReceiveId(3);
        Result<CollectBoxFlowDirectionConfResp> result = verifyJsfService.verifyBoxFlowDirectionConfs(req);
        int a = 1;
    }


}
