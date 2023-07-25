package com.jdl.basic.api.service.schedule;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.schedule.BatchWorkGridScheduleQueryDto;
import com.jdl.basic.api.domain.schedule.WorkGridSchedule;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleBatchRequest;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleRequest;

import java.util.List;

public interface WorkGridScheduleJsfService {

    Result<List<WorkGridSchedule>> batchQueryByWorkGridKey(WorkGridScheduleBatchRequest request);

    Result<Boolean> batchDeleteByWorkGridKey(WorkGridScheduleBatchRequest request);

    Result<Boolean> batchInsert(WorkGridScheduleBatchRequest request);

    Result<List<WorkGridSchedule>> querySiteScheduleByCondition(WorkGridScheduleRequest request);

    Result<WorkGridSchedule> queryWorkGridScheduleByKey(WorkGridScheduleRequest request);

    Result<List<WorkGridSchedule>> listWorkGridScheduleByKeys(BatchWorkGridScheduleQueryDto dto);
}
