package com.jdl.basic.provider.core.provider.vehicle;


import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.vehicle.VehicleIntegralConfig;
import com.jdl.basic.api.service.vehicle.VehicleIntegralConfigJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.vehicle.VehicleIntegralConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 车型积分配置服务
 */
@Slf4j
@Service
public class VehicleIntegralConfigJsfServiceImpl implements VehicleIntegralConfigJsfService {


    @Autowired
    private VehicleIntegralConfigService vehicleIntegralConfigService;


    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".VehicleIntegralConfigJsfServiceImpl.findConfigByVehicleType", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<VehicleIntegralConfig> findConfigByVehicleType(Integer vehicleType) {
        Result<VehicleIntegralConfig> result = new Result<>();
        result.toSuccess();
        if (vehicleType == null) {
            log.warn("findConfigByVehicleType|根据车型查询积分配置参数为空");
            result.toFail("车型不能为空");
            return result;
        }
        try {
            VehicleIntegralConfig config = vehicleIntegralConfigService.findConfigByVehicleType(vehicleType);
            if (config == null) {
                log.warn("findConfigByVehicleType|根据车型查询积分配置为空:vehicleType={}", vehicleType);
                result.toFail("根据车型查询积分配置为空");
                return result;
            }
            result.setData(config);
        } catch (Exception e) {
            log.error("findConfigByVehicleType|根据车型查询车型积分配置出现异常:vehicleType={}", vehicleType, e);
            result.toError("服务器异常");
        }
        return result;
    }
}
