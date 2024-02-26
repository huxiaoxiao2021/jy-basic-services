package com.jdl.basic.api.domain.tenant;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : caozhixing3
 * @version V1.0
 * @Project: jy-basic-services
 * @Package com.jdl.basic.api.domain.tenant
 * @Description:
 * @date Date : 2023年11月28日 18:12
 */
public class JyConfigDictTenant implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;
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
    /**
     * 创建人ERP
     */
    private String createUser;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人ERP
     */
    private String updateUser;

    /**
     * 更新人姓名
     */
    private String updateUserName;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 逻辑删除标志,0-删除,1-正常
     */
    private Integer yn;

    //==============================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}
