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
     * 操作类型: 1 新增 2 激活 3 回滚 4路由校验预警
     */
    private Integer operateType;


    /**
     * 信息接收人erp ,以英文逗号分割
     */
    private String receiveErps;

    /**
     * 通知消息
     */
    private String message;
}
