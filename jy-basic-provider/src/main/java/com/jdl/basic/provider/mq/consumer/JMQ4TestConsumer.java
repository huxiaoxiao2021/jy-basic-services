package com.jdl.basic.provider.mq.consumer;

import com.jd.jmq.common.message.Message;
import com.jd.joyqueue.client.springboot2.annotation.JmqListener;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class JMQ4TestConsumer {



    @JmqListener(id= "consumer-jmq4", topics = {"${mq.topic.jmq4-test-topic}"})
    public void onMessage1(List<Message> messages) {
        for (Message message: messages) {
            log.info("android-consumer:topic: " + message.getTopic() + " , body: " + new String(message.getByteBody()));
        }
    }

}
