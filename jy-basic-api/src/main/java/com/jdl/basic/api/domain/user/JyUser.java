package com.jdl.basic.api.domain.user;

import java.util.Date;

public class JyUser {
    private Long id;

    private String userErp;

    private String userName;

    private String sex;

    private String entryDate;

    private String organizationCode;

    private String organizationName;

    private String organizationFullName;

    private Integer userStatus;

    private String quitActionDate;

    private String nature;

    private String natureDesc;

    private String positionCode;

    private String positionName;

    private String stdPositionName;

    private String stdPositionCode;

    private Boolean gridDistributeFlag;

    private Date createTime;

    private Date updateTime;

    private Date ts;

    private Integer siteCode;

    private String siteName;

    private Integer siteType;
    /**
     * 机构全编码
     */
    private String organizationFullPath;

    private String workGridKey;

    public String getWorkGridKey() {
        return workGridKey;
    }

    public void setWorkGridKey(String workGridKey) {
        this.workGridKey = workGridKey;
    }

    public Integer getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(Integer siteCode) {
        this.siteCode = siteCode;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Integer getSiteType() {
        return siteType;
    }

    public void setSiteType(Integer siteType) {
        this.siteType = siteType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserErp() {
        return userErp;
    }

    public void setUserErp(String userErp) {
        this.userErp = userErp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationFullName() {
        return organizationFullName;
    }

    public void setOrganizationFullName(String organizationFullName) {
        this.organizationFullName = organizationFullName;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getQuitActionDate() {
        return quitActionDate;
    }

    public void setQuitActionDate(String quitActionDate) {
        this.quitActionDate = quitActionDate;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getNatureDesc() {
        return natureDesc;
    }

    public void setNatureDesc(String natureDesc) {
        this.natureDesc = natureDesc;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getStdPositionName() {
        return stdPositionName;
    }

    public void setStdPositionName(String stdPositionName) {
        this.stdPositionName = stdPositionName;
    }

    public String getStdPositionCode() {
        return stdPositionCode;
    }

    public void setStdPositionCode(String stdPositionCode) {
        this.stdPositionCode = stdPositionCode;
    }

    public Boolean getGridDistributeFlag() {
        return gridDistributeFlag;
    }

    public void setGridDistributeFlag(Boolean gridDistributeFlag) {
        this.gridDistributeFlag = gridDistributeFlag;
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

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public String getOrganizationFullPath() {
        return organizationFullPath;
    }

    public void setOrganizationFullPath(String organizationFullPath) {
        this.organizationFullPath = organizationFullPath;
    }
}
