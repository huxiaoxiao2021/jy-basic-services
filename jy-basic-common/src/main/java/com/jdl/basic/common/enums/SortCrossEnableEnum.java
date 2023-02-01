package com.jdl.basic.common.enums;

/**
 * @author liwenji
 * @date 2022-12-14 9:00
 */
public enum SortCrossEnableEnum {

    DISABLE(0, "disable"),
    ENABLE(1, "enable");

    private SortCrossEnableEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    
    private Integer code;
    private String name;

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
