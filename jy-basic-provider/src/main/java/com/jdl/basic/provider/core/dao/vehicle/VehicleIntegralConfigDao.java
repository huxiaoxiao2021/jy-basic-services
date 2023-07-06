package com.jdl.basic.provider.core.dao.vehicle;


import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jdl.basic.api.domain.vehicle.VehicleIntegralConfig;

/**
 * 车型积分配置DAO
 */
public interface VehicleIntegralConfigDao {

    /**
     * 根据车型查询车型对应的积分配置
     */
    @Cache(key = "VehicleIntegralConfigDao.findConfigByVehicleType@args0", memoryEnable = true, memoryExpiredTime = 5 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 10 * 60 * 1000)
    VehicleIntegralConfig findConfigByVehicleType(Integer vehicleType);

}
