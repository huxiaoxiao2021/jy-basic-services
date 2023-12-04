package com.jdl.basic.api.enums;

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
}
