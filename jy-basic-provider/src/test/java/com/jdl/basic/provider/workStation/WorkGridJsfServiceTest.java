package com.jdl.basic.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.BatchAreaWorkGridQuery;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.api.service.workStation.WorkGridJsfService;
import com.jdl.basic.api.service.workStation.WorkStationGridJsfService;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/9/1 16:37
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridJsfServiceTest {

    @Autowired
    private WorkStationGridJsfService workStationGridJsfService;

    @Autowired
    private WorkGridJsfService workGridJsfService;

    @Test
    public void queryByIdTest(){
        Result<WorkStationGrid> workStationGridResult = workStationGridJsfService.queryById(10L);
        System.out.println(JSON.toJSONString(workStationGridResult));
        workStationGridJsfService.initAllWorkGrid();
    }

    @Test
    public void batchQueryAreaWorkGridTest() {
        BatchAreaWorkGridQuery query = new BatchAreaWorkGridQuery();
        query.setSiteCodes(Arrays.asList(910, 40240));
        query.setAreaCodes(Arrays.asList("BDXC", "ZYXCQ"));
        Result<List<WorkGrid>> result = workGridJsfService.batchQueryAreaWorkGrid(query);
        log.info("result {}", JsonHelper.toJSONString(result));
    }
    
    @Test
    public void queryByWorkGridKeyWithCacheTest() {
        workGridJsfService.queryByWorkGridKeyWithCache("CDWG00000022017");
        Result<WorkGrid> result = workGridJsfService.queryByWorkGridKeyWithCache("CDWG00000022017");
        log.info(JsonHelper.toJSONString(result));
    }
}

