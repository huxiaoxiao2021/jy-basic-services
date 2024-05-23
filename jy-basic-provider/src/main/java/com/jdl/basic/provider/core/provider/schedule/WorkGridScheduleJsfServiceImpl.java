package com.jdl.basic.provider.core.provider.schedule;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.schedule.*;
import com.jdl.basic.api.service.schedule.WorkGridScheduleJsfService;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.core.service.schedule.WorkGridScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("workGridScheduleJsfServiceImpl")
@Slf4j
public class WorkGridScheduleJsfServiceImpl implements WorkGridScheduleJsfService {

    @Autowired
    private WorkGridScheduleService workGridScheduleService;

    @Override
    public Result<List<WorkGridSchedule>> batchQueryByWorkGridKey(WorkGridScheduleBatchRequest request) {
        return workGridScheduleService.batchQueryByWorkGridKey(request);
    }

    @Override
    @Deprecated
    public Result<Boolean> batchDeleteByWorkGridKey(WorkGridScheduleBatchRequest request) {
        return workGridScheduleService.batchDeleteByWorkGridKey(request);
    }

    @Override
    public Result<Boolean> batchInsert(WorkGridScheduleBatchRequest request) {
        return workGridScheduleService.batchInsert(request);
    }

    @Override
    public Result<Boolean> batchUpdateWorkGridSchedule(WorkGridScheduleBatchUpdateRequest request) {
        return workGridScheduleService.batchUpdateWorkGridSchedule(request);
    }

    @Override
    public Result<List<WorkGridSchedule>> querySiteScheduleByCondition(WorkGridScheduleRequest request) {
        return workGridScheduleService.querySiteScheduleByCondition(request);
    }

    @Override
    public Result<WorkGridSchedule> queryWorkGridScheduleByKey(WorkGridScheduleRequest request) {
        return Result.success(workGridScheduleService.queryWorkGridScheduleByKey(request));
    }

    @Override
    public Result<List<WorkGridSchedule>> listWorkGridScheduleByKeys(BatchWorkGridScheduleQueryDto dto) {
        return Result.success(workGridScheduleService.listWorkGridScheduleByKeys(dto));
    }

    @Override
    public Result<WorkGridSchedule> queryWorkGridScheduleByName(WorkGridScheduleRequest request) {
        return Result.success(workGridScheduleService.queryWorkGridScheduleByName(request));
    }

    @Override
    public Result<Boolean> cleanWorkGridScheduleOldTime(BatchCleanOldTimeRequest request) {
        return workGridScheduleService.cleanWorkGridScheduleOldTime(request);
    }

    @Override
    public Result<List<WorkGridSchedule>> queryTodayDeletedSiteSchedule(WorkGridScheduleRequest request) {
        return workGridScheduleService.queryTodayDeletedSiteSchedule(request);
    }

    @Override
    public Result<List<ScheduleValidTimeDto>> listValidCutWorkGridScheduleByTime(ValidWorkGridScheduleRequest request) {
        log.info("{} listValidCutWorkGridScheduleByTime req:{}",request.getRequestId(),JsonHelper.toJSONString(request));
        try {
            Result<List<ScheduleValidTimeDto>> rs =workGridScheduleService.listValidCutWorkGridScheduleByTime(request);
            log.info("{} listValidCutWorkGridScheduleByTime resp:{}",request.getRequestId(),JsonHelper.toJSONString(rs));
            return rs;
        } catch (Exception e) {
            log.error("{} listValidCutWorkGridScheduleByTime error:{}", request.getRequestId(),JsonHelper.toJSONString(request),e);
        }
        return Result.fail("获取班次数据失败！");
    }
}
