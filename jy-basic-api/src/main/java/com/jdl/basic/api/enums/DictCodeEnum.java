package com.jdl.basic.api.enums;


import com.jdl.basic.common.utils.StringUtils;

/**
 * @author : caozhixing3
 * @version V1.0
 * @Project: jy-basic-services
 * @Package com.jdl.basic.api.enums
 * @Description:
 * @date Date : 2023年12月04日 21:24
 */
public enum DictCodeEnum {
    BELONG_QL_SITE_TYPE("BELONG_QL_SITE_TYPE", "租户青龙对应"),
    BELONG_RZ_ORG_CODE("BELONG_RZ_ORG_CODE", "租户人资对应"),
    TENANT_BUSINESS_LINE("TENANT_BUSINESS_LINE","租户对应业务条线"),
    TENANT_SITE_TYPE("TENANT_SITE_TYPE","租户对应场地类型"),
    ;

    DictCodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 根据字典代码获取字典枚举对象
     *
     * @param code 字典代码
     * @return 字典枚举对象，如果找不到则返回null
     */
    public static DictCodeEnum getDictCodeEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (DictCodeEnum currEnum : DictCodeEnum.values()) {
            if (code.equals(currEnum.getCode())) {
                return currEnum;
            }
        }
        return null;
    }

}
