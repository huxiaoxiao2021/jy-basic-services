package com.jdl.basic.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CollectBoxFlowDirectionFlowTypeEnum {

    FLOW_TYPE_IN(1, "进港"),
    FLOW_TYPE_OUT(2, "出港");

    @Getter
    Integer code;
    @Getter
    String name;

    public static CollectBoxFlowDirectionFlowTypeEnum of(Integer code) {
        for (CollectBoxFlowDirectionFlowTypeEnum e : CollectBoxFlowDirectionFlowTypeEnum.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static String nameByCode(Integer code) {
        for (CollectBoxFlowDirectionFlowTypeEnum e : CollectBoxFlowDirectionFlowTypeEnum.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return "";
    }
}
