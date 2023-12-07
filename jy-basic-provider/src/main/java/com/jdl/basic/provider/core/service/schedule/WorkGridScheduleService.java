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
}
