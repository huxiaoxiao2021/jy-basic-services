package com.jdl.basic.api.enums;

public enum UserJobTypeEnum {
  FULL_TIME_LOBOURER("A","全日制劳动合同工"),
  LABOR_DISPATCHER("E","劳务派遣工"),
  NEW_RESOURCE_UNSCHEDULE_WORKER("K","新源计划外包工"),
  NO_FULL_TIME_LOBOURER("I","非全日制劳动合同工"),
  GENERAL_INTERN("D","普通实习生"),
  APPRENTICE("L","见习生"),
  PEAK_INTERN("G","高峰期实习生"),
  LONG_TEAM_WORKER("N","长期联盟工"),
  PEAK_WORKER("O","高峰期联盟工"),
  LOBOR_OUTSOURCING("P","劳务外包工"),
  DAY_WORKER("Q","日结联盟工"),
  NO_FULL_TIME_WORKER("R","非全日制联盟工"),
  TRAINING_ROOM("F","实训室"),
  GUN_WEN("U","顾问"),
  GANG_AO_TAI_AND_HAIWAI_EMPOLYEE("H","港澳台及海外正式工"),
  LABOR("B","劳务工"),
  TUI_XIU("T","退休");
  private String code;
  private String name;

   UserJobTypeEnum(String code, String name) {
    this.code = code;
    this.name = name;
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
}
