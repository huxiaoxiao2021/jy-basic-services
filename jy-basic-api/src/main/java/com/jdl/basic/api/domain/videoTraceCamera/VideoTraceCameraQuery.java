package com.jdl.basic.api.domain.videoTraceCamera;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class VideoTraceCameraQuery extends BasePagerCondition implements Serializable {

    private Integer siteCode;

    private String provinceAgencyCode;

    private String areaHubCode;

    private String cameraCode;

    private String videoRecorderCode;

    private Byte configStatus;
}
