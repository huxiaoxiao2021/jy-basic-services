package com.jdl.basic.common.enums;

/**
 * 集包规则数据版本状态
 */
public enum CollectBoxFlowInfoStatusEnum {
    HISTORY(0, "历史版本"),
    CURRENT(1, "当前版本"),
    UNACTIVATED(2, "待激活版本")
    ;
    private Integer code;
    private String name;

    CollectBoxFlowInfoStatusEnum() {
    }

    CollectBoxFlowInfoStatusEnum(Integer code, String name) {
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
