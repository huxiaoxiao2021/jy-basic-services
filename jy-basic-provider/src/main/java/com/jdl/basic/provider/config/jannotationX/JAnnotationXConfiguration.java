package com.jdl.basic.provider.config.jannotationX;

import com.jd.ump.x.JAnnotationX;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ump监控
 * @author zhangleqi
 * @date 2019/10/21 20:01
 */
@Configuration
public class JAnnotationXConfiguration {

    @Bean
    public JAnnotationX jAnnotationX(JAnnotationXproperties jAnnotationXproperties) {
        JAnnotationX jAnnotationX = new JAnnotationX();
        BeanUtils.copyProperties(jAnnotationXproperties,jAnnotationX);
        return jAnnotationX;
    }

}
