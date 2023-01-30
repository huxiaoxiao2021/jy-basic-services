package com.jdl.basic.provider.position;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.position.PositionDetailRecord;
import com.jdl.basic.api.domain.position.PositionQuery;
import com.jdl.basic.api.service.position.PositionQueryJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/9/1 16:11
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class PositionQueryJsfServiceTest {

    @Autowired
    private PositionQueryJsfService positionQueryJsfService;

    @Test
    public void queryPageListTest(){
        PositionQuery positionQuery = new PositionQuery();
        positionQuery.setSiteCode(910);
        positionQuery.setPageSize(10);
        positionQuery.setPageNumber(1);
        positionQuery.setFuncCode("UNSEAL_CAR_POSITION");
        Result<PageDto<PositionDetailRecord>> pageDtoResult = positionQueryJsfService.queryPageList(positionQuery);
        System.out.println(JSON.toJSONString(pageDtoResult));
    }

    @Test
    public void queryDetailTest(){
        try {
            String positionQuery = "GW00010021";
            Result<PositionDetailRecord> result = positionQueryJsfService.queryOneByPositionCode(positionQuery);
            Assert.assertTrue(true);
        }catch (Exception e){
            Assert.fail();
        }
    }
}
