package com.jdl.basic.api.enums;

public enum KaCoefficientStatusEnum {


    IN_EFFECT("1","生效中"),
    LOSE_EFFICACY("0","已作废");

    private String code;
    private String name;

    KaCoefficientStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
