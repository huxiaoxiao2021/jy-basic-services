package com.jdl.basic.api.service.vehicle;


import com.jdl.basic.api.domain.vehicle.VehicleIntegralConfig;
import com.jdl.basic.common.utils.Result;

/**
 * 车型积分配置对外jsf服务
 */
public interface VehicleIntegralConfigJsfService {

    /**
     * 根据车型查询车型对应的积分配置
     * @param vehicleType 车型编码
     */
    Result<VehicleIntegralConfig> findConfigByVehicleType(Integer vehicleType);

    /**
     * 新增积分配置
     */
    Result<Void> add(VehicleIntegralConfig config);

    /**
     * 根据车型修改积分配置
     */
    Result<Void> updateByVehicleType(VehicleIntegralConfig config);

}
