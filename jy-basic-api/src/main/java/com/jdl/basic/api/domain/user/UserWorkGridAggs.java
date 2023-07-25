package com.jdl.basic.api.domain.user;

import java.util.Date;

public class UserWorkGridAggs {
    private Long id;

    private Long workGridId;

    private String workGridKey;

    private Integer activeCount;

    private Integer quitCount;

    private Integer regularCount;

    private Integer dispatchedCount;

    private Byte yn;

    private Date createTime;

    private Date updateTime;

    private String createUserErp;

    private String createUserName;

    private String updateUserErp;

    private String updateUserName;

    private Date ts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkGridId() {
        return workGridId;
    }

    public void setWorkGridId(Long workGridId) {
        this.workGridId = workGridId;
    }

    public String getWorkGridKey() {
        return workGridKey;
    }

    public void setWorkGridKey(String workGridKey) {
        this.workGridKey = workGridKey;
    }

    public Integer getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(Integer activeCount) {
        this.activeCount = activeCount;
    }

    public Integer getQuitCount() {
        return quitCount;
    }

    public void setQuitCount(Integer quitCount) {
        this.quitCount = quitCount;
    }

    public Integer getRegularCount() {
        return regularCount;
    }

    public void setRegularCount(Integer regularCount) {
        this.regularCount = regularCount;
    }

    public Integer getDispatchedCount() {
        return dispatchedCount;
    }

    public void setDispatchedCount(Integer dispatchedCount) {
        this.dispatchedCount = dispatchedCount;
    }

    public Byte getYn() {
        return yn;
    }

    public void setYn(Byte yn) {
        this.yn = yn;
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

    public String getCreateUserErp() {
        return createUserErp;
    }

    public void setCreateUserErp(String createUserErp) {
        this.createUserErp = createUserErp;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserErp() {
        return updateUserErp;
    }

    public void setUpdateUserErp(String updateUserErp) {
        this.updateUserErp = updateUserErp;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }
}