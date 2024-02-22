package com.jdl.basic.api.domain.videoTraceCamera;

import java.util.Date;

public class VideoTraceCameraConfig {
    private Integer id;

    private Integer cameraId;

    private String refWorkGridKey;

    private String refGridKey;

    private String machineCode;

    private String chuteCode;

    private String supplyDwsCode;

    private Byte masterCamera;

    private String createErp;

    private Date createTime;

    private String updateErp;

    private Date updateTime;

    private Byte yn;

    private Date ts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCameraId() {
        return cameraId;
    }

    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
    }

    public String getRefWorkGridKey() {
        return refWorkGridKey;
    }

    public void setRefWorkGridKey(String refWorkGridKey) {
        this.refWorkGridKey = refWorkGridKey;
    }

    public String getRefGridKey() {
        return refGridKey;
    }

    public void setRefGridKey(String refGridKey) {
        this.refGridKey = refGridKey;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getChuteCode() {
        return chuteCode;
    }

    public void setChuteCode(String chuteCode) {
        this.chuteCode = chuteCode;
    }

    public String getSupplyDwsCode() {
        return supplyDwsCode;
    }

    public void setSupplyDwsCode(String supplyDwsCode) {
        this.supplyDwsCode = supplyDwsCode;
    }

    public Byte getMasterCamera() {
        return masterCamera;
    }

    public void setMasterCamera(Byte masterCamera) {
        this.masterCamera = masterCamera;
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

    public Byte getYn() {
        return yn;
    }

    public void setYn(Byte yn) {
        this.yn = yn;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }
}