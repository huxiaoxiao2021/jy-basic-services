package com.jdl.basic.provider.mq.consumer;

import com.jd.jmq.common.message.Message;
import com.jd.joyqueue.client.springboot2.annotation.JmqListener;

import java.util.List;

import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.service.cross.SortCrossService;
import com.jdl.basic.provider.dto.SortCrossModifyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class BasicSortCrossDetailConsumer {

    @Autowired
    private SortCrossService sortCrossService;

    @JmqListener(id= "basic-consumer", topics = {"${mq.consumer.topic.sort_cross_detail}"})
    public void onMessage(List<Message> messages) {
        for (Message message : messages) {

            String content = message.getText();
            log.info("consumer  basic_sort_cross_detail topic: " + message.getTopic() + " , body: " + content);
            if (StringUtils.isEmpty(content)) {
                return;
            }
            SortCrossModifyDto modifyDto = null;
            try {
                modifyDto = JsonHelper.toObject(content, SortCrossModifyDto.class);
            } catch (Exception e) {
                log.error("滑道笼车配置消息解析异常！{}{}", content, e);
            }
            if (!sortCrossService.syncSortCross(modifyDto)) {
                log.error("同步滑道笼车配置数据异常, body:[{}]", JsonHelper.toJSONString(content));
                throw new RuntimeException("同步滑道笼车配置数据异常");
            }
        }
    }

}
