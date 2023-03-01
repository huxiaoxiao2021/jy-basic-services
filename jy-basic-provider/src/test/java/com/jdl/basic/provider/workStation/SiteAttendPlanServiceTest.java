package com.jdl.basic.provider.workStation;

import com.jdl.basic.api.domain.workStation.SiteAttendPlan;
import com.jdl.basic.api.domain.workStation.SiteAttendPlanQuery;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.service.workStation.SiteAttendPlanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class SiteAttendPlanServiceTest {
    @Autowired
    private SiteAttendPlanService siteAttendPlanService;

    @Test
    public void queryPageListTest() throws ParseException {
        SiteAttendPlanQuery query = new SiteAttendPlanQuery();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        query.setStartTime(sdf.parse("2023-03-01 00:00:00"));
        query.setEndTime(sdf.parse("2023-03-04 23:59:59"));
        Result<List<SiteAttendPlan>> result = siteAttendPlanService.queryPageList(query);
        log.info("queryPageList result {}", JsonHelper.toJSONString(result));
    }

    @Test
    public void queryCountTest() throws ParseException {
        SiteAttendPlanQuery query = new SiteAttendPlanQuery();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        query.setStartTime(sdf.parse("2023-03-01 00:00:00"));
        query.setEndTime(sdf.parse("2023-03-04 23:59:59"));
        Result<Long> result = siteAttendPlanService.queryTotalCount(query);
        log.info("queryPageList result {}", JsonHelper.toJSONString(result));
    }
}
