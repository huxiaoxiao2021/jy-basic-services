package com.jdl.basic.api.enums;

public enum WorkGridManagerTaskTransferEnum {
    UN_TRANSFER(0, "未转派"),
    TRANSFERED(1, "已转派");

    private Integer code;
    private String name;

    WorkGridManagerTaskTransferEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    /**
     * 根据code获取名称
     * @param code
     * @return
     */
    public static String getNameByCode(Integer code) {
        WorkGridManagerTaskTransferEnum data = getEnum(code);
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
    public static WorkGridManagerTaskTransferEnum getEnum(Integer code) {
        for (WorkGridManagerTaskTransferEnum value : WorkGridManagerTaskTransferEnum.values()) {
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
