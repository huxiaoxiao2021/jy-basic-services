package com.jdl.basic.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOffline;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOfflineQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.service.workStation.WorkGridFlowDetailOfflineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridFlowDetailOfflineServiceTest {


    @Autowired
    private WorkGridFlowDetailOfflineService workGridFlowDetailOfflineService;

    @Test
    public void queryPageListTest(){
        WorkGridFlowDetailOfflineQuery query = new WorkGridFlowDetailOfflineQuery();
        query.setFlowDirectionType("2");
        Result<PageDto<WorkGridFlowDetailOffline>> pageDtoResult = workGridFlowDetailOfflineService.queryPageList(query);
        System.out.println(JSON.toJSONString(pageDtoResult));
    }

}
