package com.jdl.basic.api.domain.user;

import java.util.Date;

public class UserWorkGrid {
    private Long id;

    private Long userId;

    private String userErp;

    private Long workGridId;

    private String workGridKey;

    private String nature;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
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