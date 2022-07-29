package com.jdl.basic.api.enums;

public enum ServiceResponseSubCodeEnum {

    CHECK_PARAMS_FAILED("CHECK_PARAMS_FAILED","入参校验失败"),
    SYS_ERROR("SYS_ERROR", "其他错误"),

    // 按业务区分,MONITOR




    SUCCESS("SUCCESS", "成功");

    private String subCode;
    private String value;

    ServiceResponseSubCodeEnum(String subCode, String value) {
        this.subCode = subCode;
        this.value = value;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
