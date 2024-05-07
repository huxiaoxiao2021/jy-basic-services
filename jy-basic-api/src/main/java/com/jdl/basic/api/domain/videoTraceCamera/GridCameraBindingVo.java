package com.jdl.basic.api.domain.videoTraceCamera;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GridCameraBindingVo implements Serializable {
    /**
     * 用于表格树级结构的key
     */
    private String treeNodeKey;

    /**
     * 用于表格首列，值为网格名称，工序名称，设备编码、设备格口
     */
    private String treeNodeLabel;

    /**
     * 网格业务主键
     */
    private String gridBusinessKey;
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
     * 绑定状态
     */
    private Integer  bindingStatus;

    /**
     * 绑定数量
     */
    private Integer bindingCount;

    private String effectiveStatus;

    /**
     * 工序ref：work_station业务主键
     */
    private String workStationKey;
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
     * 设备编码
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
     * 是否有子节点
     */
    private boolean hasChildren;

    /**
     * 子节点数据
     */
    List<GridCameraBindingVo> children;

    /**
     * 绑定的网格
     */
    List<VideoTraceCameraConfigVo> configs;
}
