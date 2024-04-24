package com.jdl.basic.provider.mq.consumer;

import com.jd.joyqueue.client.springboot2.annotation.JmqListener;
import com.jd.jmq.common.message.Message;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.workStation.UpdateRequest;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.api.domain.workStation.WorkStationGridDeleteMqData;
import com.jdl.basic.api.enums.EditTypeEnum;
import com.jdl.basic.api.service.workStation.WorkStationGridJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.contants.ResultCodeConstant;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.JYBasicRpcException;
import com.jdl.basic.provider.mq.producer.DefaultJMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author pengchong28
 * @description 网格工序删除消费者
 * @date 2024/4/22
 */
@Slf4j
@Component
public class WorkStationGridDeleteConsumer {

    @Autowired
    WorkStationGridJsfService workStationGridJsfService;

    @Autowired
    @Qualifier("workStationGridDeleteMq")
    DefaultJMQProducer workStationGridDeleteMq;

    @JmqListener(id = "jmq4-consumer", topics = {"${mq.consumer.topic.workStationGridDeleteMq}"})
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridDeleteConsumer.onMessage", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    public void onMessage(List<Message> messages) {
        for (Message message : messages) {
            String text = message.getText();
            if (StringUtils.isEmpty(text)) {
                log.warn("消费网格工序删除消息体text为空 {}", message.getBusinessId());
                return;
            }
            if (log.isInfoEnabled()){
                log.info("消费网格工序删除消息体text {}", JsonHelper.toJSONString(messages));
            }
            WorkStationGridDeleteMqData mqData = JsonHelper.toObject(text, WorkStationGridDeleteMqData.class);
            if (mqData == null || mqData.getEditType() == null) {
                log.warn("消费网格工序,缺少操作类型 {}", text);
                return;
            }
            if (EditTypeEnum.DELETE.getCode().equals(mqData.getEditType())) {
                doDelete(message, mqData);
            }
            if (EditTypeEnum.MODIFY.getCode().equals(mqData.getEditType())) {
                doUpdate(message, mqData);
            }

        }
    }

    /**
     * 执行删除操作
     * @param message 消息对象
     * @param mqData MQ数据对象
     * @return 删除结果，布尔值
     * @throws JYBasicRpcException 当其他用户正在修改网格工序信息时抛出异常
     */
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridDeleteConsumer.doDelete", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    private boolean doDelete(Message message, WorkStationGridDeleteMqData mqData) {
            WorkStationGrid workStationGrid = mqData.getWorkStationGrid();
            if (Objects.isNull(workStationGrid)) {
                log.warn("消费网格工序删除消息,删除对象为空 {}", message.getBusinessId());
                return true;
            }
            Result<Boolean> booleanResult = workStationGridJsfService.deleteById(workStationGrid);
            // 当执行结果获取锁失败，抛异常mq重试
            if (Objects.nonNull(booleanResult) && Objects.equals(booleanResult.getCode(), ResultCodeConstant.LOCK_FAIL)) {
               throw new JYBasicRpcException("其他用户正在修改网格工序信息，请稍后操作！");
            }
            // 当执行结果不等于获取锁失败的状态，更新数据状态
            if (Objects.nonNull(booleanResult) && !Objects.equals(booleanResult.getCode(), ResultCodeConstant.LOCK_FAIL)) {
                UpdateRequest<WorkStationGrid> request = new UpdateRequest<>();
                request.setDataList(Collections.singletonList(workStationGrid));
                Result<Boolean> result = workStationGridJsfService.updatePassByIds(request);
                // 当执行结果等于获取锁失败的状态，重试更新数据状态
                if (Objects.nonNull(result) && Objects.equals(result.getCode(), ResultCodeConstant.LOCK_FAIL)) {
                    String refWorkGridKey = workStationGrid.getBusinessKey();
                    request.setOperateBusinessKey(refWorkGridKey);
                    WorkStationGridDeleteMqData mq = new WorkStationGridDeleteMqData();
                    mq.setEditType(EditTypeEnum.MODIFY.getCode());
                    mq.setUpdateRequest(request);
                    workStationGridDeleteMq.sendOnFailPersistent(refWorkGridKey, JsonHelper.toJSONString(mqData));
                    log.info("网格工序删除失败重试，更新数据状态发送MQ{}", JsonHelper.toJSONString(mqData));
                }
            }
        return false;
    }

    /**
     * 执行更新操作
     * @param message 消息对象
     * @param mqData MQ数据对象
     * @return 更新是否成功的布尔值
     * @throws JYBasicRpcException 当其他用户正在更新网格工序审批通过状态时抛出异常
     */
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridDeleteConsumer.doUpdate", jAppName=Constants.UMP_APP_NAME, mState={
        JProEnum.TP,JProEnum.FunctionError})
    private boolean doUpdate(Message message, WorkStationGridDeleteMqData mqData) {
        if (EditTypeEnum.MODIFY.getCode().equals(mqData.getEditType())) {
            UpdateRequest<WorkStationGrid> updateRequest = mqData.getUpdateRequest();
            if (Objects.isNull(updateRequest)) {
                log.warn("消费网格工序删除消息,更新对象为空 {}", message.getBusinessId());
                return true;
            }
            Result<Boolean> result = workStationGridJsfService.updatePassByIds(updateRequest);
            // 当执行结果等于获取锁失败的状态，抛异常mq重试
            if (Objects.nonNull(result) && Objects.equals(result.getCode(), ResultCodeConstant.LOCK_FAIL)) {
                throw new JYBasicRpcException("其他用户正在更新网格工序审批通过状态，请稍后操作！");
            }
        }
        return false;
    }
}
