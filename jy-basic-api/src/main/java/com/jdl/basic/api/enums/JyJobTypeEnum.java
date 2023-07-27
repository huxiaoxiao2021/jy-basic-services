package com.jdl.basic.api.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum JyJobTypeEnum {
  FULL_TIME_LABORER(1,"正式工", Arrays.asList("A", "D", "L", "H", "U", "T")),
  LABOR_DISPATCHER(2,"派遣工", Arrays.asList("E", "F", "K")),
  LABOR_OUTSOURCING(3,"外包工", Arrays.asList("B", "P")),
  NO_FULL_TIME_LABORER(4,"临时工", Arrays.asList("G", "I")),

  HOUR_WORKER(5,"小时工", Arrays.asList("Q", "R")),

  // 工种6为支援工，暂时没有对应的人资工种

  LONG_TERM_WORKER(7,"长期联盟工", Arrays.asList("N", "O")),
  ;
  private static HashMap<Integer, JyJobTypeEnum> map;
  static {
    map = new HashMap<>();
    for (JyJobTypeEnum jyJobTypeEnum : JyJobTypeEnum.values()) {
      map.put(jyJobTypeEnum.jyJobTypeCode, jyJobTypeEnum);
    }
  }
  /**
   * 拣运工种编码
   */
  private Integer jyJobTypeCode;
  /**
   * 拣运工种名称
   */
  private String jyJobTypeName;
  /**
   * 拣运工种编码对应的人资工种编码（一对多）
   */
  private List<String> userJobTypeCodeList;

   JyJobTypeEnum(Integer jyJobTypeCode, String name, List<String> userJobTypeCodeList) {
    this.jyJobTypeCode = jyJobTypeCode;
    this.jyJobTypeName = name;
    this.userJobTypeCodeList = userJobTypeCodeList;
  }

  public Integer getJyJobTypeCode() {
    return jyJobTypeCode;
  }

  public String getJyJobTypeName() {
    return jyJobTypeName;
  }

  public List<String> getUserJobTypeCodeList() {
    return userJobTypeCodeList;
  }

  public static JyJobTypeEnum getJobTypeEnum(Integer jobType) {
     return map.get(jobType);
  }
}
