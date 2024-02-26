package com.jdl.basic.api.domain.videoTraceCamera;

import lombok.Data;

import java.util.Date;

@Data
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
}