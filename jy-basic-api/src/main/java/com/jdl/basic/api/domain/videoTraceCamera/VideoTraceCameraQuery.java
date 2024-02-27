package com.jdl.basic.api.domain.videoTraceCamera;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class VideoTraceCameraQuery extends BasePagerCondition implements Serializable {

    /**
     * 场地编码
     */
    private Integer siteCode;

    /**
     * 省区编码
     */
    private String provinceAgencyCode;

    /**
     * 枢纽编码
     */
    private String areaHubCode;

    /**
     * 摄像头编码
     */
    private String cameraCode;

    /**
     * 通道编码
     */
    private String nationalChannelCode;

    /**
     * 配置状态
     */
    private Byte configStatus;

    private int id;

    private int pageSize;
}
