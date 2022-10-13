package com.jdl.basic.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.api.service.workStation.WorkStationGridJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/9/1 16:37
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkStationGridJsfServiceTest {

    @Autowired
    private WorkStationGridJsfService workStationGridJsfService;

    @Test
    public void queryByIdTest(){
        Result<WorkStationGrid> workStationGridResult = workStationGridJsfService.queryById(10L);
        System.out.println(JSON.toJSONString(workStationGridResult));
    }
}

