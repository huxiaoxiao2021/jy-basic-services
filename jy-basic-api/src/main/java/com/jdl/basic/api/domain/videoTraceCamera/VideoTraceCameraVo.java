package com.jdl.basic.api.domain.videoTraceCamera;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VideoTraceCameraVo extends VideoTraceCamera{
    private List<VideoTraceCameraConfig> videoTraceCameraConfigList;

    private String gridBusinessKey;
}
