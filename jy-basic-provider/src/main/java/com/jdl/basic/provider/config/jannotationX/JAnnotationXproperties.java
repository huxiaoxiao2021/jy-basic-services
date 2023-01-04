package com.jdl.basic.provider.config.jannotationX;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author zhangleqi
 * @date 2019/10/21 20:03
 */
@Configuration
@ConfigurationProperties(prefix = "jd.ump")
@Data
@Valid
public class JAnnotationXproperties {

    /**
     * 系统名称
     */
    @NotNull
    private String appName;
    /**
     * 初始化系统存活监控点 key
     */
    @NotNull
    private String systemKey;
    /**
     * 初始化系统的jvm监控 key
     */
    @NotNull
    private String jvmKey;
}
