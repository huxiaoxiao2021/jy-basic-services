package com.jdl.basic.api.service.schedule;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.schedule.*;

import java.util.List;

public interface WorkGridScheduleJsfService {

    Result<List<WorkGridSchedule>> batchQueryByWorkGridKey(WorkGridScheduleBatchRequest request);

    Result<Boolean> batchDeleteByWorkGridKey(WorkGridScheduleBatchRequest request);

    Result<Boolean> batchInsert(WorkGridScheduleBatchRequest request);

    Result<Boolean> batchUpdateWorkGridSchedule(WorkGridScheduleBatchUpdateRequest request);

    Result<List<WorkGridSchedule>> querySiteScheduleByCondition(WorkGridScheduleRequest request);

    Result<WorkGridSchedule> queryWorkGridScheduleByKey(WorkGridScheduleRequest request);

    Result<List<WorkGridSchedule>> listWorkGridScheduleByKeys(BatchWorkGridScheduleQueryDto dto);

    Result<Boolean> cleanWorkGridScheduleOldTime(BatchCleanOldTimeRequest request);

}
