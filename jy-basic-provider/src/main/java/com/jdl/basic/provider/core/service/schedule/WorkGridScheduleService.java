package com.jdl.basic.provider.core.service.schedule;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.schedule.WorkGridSchedule;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleBatchRequest;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleRequest;

import java.util.List;

public interface WorkGridScheduleService {

    Result<List<WorkGridSchedule>> batchQueryByWorkGridKey(WorkGridScheduleBatchRequest request);

    Result<Boolean> batchDeleteByWorkGridKey(WorkGridScheduleBatchRequest request);

    Result<Boolean> batchInsert(WorkGridScheduleBatchRequest request);

    Result<List<WorkGridSchedule>> querySiteScheduleByCondition(WorkGridScheduleRequest request);
}
