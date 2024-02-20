package com.jdl.basic.provider.mq.consumer;

import com.jd.jmq.common.message.Message;
import com.jd.joyqueue.client.springboot2.annotation.JmqListener;
import com.jd.throttle.core.Entry;
import com.jd.throttle.core.ThrottleGuard;
import com.jd.throttle.core.slots.block.BlockException;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.domain.workStation.WorkGridBatchUpdateRequest;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.service.workStation.WorkGridService;
import com.jdl.basic.provider.dto.BasicSiteChangeMQ;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteChangeConsumer.onMessage", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public void onMessage(List<Message> messages) {
        for (Message message : messages) {

            String content = message.getText();
            log.info("consumer site_change topic: " + message.getTopic() + " , body: " + content);
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
            if (mq == null || mq.getSiteCode() == null || StringUtils.isEmpty(mq.getSiteName()) || StringUtils.isEmpty(mq.getProvinceAgencyCode()) || StringUtils.isEmpty(mq.getProvinceAgencyName())) {
                log.warn("场地变更消息缺少必要字段 {}", content);
                return;
            }
            // 更新网格信息
            updateWorkGridBySiteCodeWithLimit(mq);
        }
    }

    /**
     * 侵入式 限流模式的执行方法
     * @param mq
     */
    private void updateWorkGridBySiteCodeWithLimit(BasicSiteChangeMQ mq){
        Entry entry = null;
        try {
            entry = ThrottleGuard.entry("SITE_CHANGE_DEAL_LIMIT");
            // 您需要限流的代码逻辑
            updateWorkGridBySiteCode(mq);
        } catch (BlockException e) {
            // 发生限流/熔断事件时，触发BlockException
            log.warn("消费站点变更消息时触发限流,{}",JsonHelper.toJSONString(mq), e);
            throw new RuntimeException("消费站点变更消息时触发限流");
        } finally {
            if (entry != null) {
                // 清理API，用于清理资源和采集访问时延，为监控和熔断等服务提供数据
                entry.exit();
            }
        }

    }

    private void updateWorkGridBySiteCode(BasicSiteChangeMQ mq) {
        CallerInfo info = Profiler.registerInfo(Constants.UMP_APP_NAME + "SiteChangeConsumer.updateWorkGridBySiteCode",
                Constants.UMP_APP_NAME, false, true);
        try {
            WorkGrid query = new WorkGrid();
            query.setSiteCode(mq.getSiteCode());
            List<WorkGrid> workGrids = workGridService.queryWorkGrid(query);

            if (CollectionUtils.isNotEmpty(workGrids)) {
                List<Long> ids = new ArrayList<>();
                for (WorkGrid workGrid : workGrids) {
                    if (!Objects.equals(workGrid.getProvinceAgencyCode(), mq.getProvinceAgencyCode())
                            || !Objects.equals(workGrid.getProvinceAgencyName(), mq.getProvinceAgencyName())
                            || !Objects.equals(workGrid.getSiteName(), mq.getSiteName())) {
                        ids.add(workGrid.getId());
                    }
                }
                if (CollectionUtils.isNotEmpty(ids)) {
                    WorkGridBatchUpdateRequest updateRequest = new WorkGridBatchUpdateRequest();
                    updateRequest.setIds(ids);
                    updateRequest.setProvinceAgencyCode(mq.getProvinceAgencyCode());
                    updateRequest.setProvinceAgencyName(mq.getProvinceAgencyName());
                    updateRequest.setSiteCode(mq.getSiteCode());
                    updateRequest.setSiteName(mq.getSiteName());
                    updateRequest.setUpdateUser(UPDATE_USER_ERP);
                    updateRequest.setUpdateUserName(UPDATE_USER_NAME);
                    updateRequest.setUpdateTime(new Date());

                    int result = workGridService.batchUpdateByIds(updateRequest);
                    if (result <= 0) {
                        log.info("消费场地信息变更失败 {}", JsonHelper.toJSONString(mq));
                    }
                }
            }
        } catch (Exception e) {
            Profiler.functionError(info);
            throw e;
        } finally {
            Profiler.registerInfoEnd(info);
        }

    }

}
