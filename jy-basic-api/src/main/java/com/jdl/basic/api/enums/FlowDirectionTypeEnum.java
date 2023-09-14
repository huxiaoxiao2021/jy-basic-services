package com.jdl.basic.api.enums;

/**
 * 业务条线类型枚举
 *
 * @author hujiping
 * @date 2022/5/7 4:29 PM
 */
public enum FlowDirectionTypeEnum {

    INTO_SITE(1, "进场"),
    OUT_SITE(2, "出场");

    FlowDirectionTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;

    private String name;

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

    /**
     * 根据code获取名称
     * @param code
     * @return
     */
    public static String getNameByCode(Integer code) {
        FlowDirectionTypeEnum data = getEnum(code);
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
    public static FlowDirectionTypeEnum getEnum(Integer code) {
        for (FlowDirectionTypeEnum value : FlowDirectionTypeEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
