package com.jdl.basic.api.domain.vehicle;

import java.io.Serializable;

public class VehicleVolumeDicReq implements Serializable {

  private static final long serialVersionUID = 2303350700136793153L;
  private Integer vehicleType;

  public Integer getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(Integer vehicleType) {
    this.vehicleType = vehicleType;
  }
}
