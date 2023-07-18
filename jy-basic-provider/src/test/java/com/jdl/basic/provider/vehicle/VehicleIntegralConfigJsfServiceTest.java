package com.jdl.basic.provider.vehicle;

import com.jdl.basic.api.domain.vehicle.VehicleIntegralConfig;
import com.jdl.basic.api.service.vehicle.VehicleIntegralConfigJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class VehicleIntegralConfigJsfServiceTest {

    @Autowired
    VehicleIntegralConfigJsfService vehicleIntegralConfigJsfService;

    @Test
    public void testFindConfigByVehicleType() {
        Integer vehicleType = 7;
        Result<VehicleIntegralConfig> result = vehicleIntegralConfigJsfService.findConfigByVehicleType(vehicleType);
        Assert.assertNotNull(result);
    }

    @Test
    public void testAdd() {
        VehicleIntegralConfig config = new VehicleIntegralConfig();
        config.setVehicleType(3000);
        config.setVehicleTypeName("测试车型");
        config.setPriorityFraction(300D);
        config.setCreateUserErp("sys.dms");
        config.setCreateUserName("sys.dms");
        config.setUpdateUserErp("sys.dms");
        config.setUpdateUserName("sys.dms");
        config.setCreateTime(new Date());
        config.setUpdateTime(new Date());
        Result<Void> result = vehicleIntegralConfigJsfService.add(config);
        Assert.assertNotNull(result);
    }

    @Test
    public void testUpdateByVehicleType() {
        VehicleIntegralConfig config = new VehicleIntegralConfig();
        config.setVehicleType(3000);
        config.setVehicleTypeName("测试车型1");
        config.setPriorityFraction(301D);
        config.setCreateUserErp("sys.dms1");
        config.setCreateUserName("sys.dms1");
        config.setUpdateUserErp("sys.dms1");
        config.setUpdateUserName("sys.dms1");
        config.setCreateTime(new Date());
        config.setUpdateTime(new Date());
        Result<Void> result = vehicleIntegralConfigJsfService.updateByVehicleType(config);
        Assert.assertNotNull(result);
    }

}
