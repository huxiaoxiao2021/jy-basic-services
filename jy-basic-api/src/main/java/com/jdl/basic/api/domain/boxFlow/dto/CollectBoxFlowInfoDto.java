package com.jdl.basic.api.domain.boxFlow.dto;

import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowInfo;
import lombok.Data;

import java.io.Serializable;
@Data
public class CollectBoxFlowInfoDto extends CollectBoxFlowInfo {
    /**
     * 版本状态名称
     */
    private String statusName;

    /**
     * 生效时间
     */
    private String effectTime;
}
