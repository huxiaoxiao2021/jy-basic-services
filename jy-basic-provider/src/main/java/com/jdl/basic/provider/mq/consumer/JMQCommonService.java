package com.jdl.basic.provider.mq.consumer;

import com.jd.jmq.common.message.Message;

/**
 * @author zhengchengfa
 * @Date 2021-01-27 14:46
 * MQ通用服务
 */
public interface JMQCommonService {
    //建议是否为uat环境
    boolean checkUatProduct(Message message);
}
