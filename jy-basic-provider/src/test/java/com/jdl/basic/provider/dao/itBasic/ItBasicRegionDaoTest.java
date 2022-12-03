package com.jdl.basic.provider.dao.itBasic;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.itBasic.ItBasicRegion;
import com.jdl.basic.api.dto.itBasic.qo.ItBasicRegionQo;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.dao.itBasic.ItBasicRegionDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 设备配置单测
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class ItBasicRegionDaoTest {

    @Autowired
    private ItBasicRegionDao itBasicRegionDao;

    @Test
    public void queryListTest(){
        final ItBasicRegionQo itBasicRegionQo = new ItBasicRegionQo();
        itBasicRegionQo.setYn(1);
        itBasicRegionQo.setPageNumber(1);
        itBasicRegionQo.setPageSize(2);
        final List<ItBasicRegion> dataList = itBasicRegionDao.queryList(itBasicRegionQo);
        System.out.println(JSON.toJSONString(dataList));
        Assert.assertTrue(true);
    }

    @Test
    public void insertTest(){
        try {
            final ItBasicRegion itBasicRegion = new ItBasicRegion();
            itBasicRegion.setRegionName("测试区111");
            itBasicRegion.setParentId(0L);
            itBasicRegion.setRegionLevel(1);
            final int insert = itBasicRegionDao.insertSelective(itBasicRegion);
            System.out.println(insert);
            Assert.assertTrue(true);
        }catch (Exception e){
            log.error("errror ", e);
            Assert.fail();
        }
    }
}
