package com.jdl.basic.provider.boxFlow;

import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionPushConfDto;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.provider.boxFlow.CollectBoxFlowDirectionConfPushJsfServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class CollectBoxFlowDirectionConfPushJsfServiceImplTest {
    @Autowired
    private CollectBoxFlowDirectionConfPushJsfServiceImpl collectBoxFlowDirectionConfPushJsfService;

    @Test
    public void updateOrNewConfig() {
        String json = "{\"startSiteId\": 908, \"startSiteName\": \"广州萝岗分拣中心\", \"endSiteId\": 908, \"endSiteName\": \"广州萝岗分拣中心\", \"boxReceiveId\": 910, \"boxReceiveName\": \"广州黄埔集货分拣中心\", \"boxPkgName\": \"广州黄埔集货混\", \"transportType\": 1, \"flowType\": 2, \"collectClaim\": 2, \"updateDate\": \"202302221904\"}";
        CollectBoxFlowDirectionPushConfDto dto = new CollectBoxFlowDirectionPushConfDto();
        dto = JsonHelper.toObject(json, CollectBoxFlowDirectionPushConfDto.class);
        Result<Void> result = collectBoxFlowDirectionConfPushJsfService.updateOrNewConfig(dto);
        
    }
}
