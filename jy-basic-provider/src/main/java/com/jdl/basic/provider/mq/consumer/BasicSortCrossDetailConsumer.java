package com.jdl.basic.provider.mq.consumer;

import com.jd.jmq.common.message.Message;
import com.jd.joyqueue.client.springboot2.annotation.JmqListener;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class BasicSortCrossDetailConsumer {


    @JmqListener(id= "basic-consumer", topics = {"${mq.consumer.topic.sort_cross_detail}"})
    public void onMessage(List<Message> messages) {
        for (Message message: messages) {
            log.info("consumer  basic_sort_cross_detail topic: " + message.getTopic() + " , body: " + message.getText());
        }
    }

}
