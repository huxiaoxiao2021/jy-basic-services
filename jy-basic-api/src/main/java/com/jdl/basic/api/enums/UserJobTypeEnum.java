package com.jdl.basic.api.enums;

public enum UserJobTypeEnum {
  FULL_TIME_LOBOURER("A","全日制劳动合同工",1),
  LABOR_DISPATCHER("E","劳务派遣工",2),
  NEW_RESOURCE_UNSCHEDULE_WORKER("K","新源计划外包工",0),
  NO_FULL_TIME_LOBOURER("I","非全日制劳动合同工",4),
  GENERAL_INTERN("D","普通实习生",1),
  APPRENTICE("L","见习生",0),
  PEAK_INTERN("G","高峰期实习生",4),
  LONG_TEAM_WORKER("N","长期联盟工",3),
  PEAK_WORKER("O","高峰期联盟工",3),
  LOBOR_OUTSOURCING("P","劳务外包工",2),
  DAY_WORKER("Q","日结联盟工",5),
  NO_FULL_TIME_WORKER("R","非全日制联盟工",5),
  TRAINING_ROOM("F","实训室",0),
  GUN_WEN("U","顾问",0),
  GANG_AO_TAI_AND_HAIWAI_EMPOLYEE("H","港澳台及海外正式工",0),
  LABOR("B","劳务工",0),
  TUI_XIU("T","退休",0);
  private String code;
  private String name;
  private Integer jyJobTypeCode;


  UserJobTypeEnum(String code, String name, Integer jyJobTypeCode) {
    this.code = code;
    this.name = name;
    this.jyJobTypeCode = jyJobTypeCode;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getJyJobTypeCode() {
    return jyJobTypeCode;
  }

  public void setJyJobTypeCode(Integer jyJobTypeCode) {
    this.jyJobTypeCode = jyJobTypeCode;
  }
}
