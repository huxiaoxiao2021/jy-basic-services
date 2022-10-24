package com.jdl.basic.provider.config.jannotationX;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author zhangleqi
 * @date 2019/10/21 20:03
 */
@Configuration
public class JAnnotationXproperties {

    /**
     * 系统名称
     */
    @NotNull
    @Value("${jd.ump.appName}")
    private String appName;
    /**
     * 初始化系统存活监控点 key
     */
    @NotNull
    @Value("${jd.ump.systemKey}")
    private String systemKey;
    /**
     * 初始化系统的jvm监控 key
     */
    @NotNull
    @Value("${jd.ump.jvmKey}")
    private String jvmKey;
}