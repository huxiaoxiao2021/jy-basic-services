package com.jdl.basic.api.enums;

public enum WorkGridManagerTaskBizType {
    DAILY_PATROL(1, "日常巡场"),
    MANAGER_PATROL(2, "管理巡视"),
    EXP_INSPECT(3, "异常及时检查"),
    KPI_IMPROVE(4, "指标周期改善"),
    EVENT_GOVERN(5, "事件治理整改任务");

    private Integer code;
    private String name;

    WorkGridManagerTaskBizType(Integer code, String name) {
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
