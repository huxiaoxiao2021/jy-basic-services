package com.jdl.basic.api.enums;

import java.util.HashMap;

public enum UserJobTypeEnum {
  FULL_TIME_LABORER("A","全日制劳动合同工",1),
  LABOR_DISPATCHER("E","劳务派遣工",2),
  NEW_RESOURCE_SCHEDULE_WORKER("K","新源计划外包工",2),
  NO_FULL_TIME_LABORER("I","非全日制劳动合同工",2),
  GENERAL_INTERN("D","普通实习生",1),
  APPRENTICE("L","见习生",1),
  PEAK_INTERN("G","高峰期实习生",4),
  LONG_TERM_WORKER("N","长期联盟工",7),
  PEAK_WORKER("O","高峰期联盟工",7),
  LABOR_OUTSOURCING("P","劳务外包工",3),
  DAY_WORKER("Q","日结联盟工",5),
  NO_FULL_TIME_WORKER("R","非全日制联盟工",5),
  TRAINING_ROOM("F","实训室",2),
  GUN_WEN("U","顾问",1),
  GANG_AO_TAI_AND_HAIWAI_EMPLOYEE("H","港澳台及海外正式工",1),
  LABOR("B","劳务工",3),
  TUI_XIU("T","退休",1);
  private String code;
  private String name;
  /**
   * 0-暂时没有对应的拣运工种类型
   */
  private Integer jyJobTypeCode;

  private static HashMap<String, UserJobTypeEnum> map;
  static {
    map = new HashMap<>();
    for (UserJobTypeEnum userJobTypeEnum: UserJobTypeEnum.values()) {
      map.put(userJobTypeEnum.code, userJobTypeEnum);
    }
  }

  UserJobTypeEnum(String code, String name, Integer jyJobTypeCode) {
    this.code = code;
    this.name = name;
    this.jyJobTypeCode = jyJobTypeCode;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public Integer getJyJobTypeCode() {
    return jyJobTypeCode;
  }

  public static UserJobTypeEnum getJyJobEnumByNature(String nature) {
    return map.get(nature);
  }
}
