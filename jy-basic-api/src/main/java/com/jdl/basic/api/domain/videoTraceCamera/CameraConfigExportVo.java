package com.jdl.basic.api.domain.videoTraceCamera;

import lombok.Data;

import java.util.Date;

@Data
public class CameraConfigExportVo {
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

    /**
     * 场地类型
     */
    private Integer siteType;

    /**
     * 场地类型名称
     */
    private String siteTypeName;

    /**
     * 场地编码
     */
    private Integer siteCode;

    /**
     * 场地名称
     */
    private String siteName;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 网格号:01~99
     */
    private String gridNo;

    /**
     * 网格ID
     */
    private String gridCode;

    /**
     * 网格名称
     */
    private String gridName;

    /**
     * 作业区编码
     */
    private String areaCode;

    /**
     * 作业区名称
     */
    private String areaName;


    /**
     * 摄像头编码
     */
    private String cameraCode;

    /**
     * 通道号
     */
    private String nationalChannelCode;
    /**
     * 通道名称
     */
    private String nationalChannelName;

    /**
     * 关联网格key:work_grid
     */
    private String refWorkGridKey;
    /**
     * 关联工序ref：work_station业务主键
     */
    private String refWorkStationKey;
    /**
     * 自动化设备编码
     */
    private String machineCode;
    /**
     * 分拣机格口编码
     */
    private String chuteCode;
    /**
     * 分拣机供包台DWS编码
     */
    private String supplyDwsCode;

    /**
     * 工序编码
     */
    private String workCode;

    /**
     * 工序名称
     */
    private String workName;
}
