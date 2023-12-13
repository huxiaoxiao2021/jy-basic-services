package com.jdl.basic.provider.workStation;

import com.jdl.basic.api.domain.workStation.WorkGridFlowDirection;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionQuery;
import com.jdl.basic.common.utils.JsonHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOffline;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOfflineQuery;
import com.jdl.basic.api.service.workStation.WorkGridFlowDirectionJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridFlowDirectionJsfServiceTest {


    @Autowired
    private WorkGridFlowDirectionJsfService workGridFlowDirectionJsfService;

    @Test
    public void init(){
    	workGridFlowDirectionJsfService.initWorkGridFlowOffline();
    }

    @Test
    public void queryFlowByPositionCodeTest() {
        WorkGridFlowDirectionQuery query = new WorkGridFlowDirectionQuery();
        query.setWorkStationGridKey("CDGX00000143004");
        query.setLineType(3);
        query.setFlowDirectionType(2);
        Result<List<WorkGridFlowDirection>> result = workGridFlowDirectionJsfService.queryFlowByPositionCode(query);
        System.out.println(JsonHelper.toJSONString(result));
    }

}
