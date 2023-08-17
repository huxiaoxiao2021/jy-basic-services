package com.jdl.basic.provider.mq.consumer;

import com.jd.jmq.common.message.Message;
import com.jd.joyqueue.client.springboot2.annotation.JmqListener;
import com.jdl.basic.api.domain.schedule.WorkGridSchedule;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleBatchRequest;
import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridBatchUpdateRequest;
import com.jdl.basic.api.domain.user.UserWorkGridRequest;
import com.jdl.basic.api.domain.workStation.WorkGridModifyMqData;
import com.jdl.basic.api.enums.EditTypeEnum;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.core.service.schedule.WorkGridScheduleService;
import com.jdl.basic.provider.core.service.user.UserService;
import com.jdl.basic.provider.core.service.user.UserWorkGridService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 消费删除网格消息
 * 将网格人员分配移出被删除的网格
 */
@Slf4j
@Component
public class WorkGridModifyConsumer {

    @Autowired
    UserService userService;
    @Autowired
    UserWorkGridService userWorkGridService;
    @Autowired
    WorkGridScheduleService workGridScheduleService;

    @JmqListener(id = "jmq4-consumer", topics = {"${mq.consumer.topic.workGridModifyMq}"})
    public void onMessage(List<Message> messages) {
        for (Message message : messages) {
            String text = message.getText();
            if (StringUtils.isEmpty(text)) {
                log.warn("消费网格变更消息体text为空 {}", message.getBusinessId());
                return;
            }
            WorkGridModifyMqData mq = JsonHelper.toObject(text, WorkGridModifyMqData.class);
            if (mq.getGridData() == null
                    || mq.getEditType() == null
                    || mq.getOperateTime() == null
                    || StringUtils.isEmpty(mq.getGridData().getBusinessKey())
                    || StringUtils.isEmpty(mq.getOperateUserCode())
                    || StringUtils.isEmpty(mq.getOperateUserName())) {
                log.warn("消费网格变更 缺少网格信息 {}", text);
                return;
            }
            if (EditTypeEnum.DELETE.getCode().equals(mq.getEditType())) {
                String workGridKey = mq.getGridData().getBusinessKey();
                UserWorkGridRequest request = new UserWorkGridRequest();
                request.setWorkGridKey(workGridKey);
                List<UserWorkGrid> userWorkGrids = userWorkGridService.queryByWorkGridByGridKey(request);
                if (CollectionUtils.isNotEmpty(userWorkGrids)) {
                    // 更新网格分配关系
                    UserWorkGridBatchUpdateRequest batchRequest = new UserWorkGridBatchUpdateRequest();
                    batchRequest.setDeleteUserWorkGrids(userWorkGrids);
                    batchRequest.setUpdateTime(mq.getOperateTime());
                    batchRequest.setUpdateUserErp(mq.getOperateUserCode());
                    batchRequest.setUpdateUserErp(mq.getOperateUserName());
                    userWorkGridService.batchUpdateUserWorkGrid(batchRequest);

                    // 删除被删网格对应的班次时间
                    WorkGridScheduleBatchRequest deleteRequest = new WorkGridScheduleBatchRequest();
                    List<WorkGridSchedule> schedules = new ArrayList<>();
                    WorkGridSchedule schedule = new WorkGridSchedule();
                    schedule.setWorkGridKey(workGridKey);
                    schedules.add(schedule);
                    deleteRequest.setWorkGridSchedules(schedules);
                    workGridScheduleService.batchDeleteByWorkGridKey(deleteRequest);
                }
            }
        }
    }
}
