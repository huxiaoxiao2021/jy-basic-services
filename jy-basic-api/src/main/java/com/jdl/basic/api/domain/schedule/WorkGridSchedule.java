package com.jdl.basic.api.domain.schedule;

import java.util.Date;

public class WorkGridSchedule {
    private Long id;

    private String workGridKey;

    private Integer scheduleNo;

    private String scheduleKey;

    private String scheduleName;

    private String startTime;

    private String endTime;

    private Integer scheduleType;

    private Integer sourceType;

    private Integer siteCode;

    private String areaCode;

    private Byte yn;

    private Date createTime;

    private Date updateTime;

    private String createUserErp;

    private String createUserName;

    private String updateUserErp;

    private String updateUserName;

    private Date ts;

    private String oldStartTime;

    private String oldEndTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkGridKey() {
        return workGridKey;
    }

    public void setWorkGridKey(String workGridKey) {
        this.workGridKey = workGridKey;
    }

    public Integer getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(Integer scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getScheduleKey() {
        return scheduleKey;
    }

    public void setScheduleKey(String scheduleKey) {
        this.scheduleKey = scheduleKey;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(Integer scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(Integer siteCode) {
        this.siteCode = siteCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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

    public String getOldStartTime() {
        return oldStartTime;
    }

    public void setOldStartTime(String oldStartTime) {
        this.oldStartTime = oldStartTime;
    }

    public String getOldEndTime() {
        return oldEndTime;
    }

    public void setOldEndTime(String oldEndTime) {
        this.oldEndTime = oldEndTime;
    }
}
