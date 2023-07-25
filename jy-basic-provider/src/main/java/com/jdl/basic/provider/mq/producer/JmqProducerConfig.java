package com.jdl.basic.provider.mq.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName JmqProducerConfig
 * @Description
 * @Author wyh
 * @Date 2020/8/5 10:29
 **/
@Configuration
public class JmqProducerConfig {

    @Value("${mq.topic.collectBoxFlowNotice}")
    private String collectBoxFlowNoticeTopic;
    
    @Value("${mq.topic.workGridManagerTaskConfigMq}")
    private String workGridManagerTaskConfigMq;
    
    @Bean(name = "collectBoxFlowNoticeMQ")
    public DefaultJMQProducer collectBoxFlowNoticeMQ(){
        DefaultJMQProducer mqProducer = new DefaultJMQProducer();
        mqProducer.setTopic(collectBoxFlowNoticeTopic);
        return mqProducer;
    }
    
    @Bean(name = "workGridManagerTaskConfigMq")
    public DefaultJMQProducer workGridManagerTaskConfigMq(){
        DefaultJMQProducer mqProducer = new DefaultJMQProducer();
        mqProducer.setTopic(workGridManagerTaskConfigMq);
        return mqProducer;
    }

}
