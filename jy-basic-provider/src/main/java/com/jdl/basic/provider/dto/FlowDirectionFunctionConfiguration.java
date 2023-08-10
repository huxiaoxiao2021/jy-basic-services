package com.jdl.basic.provider.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 流向和功能对应表
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "flowDirectionFunction")
public class FlowDirectionFunctionConfiguration implements Serializable {

    private static final long serialVersionUID = 6449426939149112137L;
    private Map<Integer, List<String>> functionMap;

    public List<String> getFunctionByFlowDirection(Integer flowDirectionType) {
        if (functionMap == null) {
            return null;
        }
        return functionMap.get(flowDirectionType);
    }
}