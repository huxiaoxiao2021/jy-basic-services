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
     * 所属租户
     */
    private String belongTenantCode;
    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 字典项展示文本
     */
    private String dictItemText;
    /**
     * 字典项值
     */
    private String dictItemValue;

    //=========================

    public String getBelongTenantCode() {
        return belongTenantCode;
    }

    public void setBelongTenantCode(String belongTenantCode) {
        this.belongTenantCode = belongTenantCode;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictItemText() {
        return dictItemText;
    }

    public void setDictItemText(String dictItemText) {
        this.dictItemText = dictItemText;
    }

    public String getDictItemValue() {
        return dictItemValue;
    }

    public void setDictItemValue(String dictItemValue) {
        this.dictItemValue = dictItemValue;
    }
}
