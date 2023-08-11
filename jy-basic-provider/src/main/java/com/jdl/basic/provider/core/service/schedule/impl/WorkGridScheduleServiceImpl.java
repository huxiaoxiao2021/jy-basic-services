package com.jdl.basic.provider.core.service.schedule.impl;

import com.jd.dms.comp.api.log.dto.OperateLogData;
import com.jd.dms.comp.base.ApiRequest;
import com.jd.dms.comp.base.ApiResult;
import com.jd.dms.comp.enums.EventLogEnum;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.schedule.*;
import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridBatchUpdateRequest;
import com.jdl.basic.api.domain.user.UserWorkGridDto;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.schedule.WorkGridScheduleDao;
import com.jdl.basic.provider.core.manager.OperateLogManager;
import com.jdl.basic.provider.core.service.schedule.WorkGridScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WorkGridScheduleServiceImpl implements WorkGridScheduleService {

    @Autowired
    private WorkGridScheduleDao workGridScheduleDao;

    @Autowired
    private OperateLogManager operateLogManager;

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.batchQueryByWorkGridKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<WorkGridSchedule>> batchQueryByWorkGridKey(WorkGridScheduleBatchRequest request) {
        Result<List<WorkGridSchedule>> result = Result.success();
        if (CollectionUtils.isEmpty(request.getWorkGridSchedules())) {
            return result.toFail("网格主键不能为空！");
        }
        return result.setData(workGridScheduleDao.batchQueryByWorkGridKey(request));
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.batchDeleteByWorkGridKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchDeleteByWorkGridKey(WorkGridScheduleBatchRequest request) {
        Result<Boolean> result = Result.success();
        if (CollectionUtils.isEmpty(request.getWorkGridSchedules())) {
            return result.toFail("网格主键不能为空！");
        }
        if (StringUtils.isEmpty(request.getUpdateUserErp())) {
            return result.toFail("修改人erp不能为空！");
        }
        if (request.getUpdateTime() == null) {
            return result.toFail("修改时间不能为空！");
        }
        Boolean deleteResult = workGridScheduleDao.batchDeleteByWorkGridKey(request);
        if (!deleteResult) {
            log.warn("WorkGridScheduleServiceImpl batchDeleteByWorkGridKey 执行失败！");
            throw new RuntimeException("班次时间插入失败！");
        }
        return result.setData(deleteResult);
    }

    @Override
    @Transactional
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.batchInsert", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchInsert(WorkGridScheduleBatchRequest request) {
        Result<Boolean> result = Result.success();
        if (CollectionUtils.isEmpty(request.getWorkGridSchedules())) {
            return result.toFail("班次时间插入记录不能为空！");
        }
        Boolean insertResult = workGridScheduleDao.batchInsert(request);
        if (!insertResult) {
            log.warn("WorkGridScheduleServiceImpl batchInsert 执行失败！");
            throw new RuntimeException("班次时间插入失败！");
        }
        return result.setData(insertResult);
    }

    @Override
    @Transactional
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.batchUpdateWorkGridSchedule", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchUpdateWorkGridSchedule(WorkGridScheduleBatchUpdateRequest request) {
        Result<Boolean> result = Result.success();

        if (StringUtils.isEmpty(request.getUpdateUserErp())) {
            return result.toFail("updateUserErp不能为空！");
        }
        if (StringUtils.isEmpty(request.getUpdateUserName())) {
            return result.toFail("updateUserName不能为空！");
        }
        if (request.getUpdateTime() == null) {
            return result.toFail("修改时间不能为空！");
        }

        // 获取当前代理对象
        WorkGridScheduleService currentProxy = (WorkGridScheduleService) AopContext.currentProxy();

        if (CollectionUtils.isNotEmpty(request.getDeleteWorkGridSchedule())) {
            WorkGridScheduleBatchRequest deleteRequest = new WorkGridScheduleBatchRequest();
            deleteRequest.setWorkGridSchedules(request.getDeleteWorkGridSchedule());
            deleteRequest.setUpdateUserName(request.getUpdateUserName());
            deleteRequest.setUpdateUserErp(request.getUpdateUserErp());
            deleteRequest.setUpdateTime(request.getUpdateTime());
            Result<Boolean> deleteResult = currentProxy.batchDeleteByWorkGridKey(deleteRequest);
            if (deleteResult.isFail()) {
                log.warn("batchUpdateWorkGridSchedule 批量删除失败！" + deleteResult.getMessage());
                throw new RuntimeException(deleteResult.getMessage());
            }
        }

        if (CollectionUtils.isNotEmpty(request.getAddWorkGridSchedule())) {
            WorkGridScheduleBatchRequest insertRequest = new WorkGridScheduleBatchRequest();
            insertRequest.setWorkGridSchedules(request.getAddWorkGridSchedule());
            Result<Boolean> insertResult = currentProxy.batchInsert(insertRequest);
            insertRequest.setUpdateUserName(request.getUpdateUserName());
            insertRequest.setUpdateUserErp(request.getUpdateUserErp());
            insertRequest.setUpdateTime(request.getUpdateTime());
            if (insertResult.isFail()) {
                log.warn("batchUpdateWorkGridSchedule 批量插入失败！" + insertResult.getMessage());
                throw new RuntimeException(insertResult.getMessage());
            }
        }
        ApiResult apiResult = operateLogManager.saveOperateLog(getOperateLog(request));
        if (apiResult.checkFail()) {
            log.warn("操作记录保存失败！message {}", apiResult.getMessage());
        }
        return result;
    }

    private ApiRequest<OperateLogData> getOperateLog(WorkGridScheduleBatchUpdateRequest updateRequest) {
        ApiRequest<OperateLogData> request = new ApiRequest<>();
        OperateLogData opLog = new OperateLogData();
        request.setData(opLog);
        StringBuilder sb = new StringBuilder();

        sb.append(updateRequest.getUpdateUserErp()).append("(").append(updateRequest.getUpdateUserName()).append(")");
        if (CollectionUtils.isNotEmpty(updateRequest.getAddWorkGridSchedule())) {
            sb.append(" 修改班次时间为 ");
            for (WorkGridSchedule schedule : updateRequest.getAddWorkGridSchedule()) {
                sb.append(schedule.getScheduleName()).append("(").append(schedule.getStartTime())
                        .append(Constants.SEPARATOR_HYPHEN).append(schedule.getEndTime()).append(")").append("、");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        opLog.setEventType(EventLogEnum.SCHEDULE.getCode());
        opLog.setEventContent(sb.toString());
        opLog.setUserErp(updateRequest.getUpdateUserErp());
        opLog.setUserErp(updateRequest.getUpdateUserName());
        opLog.setCreateTime(new Date());

        opLog.setEventKey(updateRequest.getWorkGridKey());
        return request;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.querySiteScheduleByCondition", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<WorkGridSchedule>> querySiteScheduleByCondition(WorkGridScheduleRequest request) {
        Result<List<WorkGridSchedule>> result = Result.success();
        if (request.getSiteCode() == null) {
            return result.toFail("场地编码不能为空！");
        }
        if (request.getSourceType() == null) {
            return result.toFail("查询维度不能为空！");
        }
        return result.setData(workGridScheduleDao.querySiteScheduleByCondition(request));
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.queryWorkGridScheduleByKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public WorkGridSchedule queryWorkGridScheduleByKey(WorkGridScheduleRequest request) {
        return workGridScheduleDao.queryWorkGridScheduleByKey(request);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.listWorkGridScheduleByKeys", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public List<WorkGridSchedule> listWorkGridScheduleByKeys(BatchWorkGridScheduleQueryDto dto) {
        return workGridScheduleDao.listWorkGridScheduleByKeys(dto);
    }
}
