package com.jdl.basic.provider.config.jannotationX;

import com.jd.ump.annotation.JAnnotation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * ump监控
 * @author zhangleqi
 * @date 2019/10/21 20:01
 */
@Configuration
public class JAnnotationXConfiguration {

    @Bean(initMethod="afterPropertiesSet")
    public JAnnotation umpJAnnotation() {
        return new JAnnotation();
    }

}
