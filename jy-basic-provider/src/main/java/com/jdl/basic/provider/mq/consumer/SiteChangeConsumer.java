package com.jdl.basic.provider.mq.consumer;

import com.jd.jmq.common.message.Message;
import com.jd.joyqueue.client.springboot2.annotation.JmqListener;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.site.SiteService;
import com.jdl.basic.provider.core.service.workStation.WorkGridService;
import com.jdl.basic.provider.dto.BasicSiteChangeMQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
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

    private final String UPDATE_USER_ERP = "sys";

    private final String UPDATE_USER_NAME = "场地信息变更";


    @Autowired
    private WorkGridService workGridService;


    @JmqListener(id= "jmq2-consumer", topics = {"${mq.consumer.topic.site_change}"})
    public void onMessage(List<Message> messages) {
        for (Message message : messages) {

            String content = message.getText();
            log.error("consumer site_change topic: " + message.getTopic() + " , body: " + content);
            if (StringUtils.isEmpty(content)) {
                return;
            }
            BasicSiteChangeMQ mq = null;
            try {
                mq = JsonHelper.toObject(content, BasicSiteChangeMQ.class);
            } catch (Exception e) {
                log.error("场地变更消息解析异常！{}{}", content, e);
                return;
            }
//            siteService.updateBasicSite(mq);
            if (mq.getSiteCode() == null || StringUtils.isEmpty(mq.getSiteName()) || StringUtils.isEmpty(mq.getProvinceAgencyCode()) || StringUtils.isEmpty(mq.getProvinceAgencyName())) {
                log.error("场地变更消息缺少必要字段 {}", content);
                return;
            }
            // 更新网格信息
            updateWorkGridBySiteCode(mq);
        }
    }

    private void updateWorkGridBySiteCode(BasicSiteChangeMQ mq) {

        WorkGrid workGrid = new WorkGrid();
        workGrid.setProvinceAgencyCode(mq.getProvinceAgencyCode());
        workGrid.setProvinceAgencyName(mq.getProvinceAgencyName());
        workGrid.setSiteCode(mq.getSiteCode());
        workGrid.setSiteName(mq.getSiteName());
        workGrid.setUpdateUser(UPDATE_USER_ERP);
        workGrid.setUpdateUserName(UPDATE_USER_NAME);
        workGrid.setUpdateTime(new Date());
        Result<Boolean> updateResult = workGridService.updateBySiteCode(workGrid);
        if (updateResult.isFail()) {
            log.error("消费场地信息变更失败 {}", JsonHelper.toJSONString(mq));
        }
    }
    
}
