package com.jdl.basic.api.service.vehicle;

import com.jdl.basic.api.domain.vehicle.VehicleVolumeDic;
import com.jdl.basic.api.domain.vehicle.VehicleVolumeDicReq;
import com.jdl.basic.api.domain.vehicle.VehicleVolumeDicResp;
import com.jdl.basic.common.utils.Result;

public interface VehicleVolumeDicJsfService {

  Result<VehicleVolumeDicResp> queryVolumeByVehicleType(VehicleVolumeDicReq req);

}
