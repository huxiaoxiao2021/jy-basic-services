package com.jdl.basic.api.domain.videoTraceCamera;

import java.util.Date;

public class VideoTraceCamera {
    private Integer id;

    /**
     * 摄像头编码
     */
    private String cameraCode;
    /**
     * 摄像头名称
     */
    private String cameraName;
    /**
     * 通道号
     */
    private String nationalChannelCode;
    /**
     * 通道名称
     */
    private String nationalChannelName;
    /**
     * 场地编码
     */
    private Integer siteCode;
    /**
     * 场地名称
     */
    private String siteName;
    /**
     * 省区编码
     */
    private String provinceAgencyCode;
    /**
     * 省区名称
     */
    private String provinceAgencyName;
    /**
     * 枢纽编码
     */
    private String areaHubCode;
    /**
     * 枢纽名称
     */
    private String areaHubName;

    private String createErp;

    private Date createTime;

    private String updateErp;

    private Date updateTime;
    /**
     * 配置状态 1已配置 0未配置
     */
    private Byte configStatus;
    /**
     * 逻辑删除标志位 0删除 1正常
     */
    private Byte status;

    private Date ts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCameraCode() {
        return cameraCode;
    }

    public void setCameraCode(String cameraCode) {
        this.cameraCode = cameraCode;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getNationalChannelCode() {
        return nationalChannelCode;
    }

    public void setNationalChannelCode(String nationalChannelCode) {
        this.nationalChannelCode = nationalChannelCode;
    }

    public String getNationalChannelName() {
        return nationalChannelName;
    }

    public void setNationalChannelName(String nationalChannelName) {
        this.nationalChannelName = nationalChannelName;
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

    public String getProvinceAgencyCode() {
        return provinceAgencyCode;
    }

    public void setProvinceAgencyCode(String provinceAgencyCode) {
        this.provinceAgencyCode = provinceAgencyCode;
    }

    public String getProvinceAgencyName() {
        return provinceAgencyName;
    }

    public void setProvinceAgencyName(String provinceAgencyName) {
        this.provinceAgencyName = provinceAgencyName;
    }

    public String getAreaHubCode() {
        return areaHubCode;
    }

    public void setAreaHubCode(String areaHubCode) {
        this.areaHubCode = areaHubCode;
    }

    public String getAreaHubName() {
        return areaHubName;
    }

    public void setAreaHubName(String areaHubName) {
        this.areaHubName = areaHubName;
    }

    public String getCreateErp() {
        return createErp;
    }

    public void setCreateErp(String createErp) {
        this.createErp = createErp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateErp() {
        return updateErp;
    }

    public void setUpdateErp(String updateErp) {
        this.updateErp = updateErp;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(Byte configStatus) {
        this.configStatus = configStatus;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }
}