package com.jdl.basic.provider.mq.consumer;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.jmq.common.message.Message;
import com.jd.joyqueue.client.springboot2.annotation.JmqListener;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.api.domain.schedule.WorkGridSchedule;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleBatchRequest;
import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridBatchUpdateRequest;
import com.jdl.basic.api.domain.user.UserWorkGridRequest;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.domain.workStation.WorkGridModifyMqData;
import com.jdl.basic.api.enums.EditTypeEnum;
import com.jdl.basic.common.contants.Constants;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    private static final String DELETE_WORK_GRID_RELEASE_RESOURCE = "删除网格释放资源";

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
                // 更新网格分配关系
                deleteUserWorkGrid(mq);

                // 删除被删网格对应的班次时间
                deleteWorkGridSchedule(mq);
            }
        }
    }

    private void deleteUserWorkGrid(WorkGridModifyMqData mq) {
        CallerInfo info = Profiler.registerInfo(Constants.UMP_APP_NAME + "WorkGridModifyConsumer.deleteUserWorkGrid",
                Constants.UMP_APP_NAME, false, true);
        try {
            WorkGrid workGrid = mq.getGridData();
            String workGridKey = workGrid.getBusinessKey();
            Integer siteCode = workGrid.getSiteCode();

            UserWorkGridRequest request = new UserWorkGridRequest();
            request.setWorkGridKey(workGridKey);
            List<UserWorkGrid> userWorkGrids = userWorkGridService.queryByWorkGridByGridKey(request);

            if (CollectionUtils.isNotEmpty(userWorkGrids)) {
                UserWorkGridBatchUpdateRequest batchRequest = new UserWorkGridBatchUpdateRequest();
                batchRequest.setDeleteUserWorkGrids(userWorkGrids);
                batchRequest.setUpdateTime(mq.getOperateTime());
                batchRequest.setUpdateUserErp(DELETE_WORK_GRID_RELEASE_RESOURCE);
                batchRequest.setUpdateUserName(DELETE_WORK_GRID_RELEASE_RESOURCE);
                batchRequest.setSiteCode(siteCode);
                userWorkGridService.batchUpdateUserWorkGrid(batchRequest);
            }
        } catch (Exception e) {
            Profiler.functionError(info);
            throw e;
        } finally {
            Profiler.registerInfoEnd(info);
        }
    }

    private void deleteWorkGridSchedule(WorkGridModifyMqData mq) {
        CallerInfo info = Profiler.registerInfo(Constants.UMP_APP_NAME + "WorkGridModifyConsumer.deleteWorkGridSchedule",
                Constants.UMP_APP_NAME, false, true);
        try {

            WorkGrid workGrid = mq.getGridData();
            String workGridKey = workGrid.getBusinessKey();
            Integer siteCode = workGrid.getSiteCode();

            WorkGridSchedule workGridSchedule = new WorkGridSchedule();
            workGridSchedule.setWorkGridKey(workGridKey);
            WorkGridScheduleBatchRequest queryRequest = new WorkGridScheduleBatchRequest();
            queryRequest.setWorkGridSchedules(Collections.singletonList(workGridSchedule));
            Result<List<WorkGridSchedule>> result = workGridScheduleService.batchQueryByWorkGridKey(queryRequest);

            if (result != null && CollectionUtils.isNotEmpty(result.getData())) {
                List<WorkGridSchedule> deleteWorkGridSchedules = result.getData().stream().map(item -> {
                    WorkGridSchedule schedule = new WorkGridSchedule();
                    schedule.setScheduleKey(item.getScheduleKey());
                    schedule.setStartTime(item.getStartTime());
                    schedule.setEndTime(item.getEndTime());
                    return schedule;
                }).collect(Collectors.toList());

                WorkGridScheduleBatchRequest deleteRequest = new WorkGridScheduleBatchRequest();
                deleteRequest.setWorkGridSchedules(deleteWorkGridSchedules);
                deleteRequest.setSiteCode(siteCode);
                deleteRequest.setUpdateUserErp(DELETE_WORK_GRID_RELEASE_RESOURCE);
                deleteRequest.setUpdateUserName(DELETE_WORK_GRID_RELEASE_RESOURCE);
                deleteRequest.setUpdateTime(mq.getOperateTime());
                workGridScheduleService.batchDeleteByScheduleKey(deleteRequest);
            }

        } catch (Exception e) {
            Profiler.functionError(info);
            throw e;
        } finally {
            Profiler.registerInfoEnd(info);
        }
    }
}
