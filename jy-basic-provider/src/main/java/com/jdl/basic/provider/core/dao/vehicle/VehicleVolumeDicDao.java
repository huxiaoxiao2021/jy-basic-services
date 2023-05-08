package com.jdl.basic.provider.core.dao.vehicle;

import com.jdl.basic.api.domain.vehicle.VehicleVolumeDic;

public interface VehicleVolumeDicDao {
    int deleteByPrimaryKey(Integer id);

    int insert(VehicleVolumeDic record);

    int insertSelective(VehicleVolumeDic record);

    VehicleVolumeDic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VehicleVolumeDic record);

    int updateByPrimaryKey(VehicleVolumeDic record);

    VehicleVolumeDic queryVolumeByVehicleType(Integer vehicleType);
}
