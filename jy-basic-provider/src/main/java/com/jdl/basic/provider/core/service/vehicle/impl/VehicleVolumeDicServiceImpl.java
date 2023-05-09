package com.jdl.basic.provider.core.service.vehicle.impl;

import com.jdl.basic.api.domain.vehicle.VehicleVolumeDic;
import com.jdl.basic.provider.core.dao.vehicle.VehicleVolumeDicDao;
import com.jdl.basic.provider.core.service.vehicle.VehicleVolumeDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleVolumeDicServiceImpl implements VehicleVolumeDicService {
  @Autowired
  VehicleVolumeDicDao vehicleVolumeDicDao;

  @Override
  public VehicleVolumeDic queryVolumeByVehicleType(int vehicleType) {
    return vehicleVolumeDicDao.queryVolumeByVehicleType(vehicleType);
  }
}
