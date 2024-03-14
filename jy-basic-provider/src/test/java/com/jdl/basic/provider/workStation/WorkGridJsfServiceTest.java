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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        workGridJsfService.queryByWorkGridKeyWithCache("CDWG00000022007");
        Result<WorkGrid> result = workGridJsfService.queryByWorkGridKeyWithCache("CDWG00000022007");
        log.info(JsonHelper.toJSONString(result));
    }

    @Test
    public void queryAreaInfo() {
        WorkGrid workGrid = new WorkGrid();
        Result<List<WorkGrid>> listResult1 = workGridJsfService.queryAreaInfo(workGrid);
        List<HashMap> collect1 = listResult1.getData().stream().map(x -> {
            HashMap map = new HashMap();
            map.put("AreaCode", x.getAreaCode());
            map.put("AreaName", x.getAreaName());
            return map;
        }).collect(Collectors.toList());
        log.error("查询全国场地，{}",collect1);
        workGrid.setProvinceAgencyCode("110000");
        workGrid.setAreaHubCode(null);
        workGrid.setSiteCode(null);
        Result<List<WorkGrid>> listResult2 = workGridJsfService.queryAreaInfo(workGrid);
        List<HashMap> collect2 = listResult2.getData().stream().map(x -> {
            HashMap map = new HashMap();
            map.put("AreaCode", x.getAreaCode());
            map.put("AreaName", x.getAreaName());
            return map;
        }).collect(Collectors.toList());
        log.error("查询省区场地，{}",collect2);
        workGrid.setProvinceAgencyCode(null);
        workGrid.setAreaHubCode("100204");
        workGrid.setSiteCode(null);
        Result<List<WorkGrid>> listResult3 = workGridJsfService.queryAreaInfo(workGrid);
        List<HashMap> collect3 = listResult3.getData().stream().map(x -> {
            HashMap map = new HashMap();
            map.put("AreaCode", x.getAreaCode());
            map.put("AreaName", x.getAreaName());
            return map;
        }).collect(Collectors.toList());
        log.error("查询枢纽场地，{}",collect3);
        workGrid.setProvinceAgencyCode(null);
        workGrid.setAreaHubCode(null);
        workGrid.setSiteCode(910);
        Result<List<WorkGrid>> listResult4 = workGridJsfService.queryAreaInfo(workGrid);
        List<HashMap> collect4 = listResult4.getData().stream().map(x -> {
            HashMap map = new HashMap();
            map.put("AreaCode", x.getAreaCode());
            map.put("AreaName", x.getAreaName());
            return map;
        }).collect(Collectors.toList());
        log.error("查询站点场地，{}",collect4);
    }
}

