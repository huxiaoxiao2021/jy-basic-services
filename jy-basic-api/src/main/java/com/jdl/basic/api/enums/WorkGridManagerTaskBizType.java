package com.jdl.basic.api.enums;

public enum WorkGridManagerTaskBizType {
    DAILY_PATROL(1, "例行自检巡场任务"),
    MANAGER_PATROL(2, "飞检巡场任务"),
    EXP_INSPECT(3, "异常自检任务"),
    KPI_IMPROVE(4, "指标改善任务"),
    EVENT_GOVERN(5, "工单事件自检任务"),
    MONITOR_PATROL(6, "监控巡场任务");

    private Integer code;
    private String name;

    WorkGridManagerTaskBizType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    /**
     * 根据code获取名称
     * @param code
     * @return
     */
    public static String getNameByCode(Integer code) {
        WorkGridManagerTaskBizType data = getEnum(code);
        if(data != null) {
            return data.getName();
        }
        return null;
    }
    /**
     * 根据code获取enum
     * @param code
     * @return
     */
    public static WorkGridManagerTaskBizType getEnum(Integer code) {
        for (WorkGridManagerTaskBizType value : WorkGridManagerTaskBizType.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
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
