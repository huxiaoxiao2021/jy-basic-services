package com.jdl.basic.api.domain.boxFlow.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CollectBoxFlowNoticDto implements Serializable {
    /**
     * 激活版本
     */
    private String version;
    /**
     * 生效时间
     */
    private String effectTime;
    /**
     * 操作类型
     */
    private Integer operateType;
}
