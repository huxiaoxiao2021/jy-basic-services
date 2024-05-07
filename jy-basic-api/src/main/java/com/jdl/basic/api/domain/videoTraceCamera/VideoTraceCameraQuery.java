package com.jdl.basic.api.domain.videoTraceCamera;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    /**
     * 查询的时间点 格式 "yyyy-MM-dd HH:mm:ss"
     *
     */
    private String operateTimeStr;

    /**
     * 1在线，2离线，3禁用
     */
    private Integer status;

    /**
     * 1正常，0删除
     */
    private Integer yn;

    private int id;

    private int pageSize;
    /**
     * 多个摄像头编码，逗号分割
     */
    private String cameraCodes;

    /**
     * 多个通道编码，逗号分割
     */
    private String nationalChannelCodes;
    /**
     * 摄像头编码
     */
    private List<String> cameraCodeList;

    /**
     * 通道编码
     */
    private List<String> nationalChannelCodeList;
}
