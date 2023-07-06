package com.jdl.basic.provider.core.service.schedule;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.schedule.WorkGridSchedule;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleRequest;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.ApplicationLaunch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridScheduleServiceTest {

    @Autowired
    private WorkGridScheduleService workGridScheduleService;

    public void batchInsert() {

    }
    public void batchDeleteByWorkGridKey() {

    }

    public void batchQueryByWorkGridKey() {

    }

    @Test
    public void querySiteScheduleByCondition() {
        WorkGridScheduleRequest request = new WorkGridScheduleRequest();
        request.setSiteCode(910);
        Result<List<WorkGridSchedule>> result = workGridScheduleService.querySiteScheduleByCondition(request);
        log.info("result {}", JsonHelper.toJSONString(result));
    }
}
