package com.jdl.basic.api.enums;

public enum UserSatusEnum {
  QUIT(2, "离职"),
  ONJOB(1, "在职");
  private Integer code;
  private String name;

  UserSatusEnum(Integer code, String name) {
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
