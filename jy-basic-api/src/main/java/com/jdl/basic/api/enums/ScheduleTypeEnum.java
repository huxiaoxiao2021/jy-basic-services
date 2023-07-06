package com.jdl.basic.api.enums;

public enum ScheduleTypeEnum {

    SITE(1, "场地纬度"),
    WORK_AREA(2, "作业区纬度"),
    WORK_GRID(3, "网格纬度"),
    ;

    private int code;

    private String name;

    ScheduleTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
