package com.jdl.basic.api.enums;

public enum WorkGridManagerTaskBizType {
    DAILY_PATROL(1, "例行自检巡场任务", "自检"),
    MANAGER_PATROL(2, "飞检巡场任务", "飞检"),
    EXP_INSPECT(3, "异常自检任务", "异常"),
    KPI_IMPROVE(4, "指标改善任务", "指标"),
    EVENT_GOVERN(5, "工单事件自检任务", "工单"),
    MONITOR_PATROL(6, "监控巡场任务", "巡场");

    private Integer code;
    private String name;
    
    private String shortLable;

    WorkGridManagerTaskBizType(Integer code, String name, String shortLable) {
        this.code = code;
        this.name = name;
        this.shortLable = shortLable;
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

    public String getShortLable() {
        return shortLable;
    }

    public void setShortLable(String shortLable) {
        this.shortLable = shortLable;
    }
}
