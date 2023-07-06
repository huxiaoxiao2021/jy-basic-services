package com.jdl.basic.provider.core.service.vehicle;

import com.jdl.basic.api.domain.vehicle.VehicleIntegralConfig;

/**
 * 车型积分配置服务
 */
public interface VehicleIntegralConfigService {

    /**
     * 根据车型查询车型对应的积分配置
     */
    VehicleIntegralConfig findConfigByVehicleType(Integer vehicleType);

}
