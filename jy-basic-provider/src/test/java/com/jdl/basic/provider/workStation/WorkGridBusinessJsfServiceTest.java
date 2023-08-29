package com.jdl.basic.provider.workStation;

import com.jdl.basic.api.domain.workStation.DockCodeAndPhoneQuery;
import com.jdl.basic.api.service.workStation.WorkGridBusinessJsfService;
import com.jdl.basic.api.service.workStation.WorkGridFlowDirectionJsfService;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridBusinessJsfServiceTest {


    @Autowired
    private WorkGridBusinessJsfService workGridBusinessJsfService;

    @Test
    public void test(){
        DockCodeAndPhoneQuery dockCodeAndPhoneQuery = new DockCodeAndPhoneQuery();
        dockCodeAndPhoneQuery.setStartSiteID("010F016");
        dockCodeAndPhoneQuery.setEndSiteID("010F002");
        dockCodeAndPhoneQuery.setFlowDirectionType(1);
        workGridBusinessJsfService.queryDockCodeByFlowDirection(dockCodeAndPhoneQuery);
    }

}
