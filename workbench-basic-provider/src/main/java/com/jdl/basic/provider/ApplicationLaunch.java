package com.jdl.basic.provider;

import com.jd.jsf.spring.boot.annotation.EnableJsf;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

@MapperScan(basePackages = {"com.jdl.basic.dao"})
@SpringBootApplication(scanBasePackages = {"com.jdl.basic"})
@PropertySources(
        value = {
                @PropertySource(value = {
                        "classpath:basic-jsf-provider.properties",
                        "classpath:basic-jsf-consumer.properties",
                }, encoding = "utf-8"),
        }
)
@ImportAutoConfiguration(value = SpringBootConfiguration.class)
@EnableJsf
@Slf4j
public class ApplicationLaunch {


    public static void main(String[] args) throws InterruptedException {
        try {
            SpringApplication.run(ApplicationLaunch.class, args);
            Thread.currentThread().join();
        } catch (Exception e) {
            log.error("启动异常",e);
            throw e;
        }
    }
}
