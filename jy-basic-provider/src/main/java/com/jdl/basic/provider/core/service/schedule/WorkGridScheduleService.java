package com.jdl.basic.provider.core.service.schedule;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.schedule.*;

import java.util.List;

public interface WorkGridScheduleService {

    Result<List<WorkGridSchedule>> batchQueryByWorkGridKey(WorkGridScheduleBatchRequest request);

    Result<Boolean> batchDeleteByWorkGridKey(WorkGridScheduleBatchRequest request);

    Result<Boolean> batchInsert(WorkGridScheduleBatchRequest request);

    Result<Boolean> batchUpdateWorkGridSchedule(WorkGridScheduleBatchUpdateRequest request);

    Result<List<WorkGridSchedule>> querySiteScheduleByCondition(WorkGridScheduleRequest request);

    WorkGridSchedule queryWorkGridScheduleByKey(WorkGridScheduleRequest request);

    List<WorkGridSchedule> listWorkGridScheduleByKeys(BatchWorkGridScheduleQueryDto dto);

    WorkGridSchedule queryWorkGridScheduleByName(WorkGridScheduleRequest request);

    Result<Boolean> cleanWorkGridScheduleOldTime(BatchCleanOldTimeRequest request);

    Result<List<WorkGridSchedule>> queryTodayDeletedSiteSchedule(WorkGridScheduleRequest request);

    /**
     * 根据班次key删除班次并设置班次失效时间
     * @param request
     * @return
     */
    Result<Boolean> batchDeleteByScheduleKey(WorkGridScheduleBatchRequest request);

    /**
     * 查询给定时间内的有效班次
     * @param request
     * @return
     */
    Result<List<ScheduleValidTimeDto>> listValidWorkGridScheduleByTime(ValidWorkGridScheduleRequest request);
}
