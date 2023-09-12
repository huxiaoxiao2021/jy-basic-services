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
    private SiteService siteService;

    @Autowired
    private WorkGridService workGridService;

    @Autowired
    private BaseMajorManager baseMajorManager;

    @JmqListener(id= "jmq2-consumer", topics = {"${mq.consumer.topic.site_change}"})
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
                log.error("场地变更消息解析异常！{}{}", content, e);
                return;
            }
//            siteService.updateBasicSite(basicSiteChangeMQ);
            if (basicSiteChangeMQ.getSiteCode() == null) {
                log.warn("场地变更消息缺少必要字段 {}", content);
                return;
            }
            BaseStaffSiteOrgDto siteOrgDto = baseMajorManager.getBaseSiteBySiteId(basicSiteChangeMQ.getSiteCode());
            if (siteOrgDto == null) {
                log.warn("根据场地编码找不到场地 siteCode={}", basicSiteChangeMQ.getSiteCode());
                return;
            }
            WorkGrid workGrid = new WorkGrid();
            workGrid.setProvinceAgencyCode(siteOrgDto.getProvinceAgencyCode());
            workGrid.setProvinceAgencyName(siteOrgDto.getProvinceAgencyName());
            workGrid.setSiteCode(siteOrgDto.getSiteCode());
            workGrid.setSiteName(siteOrgDto.getSiteName());
            workGrid.setUpdateUser(UPDATE_USER_ERP);
            workGrid.setUpdateUserName(UPDATE_USER_NAME);
            workGrid.setUpdateTime(new Date());
            Result<Boolean> updateResult = workGridService.updateBySiteCode(workGrid);
            if (updateResult.isFail()) {
                log.warn("消费场地信息变更失败 {}", content);
            }
        }
    }
    
}
