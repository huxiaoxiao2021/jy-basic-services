package com.jdl.basic.provider.core.service.vehicle;

import com.jdl.basic.api.domain.vehicle.VehicleVolumeDic;

public interface VehicleVolumeDicService {
  /**
   * 查询某车型的货物容量
   */
  VehicleVolumeDic queryVolumeByVehicleType(int vehicleType);

}
