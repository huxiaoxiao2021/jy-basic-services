package com.jdl.basic.api.domain.videoTraceCamera;

import lombok.Data;

import java.util.Date;

@Data
public class VideoTraceCameraImport {

    private String stationGridKey;
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
    private String siteCode;


    private String updateErp;

    private Date updateTime;
    /**
     *
     */
    private Byte status;

}
