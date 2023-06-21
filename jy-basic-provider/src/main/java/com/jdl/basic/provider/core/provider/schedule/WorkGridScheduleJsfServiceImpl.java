package com.jdl.basic.provider.core.provider.schedule;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.schedule.WorkGridSchedule;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleBatchRequest;
import com.jdl.basic.api.service.schedule.WorkGridScheduleJsfService;
import com.jdl.basic.provider.core.service.schedule.WorkGridScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("workGridScheduleJsfServiceImpl")
public class WorkGridScheduleJsfServiceImpl implements WorkGridScheduleJsfService {

    @Autowired
    private WorkGridScheduleService workGridScheduleService;

    @Override
    public Result<List<WorkGridSchedule>> batchQueryByWorkGridKey(WorkGridScheduleBatchRequest request) {
        return workGridScheduleService.batchQueryByWorkGridKey(request);
    }

    @Override
    public Result<Boolean> batchDeleteByWorkGridKey(WorkGridScheduleBatchRequest request) {
        return workGridScheduleService.batchDeleteByWorkGridKey(request);
    }

    @Override
    public Result<Boolean> batchInsert(WorkGridScheduleBatchRequest request) {
        return workGridScheduleService.batchInsert(request);
    }
}
