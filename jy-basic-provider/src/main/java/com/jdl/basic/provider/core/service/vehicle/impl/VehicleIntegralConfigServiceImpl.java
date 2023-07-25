package com.jdl.basic.provider.core.service.vehicle.impl;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.vehicle.VehicleIntegralConfig;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.vehicle.VehicleIntegralConfigDao;
import com.jdl.basic.provider.core.service.vehicle.VehicleIntegralConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 车型积分配置服务
 */
@Service
public class VehicleIntegralConfigServiceImpl implements VehicleIntegralConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleIntegralConfigServiceImpl.class);

    @Autowired
    private VehicleIntegralConfigDao vehicleIntegralConfigDao;


    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".VehicleIntegralConfigServiceImpl.findConfigByVehicleType", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public VehicleIntegralConfig findConfigByVehicleType(Integer vehicleType) {
        return vehicleIntegralConfigDao.findConfigByVehicleType(vehicleType);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".VehicleIntegralConfigServiceImpl.add", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public void add(VehicleIntegralConfig config) {
        vehicleIntegralConfigDao.add(config);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".VehicleIntegralConfigServiceImpl.updateByVehicleType", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public void updateByVehicleType(VehicleIntegralConfig config) {
        vehicleIntegralConfigDao.updateByVehicleType(config);
    }
}
