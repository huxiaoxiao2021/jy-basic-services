package com.jdl.basic.provider.dao.itBasic;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.itBasic.ItBasicStorageIpRegion;
import com.jdl.basic.api.dto.itBasic.po.ItBasicIpRegionPo;
import com.jdl.basic.api.dto.itBasic.qo.ItBasicStorageIpRegionQo;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.dao.itBasic.ItBasicStorageIpRegionDao;
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
public class ItBasicStorageIpRegionDaoTest {

    @Autowired
    private ItBasicStorageIpRegionDao itBasicStorageIpRegionDao;

    @Test
    public void queryListTest(){
        final ItBasicStorageIpRegionQo itBasicStorageIpRegionQo = new ItBasicStorageIpRegionQo();
        itBasicStorageIpRegionQo.setYn(1);
        itBasicStorageIpRegionQo.setPageNumber(1);
        itBasicStorageIpRegionQo.setPageSize(2);
        final List<ItBasicStorageIpRegion> dataList = itBasicStorageIpRegionDao.queryList(itBasicStorageIpRegionQo);
        System.out.println(JSON.toJSONString(dataList));
        Assert.assertTrue(true);
    }

    @Test
    public void queryCountTest(){
        final ItBasicStorageIpRegionQo itBasicStorageIpRegionQo = new ItBasicStorageIpRegionQo();
        itBasicStorageIpRegionQo.setYn(1);
        itBasicStorageIpRegionQo.setPageNumber(1);
        itBasicStorageIpRegionQo.setPageSize(2);
        final long total = itBasicStorageIpRegionDao.queryCount(itBasicStorageIpRegionQo);
        System.out.println(total);
        Assert.assertTrue(true);
    }

    @Test
    public void insertTest(){
        try {
            final ItBasicStorageIpRegion itBasicStorageIpRegion = new ItBasicStorageIpRegion();
            itBasicStorageIpRegion.setStorageId(1235345l);
            itBasicStorageIpRegion.setStartIp(1325345345L);
            itBasicStorageIpRegion.setEndIp(1325345385L);
            final int insert = itBasicStorageIpRegionDao.insertSelective(itBasicStorageIpRegion);
            System.out.println(insert);
            Assert.assertTrue(true);
        }catch (Exception e){
            log.error("error ", e);
            Assert.fail();
        }
    }

    @Test
    public void selectMatchRegionByIpTest(){
        final ItBasicIpRegionPo itBasicIpRegionPo = new ItBasicIpRegionPo();
        itBasicIpRegionPo.setIp("10.63.174.134");
        final ItBasicStorageIpRegion itBasicStorageIpRegion = itBasicStorageIpRegionDao.selectMatchRegionByIp(itBasicIpRegionPo);
        System.out.println(JSON.toJSON(itBasicStorageIpRegion));
        Assert.assertTrue(true);
    }
}
