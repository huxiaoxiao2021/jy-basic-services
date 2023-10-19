package com.jdl.basic.provider.core.service.schedule.impl;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.schedule.*;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.schedule.WorkGridScheduleDao;
import com.jdl.basic.provider.core.service.schedule.WorkGridScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class WorkGridScheduleServiceImpl implements WorkGridScheduleService {

    @Autowired
    private WorkGridScheduleDao workGridScheduleDao;


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
        }
        return result.setData(deleteResult);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.batchInsert", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchInsert(WorkGridScheduleBatchRequest request) {
        Result<Boolean> result = Result.success();
        if (CollectionUtils.isEmpty(request.getWorkGridSchedules())) {
            return result.toFail("班次时间插入记录不能为空！");
        }
        Boolean insertResult = workGridScheduleDao.batchInsert(request);
        if (!insertResult) {
            log.warn("WorkGridScheduleServiceImpl batchInsert 执行失败！");
            throw new RuntimeException("批量插入记录失败！");
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

        if (CollectionUtils.isNotEmpty(request.getDeleteWorkGridSchedule())) {
            WorkGridScheduleBatchRequest deleteRequest = buildScheduleBatchRequest(request, request.getDeleteWorkGridSchedule());
            Result<Boolean> deleteResult = batchDeleteByWorkGridKey(deleteRequest);
            if (deleteResult.isFail()) {
                log.warn("batchUpdateWorkGridSchedule 批量删除失败！" + deleteResult.getMessage());
            }
        }

        if (CollectionUtils.isNotEmpty(request.getAddWorkGridSchedule())) {
            WorkGridScheduleBatchRequest insertRequest = buildScheduleBatchRequest(request, request.getAddWorkGridSchedule());
            Result<Boolean> insertResult = batchInsert(insertRequest);
            if (insertResult.isFail()) {
                log.warn("batchUpdateWorkGridSchedule 批量插入失败！" + insertResult.getMessage());
                throw new RuntimeException(insertResult.getMessage());
            }
        }
        return result.setData(Boolean.TRUE);
    }

    private WorkGridScheduleBatchRequest buildScheduleBatchRequest(WorkGridScheduleBatchUpdateRequest inputRequest, List<WorkGridSchedule> workGridSchedules) {
        WorkGridScheduleBatchRequest outputRequest = new WorkGridScheduleBatchRequest();
        outputRequest.setWorkGridSchedules(workGridSchedules);
        outputRequest.setUpdateUserName(inputRequest.getUpdateUserName());
        outputRequest.setUpdateUserErp(inputRequest.getUpdateUserErp());
        outputRequest.setUpdateTime(inputRequest.getUpdateTime());
        return outputRequest;
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

    @Override
    public WorkGridSchedule queryWorkGridScheduleByName(WorkGridScheduleRequest request) {
        return workGridScheduleDao.queryWorkGridScheduleByName(request);
    }
}
