package com.jdl.basic.provider.mq.producer;

import com.google.common.collect.Lists;
import com.jd.jmq.client.producer.MessageProducer;
import com.jd.jmq.common.exception.JMQException;
import com.jd.jmq.common.message.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Slf4j
public class DefaultJMQProducer {


    @Autowired(required = false)
    @Qualifier("jyBasicProducerSplit")
    private MessageProducer envMessageProducer;

    /**
     * 消息主题
     */
    @Setter
    @Getter
    private String topic;

    /**
     * 超时时间-默认值1000ms
     */
    @Setter
    private int timeout = 1000;

    /**
     * 批量提交 一次提交数量
     */
    @Setter
    private int batchSize = 100;

    /**
     * JMQ消息发送,抛出异常，需要自行处理
     *
     * @param businessId 业务ID号
     * @param body       消息体
     * @throws JMQException
     */
    public void send(String businessId, String body) throws JMQException {
        if (log.isDebugEnabled()) {
            log.debug("推送MQ数据为topic:{}->body:{}", this.topic, body);
        }
        Message message = new Message(this.topic, body, businessId);
        envMessageProducer.send(message, this.timeout);
    }

    public void sendOnFailPersistent(String businessId, String body) {
        try {
            send(businessId, body);
        } catch (Throwable ex) {
            log.error("MQ发送失败，将进行消息队列持久化:{}",businessId, ex);
            persistent(businessId, body);
        }
    }

    private void persistent(String businessId, String body) {
        try {
            //待实现
        } catch (Throwable throwable) {
            log.error("消息队列持久化:{}",businessId, throwable);
        }
    }

    /**
     * 批量JMQ消息发送
     *
     * @param messages
     * @throws JMQException
     */
    public void batchSend(List<Message> messages) throws JMQException {
        if (messages != null && !messages.isEmpty()) {
            messages.forEach(msg -> msg.setTopic(topic));
            int size = messages.size();
            if (size <= this.batchSize) {
                envMessageProducer.send(messages, this.timeout);
            } else {
                List<List<Message>> partition = Lists.partition(messages, batchSize);
                for(List<Message> list: partition){
                    envMessageProducer.send(list, this.timeout);
                }
            }
        }
    }

    /**
     * 批量JMQ消息发送，发送时出现异常直接落库
     *
     * @param messages
     */
    public void batchSendOnFailPersistent(List<Message> messages) {
        try {
            this.batchSend(messages);
        } catch (Throwable ex) {
            log.error("批量MQ发送失败，将进行消息队列持久化", ex);
            this.batchPersistent(messages);
        }
    }

    private void batchPersistent(List<Message> messages) {
        try {
            //待实现
        } catch (Throwable throwable) {
            log.error("消息队列持久化", throwable);
        }
    }

    /**
     * 获取JMQ消息体
     *
     * @param businessId
     * @param body
     * @return
     */
    public Message getMessage(String businessId, String body) {
        return new Message(this.topic, body, businessId);
    }

}
