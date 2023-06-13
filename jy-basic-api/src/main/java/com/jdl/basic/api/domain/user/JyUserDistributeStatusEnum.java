package com.jdl.basic.api.domain.user;

public enum JyUserDistributeStatusEnum {
    UNDISTRIBUTED(false, "未分配"),
    DISTRIBUTED(true, "已分配"),
    ;

    private boolean flag;

    private String name;

    JyUserDistributeStatusEnum(boolean code, String name) {
        this.flag = code;
        this.name = name;
    }

    public boolean getFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }
}
