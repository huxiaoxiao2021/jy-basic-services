package com.jdl.basic.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.WorkStation;
import com.jdl.basic.api.service.workStation.WorkStationJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.service.workStation.WorkStationService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private WorkStationService workStationService;    

    @Test
    public void queryByIdTest(){
        Result<WorkStation> workStationResult = workStationJsfService.queryById(10L);
        System.out.println(JSON.toJSONString(workStationResult));
    }
    @Test
    public void queryByBusinessKeyTest(){
    	WorkStation insertData = new WorkStation();
    	insertData.setAreaCode("test1");
    	insertData.setAreaName("testName1");
    	insertData.setWorkCode("test1-1");
    	insertData.setWorkName("test1-1");
    	
    	WorkStation queryData = new WorkStation();
    	BeanUtils.copyProperties(insertData, queryData);
    	
    	workStationService.insert(insertData);
    	
        System.out.println("删除前："+JSON.toJSONString(workStationService.queryByBusinessKey(queryData)));
        System.out.println("删除前："+JSON.toJSONString(workStationService.queryByBusinessKeyWithCache(queryData)));
        System.out.println("删除前-jsf："+JSON.toJSONString(workStationJsfService.queryByBusinessKey(queryData)));
        System.out.println("删除前-jsf1："+JSON.toJSONString(workStationJsfService.queryByRealBusinessKey(insertData.getBusinessKey())));
        workStationService.deleteById(insertData);
        System.out.println("删除后："+JSON.toJSONString(workStationService.queryByBusinessKey(queryData)));
        System.out.println("删除后："+JSON.toJSONString(workStationService.queryByBusinessKeyWithCache(queryData)));
        System.out.println("删除后-jsf："+JSON.toJSONString(workStationJsfService.queryByBusinessKey(queryData)));
        System.out.println("删除后-jsf1："+JSON.toJSONString(workStationJsfService.queryByRealBusinessKey(insertData.getBusinessKey())));
        
    }    
}
