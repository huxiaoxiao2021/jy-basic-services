package com.jdl.basic.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.WorkStationAttendPlan;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.api.domain.workStation.WorkStationGridQuery;
import com.jdl.basic.api.service.workStation.WorkStationAttendPlanJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.service.workStation.WorkStationAttendPlanService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private WorkStationAttendPlanService workStationAttendPlanService;
    @Test
    public void queryByBusinessKeyTest(){
    	WorkStationAttendPlan insertData = new WorkStationAttendPlan();
    	insertData.setAreaCode("test1");
    	insertData.setAreaName("testName1");
    	insertData.setWorkCode("test1-test");
    	insertData.setWorkName("test1-1");
    	insertData.setSiteCode(910);
    	insertData.setFloor(1);
    	insertData.setGridNo("02");
    	insertData.setGridCode("test1-01");
    	insertData.setPlanAttendNum(10);
    	insertData.setPlanName("test");
    	insertData.setWaveCode(1);
    	insertData.setRefStationKey("GX000000230011");
    	insertData.setRefGridKey("CDGX00000101001");
    	
    	WorkStationAttendPlan queryData = new WorkStationAttendPlan();
    	BeanUtils.copyProperties(insertData, queryData);
    	System.out.println("插入数据："+JSON.toJSONString(workStationAttendPlanService.insert(insertData)));
    	
        System.out.println("删除前："+JSON.toJSONString(workStationAttendPlanService.queryByBusinessKeys(queryData)));
        System.out.println("删除前："+JSON.toJSONString(workStationAttendPlanService.queryByBusinessKeysWithCache(queryData)));
        System.out.println("删除前-jsf："+JSON.toJSONString(workStationAttendPlanJsfService.queryByBusinessKeys(queryData)));
        workStationAttendPlanService.deleteById(insertData);
        System.out.println("删除后："+JSON.toJSONString(workStationAttendPlanService.queryByBusinessKeys(queryData)));
        System.out.println("删除后："+JSON.toJSONString(workStationAttendPlanService.queryByBusinessKeysWithCache(queryData)));
        System.out.println("删除后-jsf："+JSON.toJSONString(workStationAttendPlanJsfService.queryByBusinessKeys(queryData)));
        
    }
    @Test
    public void queryByIdTest(){
        Result<WorkStationAttendPlan> result = workStationAttendPlanJsfService.queryById(12L);
        System.out.println(JSON.toJSONString(result));
    }
}
