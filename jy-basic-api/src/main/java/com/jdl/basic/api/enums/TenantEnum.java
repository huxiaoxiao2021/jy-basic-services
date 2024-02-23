package com.jdl.basic.api.enums;

import com.jdl.basic.common.utils.StringUtils;

/**
 * @author : caozhixing3
 * @version V1.0
 * @Project: jy-basic-services
 * @Package com.jdl.basic.api.enums
 * @Description:
 * @date Date : 2023年11月29日 17:36
 */
public enum TenantEnum {
    TENANT_JY("JY", "拣运"),
    TENANT_COLD_MEDICINE("CC", "冷链医药"),
    ;

    TenantEnum(String code, String name) {
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
     * 获取具有默认值的租户枚举。
     * @param code 入参，要匹配的租户代码
     * @return 匹配的租户枚举，如果没有匹配则返回默认枚举
     */
    public static TenantEnum getTenantEnumWithDefault(String code) {
        if (StringUtils.isBlank(code)) {
            return TenantEnum.TENANT_JY;
        }
        for (TenantEnum currEnum : TenantEnum.values()) {
            if (code.equals(currEnum.getCode())) {
                return currEnum;
            }
        }
        return TenantEnum.TENANT_JY;
    }
}
