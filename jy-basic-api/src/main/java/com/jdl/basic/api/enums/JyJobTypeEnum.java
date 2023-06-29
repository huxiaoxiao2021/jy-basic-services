package com.jdl.basic.api.enums;

public enum JyJobTypeEnum {
  FULL_TIME_LOBOURER(1,"正式工"),
  LABOR_DISPATCHER(2,"派遣工"),
  LONG_TEAM_WORKER(3,"长期联盟工"),
  NO_FULL_TIME_LOBOURER(4,"临时工"),
  HOUR_WORKER(5,"小时工"),
  LOBOR_OUTSOURCING(6,"外包工");
  private Integer code;
  private String name;

   JyJobTypeEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
