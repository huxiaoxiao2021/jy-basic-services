package com.jdl.basic.api.domain.videoTraceCamera;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VideoTraceCameraVo extends VideoTraceCamera{
    private List<VideoTraceCameraConfig> videoTraceCameraConfigList;

    private String gridBusinessKey;

    /**
     * 主摄像头 1是 0否
     */
    private Byte masterCamera;

    /**
     *
     */
    private List<String> workGridNames;
}
