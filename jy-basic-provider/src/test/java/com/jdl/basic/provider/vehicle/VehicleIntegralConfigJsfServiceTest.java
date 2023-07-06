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

}
