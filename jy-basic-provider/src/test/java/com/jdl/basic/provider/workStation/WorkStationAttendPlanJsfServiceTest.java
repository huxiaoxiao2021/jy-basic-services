package com.jdl.basic.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.WorkStationAttendPlan;
import com.jdl.basic.api.service.workStation.WorkStationAttendPlanJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/9/1 16:31
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkStationAttendPlanJsfServiceTest {

    @Autowired
    private WorkStationAttendPlanJsfService workStationAttendPlanJsfService;

    @Test
    public void queryByIdTest(){
        Result<WorkStationAttendPlan> result = workStationAttendPlanJsfService.queryById(12L);
        System.out.println(JSON.toJSONString(result));
    }
}
