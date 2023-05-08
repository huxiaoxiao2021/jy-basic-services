package com.jdl.basic.provider.core.provider.vehicle;

import com.jdl.basic.api.domain.vehicle.VehicleVolumeDic;
import com.jdl.basic.api.domain.vehicle.VehicleVolumeDicReq;
import com.jdl.basic.api.domain.vehicle.VehicleVolumeDicResp;
import com.jdl.basic.api.service.vehicle.VehicleVolumeDicJsfService;
import com.jdl.basic.common.utils.BeanUtils;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.vehicle.VehicleVolumeDicService;
import com.jdl.basic.rpc.exception.JYBasicRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleVolumeDicJsfServiceImpl implements VehicleVolumeDicJsfService {

  @Autowired
  VehicleVolumeDicService vehicleVolumeDicService;


  @Override
  public Result<VehicleVolumeDicResp> queryVolumeByVehicleType(VehicleVolumeDicReq req) {
    checkVehicleVolumeDicReq(req);
    VehicleVolumeDic vehicleVolumeDic = vehicleVolumeDicService.queryVolumeByVehicleType(req.getVehicleType());
    if (ObjectHelper.isNotNull(vehicleVolumeDic)) {
      VehicleVolumeDicResp resp = BeanUtils.copy(vehicleVolumeDic, VehicleVolumeDicResp.class);
      return Result.success(resp);
    }
    return Result.fail("未查询到对应车辆类型的容量数据！");
  }

  private void checkVehicleVolumeDicReq(VehicleVolumeDicReq req) {
    if (ObjectHelper.isEmpty(req) || ObjectHelper.isEmpty(req.getVehicleType())) {
      throw new JYBasicRpcException("参数异常：车辆类型不允许为空！");
    }
  }
}
