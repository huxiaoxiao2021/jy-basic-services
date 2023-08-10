package com.jdl.basic.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.api.service.workStation.WorkStationGridJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.common.Jimdb.CacheService;
import com.jdl.basic.provider.core.dao.workStation.WorkStationGridDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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
    @Qualifier("workStationGridDao")
    private WorkStationGridDao workStationGridDao;

    @Resource
    @Qualifier("JimdbCacheService")
    private CacheService cacheService;

    @Test
    public void queryByIdTest(){
        Result<WorkStationGrid> workStationGridResult = workStationGridJsfService.queryById(10L);
        System.out.println(JSON.toJSONString(workStationGridResult));
        workStationGridJsfService.initAllWorkGrid();
    }

    @Test
    public void testQueryWorkStationGridBybusinessKeyWithCache(){
        String businessKey = "CDGX000000290011";
        WorkStationGrid workStationGrid = workStationGridDao.queryWorkStationGridBybusinessKeyWithCache(businessKey);
        Assert.assertNotNull(workStationGrid);
        String cacheKey = "WorkStationGridDao.queryWorkStationGridBybusinessKeyWithCache" + businessKey;
        String workStationGridJson1 = cacheService.get(cacheKey);
        Assert.assertNotNull(workStationGridJson1);
        cacheService.del(cacheKey);
        String workStationGridJson2 = cacheService.get(cacheKey);
        Assert.assertNull(workStationGridJson2);
    }

}

