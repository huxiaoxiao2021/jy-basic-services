package com.jdl.basic.api.domain.videoTraceCamera;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class VideoTraceCameraConfigQuery implements Serializable {
    /**
     * 关联网格key:work_grid
     */
    private String workGridKey;
    /**
     * 关联工序ref：work_station业务主键
     */
    private String workStationKey;
    /**
     * 自动化设备编码
     */
    private String machineCode;
    /**
     * 设备格口编码
     */
    private String chuteCode;
    /**
     * 设备DWS编码
     */
    private String supplyDwsCode;

    /**
     * 操作时间
     */
    private Date operaterTime;
}
