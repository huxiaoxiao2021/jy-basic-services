package com.jdl.basic.provider.config.ducc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DUCC配置类
 *
 * @author hujiping
 * @date 2021/3/19 10:36 上午
 */
@Configuration
public class DuccConfig {
    @Bean(name = "duccPropertyConfiguration")
    public DuccPropertyConfiguration duccPropertyConfiguration(){
        return new DuccPropertyConfiguration();
    }
}