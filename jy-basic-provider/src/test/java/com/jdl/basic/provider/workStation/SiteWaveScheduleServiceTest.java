package com.jdl.basic.provider.workStation;

import com.jdl.basic.api.domain.workStation.SiteWaveScheduleQuery;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.service.workStation.SiteWaveScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class SiteWaveScheduleServiceTest {

    @Autowired
    SiteWaveScheduleService siteWaveScheduleService;

    @Test
    public void importDatasTest(){

    }

    @Test
    public void queryPageTest(){

        SiteWaveScheduleQuery query = new SiteWaveScheduleQuery();
        query.setOrgCode(6);
        query.setSiteCode(null);
        query.setPageSize(500);
        System.out.println(JsonHelper.toJSONString(siteWaveScheduleService.queryPageList(query)));
    }
}
