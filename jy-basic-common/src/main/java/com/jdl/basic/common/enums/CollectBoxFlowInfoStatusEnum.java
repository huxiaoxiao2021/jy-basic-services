package com.jdl.basic.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 集包规则数据版本状态
 */
public enum CollectBoxFlowInfoStatusEnum {
    HISTORY(0, "历史版本"),
    CURRENT(1, "当前版本"),
    UNACTIVATED(2, "大数据推送版本")
    ;
    private Integer code;
    private String name;

    private static Map<Integer, CollectBoxFlowInfoStatusEnum> codeMap;
    static {
        //将所有枚举装载到map中
        codeMap = new HashMap<Integer, CollectBoxFlowInfoStatusEnum>();
        for (CollectBoxFlowInfoStatusEnum areaEnum : CollectBoxFlowInfoStatusEnum.values()) {
            codeMap.put(areaEnum.getCode(), areaEnum);
        }
    }

    CollectBoxFlowInfoStatusEnum() {
    }

    CollectBoxFlowInfoStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 通过编码获取枚举
     *
     * @param code 编码
     * @return 区域名称
     */
    public static String getNameByCode(Integer code) {
        if(code != null && codeMap.containsKey(code)) {
            return codeMap.get(code).getName();
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
