package com.jdl.basic.provider.core.provider.schedule;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.schedule.WorkGridSchedule;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleBatchRequest;
import com.jdl.basic.api.service.schedule.WorkGridScheduleJsfService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkGridScheduleJsfServiceImpl implements WorkGridScheduleJsfService {
    @Override
    public Result<List<WorkGridSchedule>> queryRecordDetail(WorkGridScheduleBatchRequest request) {
        return null;
    }
}
