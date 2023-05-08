package com.jdl.basic.api.domain.vehicle;

public class VehicleVolumeDicResp {
  private Integer id;

  private String vehicleTypeName;

  private Integer vehicleType;

  private String length;

  private Integer volumeTrunkC;

  private Integer volumeTrunkB;

  private Integer volumeBranchC;

  private Integer volumeBranchB;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getVehicleTypeName() {
    return vehicleTypeName;
  }

  public void setVehicleTypeName(String vehicleTypeName) {
    this.vehicleTypeName = vehicleTypeName;
  }

  public Integer getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(Integer vehicleType) {
    this.vehicleType = vehicleType;
  }

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
  }

  public Integer getVolumeTrunkC() {
    return volumeTrunkC;
  }

  public void setVolumeTrunkC(Integer volumeTrunkC) {
    this.volumeTrunkC = volumeTrunkC;
  }

  public Integer getVolumeTrunkB() {
    return volumeTrunkB;
  }

  public void setVolumeTrunkB(Integer volumeTrunkB) {
    this.volumeTrunkB = volumeTrunkB;
  }

  public Integer getVolumeBranchC() {
    return volumeBranchC;
  }

  public void setVolumeBranchC(Integer volumeBranchC) {
    this.volumeBranchC = volumeBranchC;
  }

  public Integer getVolumeBranchB() {
    return volumeBranchB;
  }

  public void setVolumeBranchB(Integer volumeBranchB) {
    this.volumeBranchB = volumeBranchB;
  }
}
