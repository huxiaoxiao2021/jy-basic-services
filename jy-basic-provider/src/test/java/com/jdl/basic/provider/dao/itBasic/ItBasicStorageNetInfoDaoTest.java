package com.jdl.basic.provider.dao.itBasic;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.itBasic.ItBasicStorageNetInfo;
import com.jdl.basic.api.dto.itBasic.qo.ItBasicStorageNetInfoQo;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.dao.itBasic.ItBasicStorageNetInfoDao;
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
public class ItBasicStorageNetInfoDaoTest {

    @Autowired
    private ItBasicStorageNetInfoDao itBasicStorageNetInfoDao;

    @Test
    public void queryListTest(){
        final ItBasicStorageNetInfoQo itBasicStorageNetInfoQo = new ItBasicStorageNetInfoQo();
        itBasicStorageNetInfoQo.setPageNumber(1);
        itBasicStorageNetInfoQo.setPageSize(2);
        itBasicStorageNetInfoQo.setYn(1);
        final List<ItBasicStorageNetInfo> dataList = itBasicStorageNetInfoDao.queryList(itBasicStorageNetInfoQo);
        System.out.println(JSON.toJSONString(dataList));
        Assert.assertTrue(true);
    }

    @Test
    public void insertTest(){
        try {
            final ItBasicStorageNetInfo ItBasicStorageNetInfo = new ItBasicStorageNetInfo();
            ItBasicStorageNetInfo.setRegionId(125L);
            ItBasicStorageNetInfo.setStorageName("测试分拣中心");
            ItBasicStorageNetInfo.setCreateUser("fanggang7");
            final int insert = itBasicStorageNetInfoDao.insertSelective(ItBasicStorageNetInfo);
            System.out.println(insert);
            Assert.assertTrue(true);
        }catch (Exception e){
            log.error("error ", e);
            Assert.fail();
        }
    }
}
