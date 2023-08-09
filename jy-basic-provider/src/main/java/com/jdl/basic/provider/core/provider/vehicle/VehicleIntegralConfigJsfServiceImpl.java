package com.jdl.basic.provider.core.provider.vehicle;


import com.alibaba.fastjson.JSON;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.vehicle.VehicleIntegralConfig;
import com.jdl.basic.api.service.vehicle.VehicleIntegralConfigJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.vehicle.VehicleIntegralConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".VehicleIntegralConfigJsfServiceImpl.add", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Void> add(VehicleIntegralConfig config) {
        Result<Void> result = new Result<>();
        result.toSuccess();
        if (config.getVehicleType() == null) {
            result.toFail("车型不能为空");
            return result;
        }
        if (StringUtils.isBlank(config.getVehicleTypeName())) {
            result.toFail("车型名称不能为空");
            return result;
        }
        if (config.getPriorityFraction() == null) {
            result.toFail("分数不能为空");
            return result;
        }
        try {
            Date date = new Date();
            config.setCreateTime(date);
            config.setUpdateTime(date);
            VehicleIntegralConfig oldConfig = vehicleIntegralConfigService.findConfigByVehicleType(config.getVehicleType());
            if (oldConfig != null) {
                log.warn("add|根据车型查询积分配置不为空:config={}", JSON.toJSONString(oldConfig));
                result.toFail("根据车型查询积分配置不为空");
                return result;
            }
            vehicleIntegralConfigService.add(config);
        } catch (Exception e) {
            log.error("add|新增积分配置出现异常:config={}", JSON.toJSONString(config), e);
            result.toError("服务器异常");
        }
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".VehicleIntegralConfigJsfServiceImpl.updateByVehicleType", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Void> updateByVehicleType(VehicleIntegralConfig config) {
        Result<Void> result = new Result<>();
        result.toSuccess();
        if (config.getVehicleType() == null) {
            result.toFail("车型不能为空");
            return result;
        }
        if (config.getPriorityFraction() == null) {
            result.toFail("分数不能为空");
            return result;
        }
        try {
            Date date = new Date();
            config.setUpdateTime(date);
            vehicleIntegralConfigService.updateByVehicleType(config);
        } catch (Exception e) {
            log.error("updateByVehicleType|根据车型修改积分配置出现异常:config={}", JSON.toJSONString(config), e);
            result.toError("服务器异常");
        }
        return result;
    }
}
