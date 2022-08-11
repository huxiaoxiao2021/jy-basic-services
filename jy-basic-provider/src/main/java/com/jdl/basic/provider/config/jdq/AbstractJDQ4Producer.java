package com.jdl.basic.provider.config.jdq;

import com.alibaba.fastjson.JSONObject;
import com.jd.bdp.jdq.JDQConfigUtil;
import com.jd.bdp.jdq.JDQ_CLIENT_INFO_ENV;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Properties;


public abstract class AbstractJDQ4Producer implements JDQ4Producer, InitializingBean, DisposableBean {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    protected abstract JdqConfig initJdqConfig();


    KafkaProducer<String, String> producer;
    private JdqConfig jdqConfig;


    @Override
    public void produce(String key, Object value) {
        if (!jdqConfig.isEnabled()) {
            return;
        }

        producer.send(new ProducerRecord<>(jdqConfig.getTopic(), key, JSONObject.toJSONString(value)), (metadata, exception) -> {
            if (exception != null) {
                log.error("JDQ 发送失败,topic:" + jdqConfig.getTopic(), exception);
            }
        });

    }

    @Override
    public void destroy() throws Exception {
        if (!jdqConfig.isEnabled()) {
            return;
        }

        try {
            producer.flush();
            producer.close();
        } catch (Exception e) {
            log.error("JDQ关闭失败,username" + jdqConfig.getClientId(), e);
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {


        jdqConfig = initJdqConfig();
        if (!jdqConfig.isEnabled()) {
            return;
        }
        checkParam(jdqConfig);


        if (jdqConfig.isTestEnv()) {
            JDQ_CLIENT_INFO_ENV.authClientInfoENV("11.7.94.195", 8700);
            JDQ_CLIENT_INFO_ENV.setTestEnv();
        }

        Properties properties = new Properties();
        properties.putAll(JDQConfigUtil.getClientConfigs(jdqConfig.getClientId(), jdqConfig.getDomain(), jdqConfig.getPassword()));

        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "524288");//增加批次效果，性能更好
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 500);//增加批次效果，性能更好
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4");

        if (jdqConfig.getUserDefinedProperties() != null) {
            properties.putAll(jdqConfig.getUserDefinedProperties());
        }

        this.producer = new KafkaProducer<String, String>(properties, new StringSerializer(), new StringSerializer());

    }

    private void checkParam(@NonNull JdqConfig jdqConfig) {
        if (StringUtils.isEmpty(jdqConfig.getDomain()) || StringUtils.isEmpty(jdqConfig.getClientId()) || StringUtils.isEmpty(jdqConfig.getPassword())) {
            throw new IllegalArgumentException("JDQ参数错误");
        }
    }
}
