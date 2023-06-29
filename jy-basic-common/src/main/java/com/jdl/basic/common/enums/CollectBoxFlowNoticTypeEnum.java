package com.jdl.basic.common.enums;

public enum CollectBoxFlowNoticTypeEnum {
    ADD(1, "新增"),
    ACTIVATE(2, "激活"),
    ROLLBACK(3, "回滚"),
    ROUTER_WARNING(4, "路由校验预警");
    private Integer code;
    private String name;

    CollectBoxFlowNoticTypeEnum(Integer code, String name) {
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
