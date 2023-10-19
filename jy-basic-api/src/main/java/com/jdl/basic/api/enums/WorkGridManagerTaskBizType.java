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
