package com.jdl.basic.api.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 站点与工单部门配置状态枚举
 *
 * @author fanggang7
 * @date 2020-05-14 22:43:56 周四
 **/
public enum ConfigSiteAssocWosDeptStatusEnum {

    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    public static Map<Integer, String> ENUM_MAP;

    public static List<Integer> ENUM_LIST;

    private Integer code;

    private String name;

    static {
        //将所有枚举装载到map中
        ENUM_MAP = new HashMap<Integer, String>();
        ENUM_LIST = new ArrayList<Integer>();
        for (ConfigSiteAssocWosDeptStatusEnum _enum : ConfigSiteAssocWosDeptStatusEnum.values()) {
            ENUM_MAP.put(_enum.getCode(), _enum.getName());
            ENUM_LIST.add(_enum.getCode());
        }
    }

    /**
     * 通过code获取name
     *
     * @param code 编码
     * @return string
     */
    public static String getEnumNameByCode(Integer code) {
        return ENUM_MAP.get(code);
    }

    ConfigSiteAssocWosDeptStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
