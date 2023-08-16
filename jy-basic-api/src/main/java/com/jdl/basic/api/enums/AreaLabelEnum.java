package com.jdl.basic.api.enums;

public enum AreaLabelEnum {
  NEED_FIND_GOODS(1,"需清场");
  private int code;
  private String name;

  AreaLabelEnum(int code, String name) {
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
