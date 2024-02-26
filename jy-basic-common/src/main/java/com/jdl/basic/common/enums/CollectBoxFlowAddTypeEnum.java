package com.jdl.basic.common.enums;

public enum CollectBoxFlowAddTypeEnum {
    USER_ADD(1, "用户新增"),
    ALGORITHM_PUSH(2, "算法推送");
    private Integer code;
    private String name;

    CollectBoxFlowAddTypeEnum(Integer code, String name) {
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
