package com.jdl.basic.api.domain.videoTraceCamera;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class VideoTraceCameraConfigVo  implements Serializable {
    /**
     * 关联摄像头ref:video_trace_camera物理主键
     */
    private Integer cameraId;
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
     * 更新人
     */
    private String updateUserErp;
    private String updateErp;

    /**
     * 主摄像头 1是 0否
     */
    private Byte masterCamera;

    private String createErp;

    private Date createTime;

    private Date updateTime;

    /**
     * 配置生效开始时间
     */
    private Date startEffectiveTime;
    /**
     * 配置生效结束时间
     */
    private Date endEffectiveTime;
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

    private List<VideoTraceCameraConfig> videoTraceCameraConfigs;
}
