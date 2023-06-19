package com.jdl.basic.api.service.schedule;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.schedule.WorkGridSchedule;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleBatchRequest;

import java.util.List;

public interface WorkGridScheduleJsfService {

    Result<List<WorkGridSchedule>> queryRecordDetail(WorkGridScheduleBatchRequest request);
}
