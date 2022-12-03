package com.jdl.basic.provider.dao.device;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.deivce.DeviceInfo;
import com.jdl.basic.api.dto.device.qo.DeviceInfoQo;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.dao.device.DeviceInfoDao;
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
public class DeviceInfoDaoTest {

    @Autowired
    private DeviceInfoDao deviceInfoDao;

    @Test
    public void queryListTest(){
        final DeviceInfoQo deviceInfoQo = new DeviceInfoQo();
        deviceInfoQo.setYn(1);
        deviceInfoQo.setPageNumber(1);
        deviceInfoQo.setPageSize(2);
        final List<DeviceInfo> dataList = deviceInfoDao.queryList(deviceInfoQo);
        System.out.println(JSON.toJSONString(dataList));
        Assert.assertTrue(true);
    }

    @Test
    public void queryCountTest(){
        final DeviceInfoQo deviceInfoQo = new DeviceInfoQo();
        deviceInfoQo.setYn(1);
        deviceInfoQo.setPageNumber(1);
        deviceInfoQo.setPageSize(2);
        final long total = deviceInfoDao.queryCount(deviceInfoQo);
        System.out.println(total);
        Assert.assertTrue(true);
    }

    @Test
    public void insertTest(){
        try {
            final DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceCode("DEVICE-01");
            deviceInfo.setDeviceName("设备01");
            deviceInfo.setDeviceSn("aaaabbb");
            deviceInfo.setDeviceType(1);
            deviceInfo.setManufacturerCode("fact");
            deviceInfo.setManufacturerName("测试厂家");
            final int insert = deviceInfoDao.insertSelective(deviceInfo);
            System.out.println(insert);
            Assert.assertTrue(true);
        }catch (Exception e){
            log.error("errror ", e);
            Assert.fail();
        }
    }
}
