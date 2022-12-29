package com.jdl.basic.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.WorkStation;
import com.jdl.basic.api.service.workStation.WorkStationJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/9/1 16:43
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkStationJsfServiceTest {

    @Autowired
    private WorkStationJsfService workStationJsfService;

    @Test
    public void queryByIdTest(){
        Result<WorkStation> workStationResult = workStationJsfService.queryById(10L);
        System.out.println(JSON.toJSONString(workStationResult));
    }

    @Test
    public void queryWorkStationBybusinessKeyWithCacheTest(){
        Result<WorkStation> result = workStationJsfService.queryWorkStationBybusinessKeyWithCache("GX00000005006");
        System.out.println(JSON.toJSONString(result));
    }
}
