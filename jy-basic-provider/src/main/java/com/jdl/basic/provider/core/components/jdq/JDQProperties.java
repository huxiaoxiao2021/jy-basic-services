package com.jdl.basic.provider.core.components.jdq;

import com.jdl.basic.provider.config.jdq.JdqConfig;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("jdq")
public class JDQProperties {
    boolean enabled;

    List<JdqConfig> producers;

    public JdqConfig getConfigById(String id) {
        if (producers == null) {
            return null;
        }
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("id不能为空");
        }
        for (JdqConfig producerConfig : producers) {
            if (id.equals(producerConfig.getId())) {
                return producerConfig;
            }
        }
        return null;
    }
}
