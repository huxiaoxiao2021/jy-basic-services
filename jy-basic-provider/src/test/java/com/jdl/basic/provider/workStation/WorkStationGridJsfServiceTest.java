package com.jdl.basic.provider.workStation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.api.domain.workStation.WorkStationGridQuery;
import com.jdl.basic.api.service.workStation.WorkStationGridJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.service.workStation.WorkStationGridService;

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
    @Autowired
    private WorkStationGridService workStationGridService;    
    @Test
    public void queryByBusinessKeyTest(){
    	WorkStationGrid insertData = new WorkStationGrid();
    	insertData.setAreaCode("test1");
    	insertData.setAreaName("testName1");
    	insertData.setWorkCode("test1-test");
    	insertData.setWorkName("test1-1");
    	insertData.setRefStationKey("GX000000230011");
    	insertData.setSiteCode(910);
    	insertData.setFloor(1);
    	insertData.setGridNo("01");
    	insertData.setGridCode("test1-01");
    	insertData.setStandardNum(10);
    	insertData.setOwnerUserErp("ww");
    	
    	WorkStationGrid queryData = new WorkStationGrid();
    	BeanUtils.copyProperties(insertData, queryData);
    	WorkStationGridQuery queryData1 = new WorkStationGridQuery();
    	BeanUtils.copyProperties(insertData, queryData1);
    	System.out.println("插入数据："+JSON.toJSONString(workStationGridService.insert(insertData)));
    	
        System.out.println("删除前："+JSON.toJSONString(workStationGridService.queryByBusinessKey(queryData)));
        System.out.println("删除前："+JSON.toJSONString(workStationGridService.queryByBusinessKeyWithCache(queryData)));
        System.out.println("删除前-jsf："+JSON.toJSONString(workStationGridJsfService.queryByBusinessKey(queryData)));
        System.out.println("删除前-jsf1："+JSON.toJSONString(workStationGridJsfService.queryByGridKey(queryData1)));
        workStationGridService.deleteById(insertData);
        System.out.println("删除后："+JSON.toJSONString(workStationGridService.queryByBusinessKey(queryData)));
        System.out.println("删除后："+JSON.toJSONString(workStationGridService.queryByBusinessKeyWithCache(queryData)));
        System.out.println("删除后-jsf："+JSON.toJSONString(workStationGridJsfService.queryByBusinessKey(queryData)));
        System.out.println("删除后-jsf1："+JSON.toJSONString(workStationGridJsfService.queryByGridKey(queryData1)));
        
    }
    @Test
    public void queryByIdTest(){
        Result<WorkStationGrid> workStationGridResult = workStationGridJsfService.queryById(10L);
        System.out.println(JSON.toJSONString(workStationGridResult));
    }
}

