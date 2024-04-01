package com.jdl.basic.provider;

import com.jd.security.configsec.spring.config.JDSecurityPropertyCleanService;
import com.jd.security.configsec.spring.config.JDSecurityPropertySourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * @ProjectName：JY-MSP
 * @Package： com.jd.bluedragon.dms.msp.provider
 * @ClassName: ApplicationLaunch
 * @Description:
 * @CreateDate 2022/4/21 17:40
 * @Copyright: Copyright (c)2020 JDL.CN All Right Reserved
 * @Since: JDK 1.8
 * @Version： V1.0
 */

@PropertySources(
        value = {
                @PropertySource(value = {"classpath:important.properties"}, encoding = "utf-8", factory = JDSecurityPropertySourceFactory.class),
                @PropertySource(value = {"classpath:aces.properties"}, encoding = "utf-8", factory = JDSecurityPropertySourceFactory.class)
        }
)
@Import(JDSecurityPropertyCleanService.class)
@MapperScan(basePackages = {"com.jdl.basic.provider.core.dao"})
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class}, scanBasePackages = {"com.jdl.basic"})
@ImportResource(value = {
        "classpath:spring/spring-context.xml"
})
@ImportAutoConfiguration(value = SpringBootConfiguration.class)
@Slf4j
public class ApplicationLaunch extends SpringBootServletInitializer {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ApplicationLaunch.class, args);
            log.info("ServiceBootApplication start success!");
        }
        catch (Exception e) {
            log.error("ServiceBootApplication start error",e);
        }
    }
}
