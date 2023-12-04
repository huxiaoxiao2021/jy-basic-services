package com.jdl.basic.api.domain.tenant;

/**
 * @author : caozhixing3
 * @version V1.0
 * @Project: jy-basic-services
 * @Package com.jdl.basic.api.domain.tenant
 * @Description:
 * @date Date : 2023年11月28日 18:34
 */
public class JyConfigDictTenantQuery {
    /**
     *
     */
    private Integer businessType;
    /**
     *
     */
    private String key;
    /**
     *
     */
    private String value;

    //=========================

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
