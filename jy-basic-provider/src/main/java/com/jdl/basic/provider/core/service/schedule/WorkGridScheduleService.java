package com.jdl.basic.provider.core.service.schedule;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.schedule.WorkGridSchedule;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleBatchRequest;

import java.util.List;

public interface WorkGridScheduleService {
    Result<List<WorkGridSchedule>> queryRecordDetail(WorkGridScheduleBatchRequest request);
}
