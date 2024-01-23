package com.jdl.basic.api.enums;


import com.jdl.basic.common.utils.StringUtils;

import java.util.Arrays;

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
    TENANT_UNLOAD_SCAN_ALIES_UAT("TENANT_UNLOAD_SCAN_ALIES_UAT","租户卸车回调uat别名"),
    TENANT_UNLOAD_SCAN_ALIES("TENANT_UNLOAD_SCAN_ALIES","租户卸车回调正式别名"),
    TENANT_SEND_SCAN_ALIES_UAT("TENANT_SEND_SCAN_ALIES_UAT","租户发货回调uat别名"),
    TENANT_SEND_SCAN_ALIES("TENANT_SEND_SCAN_ALIES","租户发货回调正式别名"),
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

    /**
     * 判断给定的代码是否是合法的别名字典代码
     * @param code 要检查的代码
     * @return 如果代码是合法的别名字典代码则返回true，否则返回false
     */
    public static boolean isLegalAliesDictCode(String code){
        if (StringUtils.isBlank(code)) {
            return false;
        }
        for (DictCodeEnum currEnum : Arrays.asList(TENANT_UNLOAD_SCAN_ALIES_UAT,TENANT_UNLOAD_SCAN_ALIES,TENANT_SEND_SCAN_ALIES_UAT,TENANT_SEND_SCAN_ALIES)) {
            if (code.equals(currEnum.getCode())) {
                return true;
            }
        }
        return false;
    }

}
