package com.jdl.basic.provider.mq.consumer;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.jmq.common.message.Message;
import com.jd.joyqueue.client.springboot2.annotation.JmqListener;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.api.domain.schedule.UserScheduleUpdateMQ;
import com.jdl.basic.api.domain.schedule.WorkGridSchedule;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleBatchRequest;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleBatchUpdateRequest;
import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridBatchUpdateRequest;
import com.jdl.basic.api.domain.user.UserWorkGridRequest;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.domain.workStation.WorkGridModifyMqData;
import com.jdl.basic.api.enums.EditTypeEnum;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.core.service.schedule.WorkGridScheduleService;
import com.jdl.basic.provider.core.service.user.UserService;
import com.jdl.basic.provider.core.service.user.UserWorkGridService;
import com.jdl.basic.provider.mq.producer.DefaultJMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
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

    @Autowired
    @Qualifier("userScheduleUpdateMq")
    DefaultJMQProducer mqProducer;

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
                List<WorkGridSchedule> deleteWorkGridSchedules = result.getData();

                WorkGridScheduleBatchUpdateRequest deleteRequest = new WorkGridScheduleBatchUpdateRequest();
                deleteRequest.setDeleteWorkGridSchedule(deleteWorkGridSchedules);
                deleteRequest.setSiteCode(siteCode);
                deleteRequest.setUpdateUserErp(DELETE_WORK_GRID_RELEASE_RESOURCE);
                deleteRequest.setUpdateUserName(DELETE_WORK_GRID_RELEASE_RESOURCE);
                deleteRequest.setUpdateTime(mq.getOperateTime());
                workGridScheduleService.batchUpdateWorkGridSchedule(deleteRequest);

                produceDeleteUserScheduleMessage(deleteWorkGridSchedules);
            }

        } catch (Exception e) {
            Profiler.functionError(info);
            throw e;
        } finally {
            Profiler.registerInfoEnd(info);
        }
    }

    /**
     * 发送删除排班计划mq
     * @param deletedSchedules 需要删除的班次
     */
    private void produceDeleteUserScheduleMessage(List<WorkGridSchedule> deletedSchedules) {
        for (WorkGridSchedule deletedSchedule : deletedSchedules) {
            Date validStartTime = getValidStartTime(deletedSchedule);
            Date currentTime = new Date();

            UserScheduleUpdateMQ mq;
            // 当前时间在班次开始生效时间之前  删除今天以后的排班计划（包含今天）
            if (!currentTime.before(validStartTime)) {
                mq = getUserScheduleUpdateMQ(deletedSchedule, DateHelper.getDateOfyyMMdd2(currentTime));
            } else { //删除明天以后的排班计划
                mq = getUserScheduleUpdateMQ(deletedSchedule, DateHelper.getDateOfyyMMdd2(DateUtils.addDays(currentTime, 1)));
            }
            String businessId = mq.getScheduleKey() + Constants.COLON + mq.getScheduleDate();
            mqProducer.sendOnFailPersistent(businessId, JsonHelper.toJSONString(mq));
        }
    }

    /**
     * 获取班次当天开始生效时间
     * @param workGridSchedule 班次
     * @return
     */
    private Date getValidStartTime(WorkGridSchedule workGridSchedule) {
        String[] splits = workGridSchedule.getStartTime().split(Constants.COLON);
        Integer hour = Integer.valueOf(splits[0]);
        Integer minute = Integer.valueOf(splits[1]);
        Date validStartTime = DateUtils.truncate(new Date(), Calendar.DATE);
        validStartTime = DateUtils.addMinutes(DateUtils.addHours(validStartTime, hour), minute);
        return validStartTime;
    }

    private UserScheduleUpdateMQ getUserScheduleUpdateMQ(WorkGridSchedule workGridSchedule, String scheduleDate) {
        UserScheduleUpdateMQ mq = new UserScheduleUpdateMQ();
        mq.setScheduleKey(workGridSchedule.getScheduleKey());
        mq.setScheduleDate(scheduleDate);
        mq.setStartTime(workGridSchedule.getStartTime());
        mq.setEndTime(workGridSchedule.getEndTime());
        mq.setUpdateUserErp("sys删除网格");
        mq.setUpdateUserName("sys删除网格");
        mq.setDeleteFlag(true);
        return mq;
    }
}
