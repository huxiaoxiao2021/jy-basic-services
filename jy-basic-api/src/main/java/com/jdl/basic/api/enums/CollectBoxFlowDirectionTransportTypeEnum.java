package com.jdl.basic.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CollectBoxFlowDirectionTransportTypeEnum {

    TRANSPORT_TYPE_HIGHWAY(1, "公路"),
    TRANSPORT_TYPE_AIR(2, "航空");

    @Getter
    Integer code;
    @Getter
    String name;

    public static CollectBoxFlowDirectionTransportTypeEnum of(Integer code) {
        for (CollectBoxFlowDirectionTransportTypeEnum e : CollectBoxFlowDirectionTransportTypeEnum.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static String nameByCode(Integer code) {
        for (CollectBoxFlowDirectionTransportTypeEnum e : CollectBoxFlowDirectionTransportTypeEnum.values()) {
            if (e.code.equals(code)) {
                return e.name;
            }
        }
        return "";
    }
}
