package com.jdl.basic.provider.mq.consumer;

import com.jd.jmq.common.message.Message;
import com.jd.joyqueue.client.springboot2.annotation.JmqListener;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.service.site.SiteService;
import com.jdl.basic.provider.dto.BasicSiteChangeMQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 站点变更消息
 *
 * @author hujiping
 * @date 2023/5/30 2:29 PM
 */
@Component
@Slf4j
public class SiteChangeConsumer {

    @Autowired
    private SiteService siteService;

    @JmqListener(id= "basic-consumer", topics = {"${mq.consumer.topic.site_change}"})
    public void onMessage(List<Message> messages) {
        for (Message message : messages) {

            String content = message.getText();
            log.info("consumer site_change topic: " + message.getTopic() + " , body: " + content);
            if (StringUtils.isEmpty(content)) {
                return;
            }
            BasicSiteChangeMQ basicSiteChangeMQ = null;
            try {
                basicSiteChangeMQ = JsonHelper.toObject(content, BasicSiteChangeMQ.class);
            } catch (Exception e) {
                log.error("滑道笼车配置消息解析异常！{}{}", content, e);
            }
            siteService.updateBasicSite(basicSiteChangeMQ);
        }
    }
    
}
