package com.jdl.basic.provider.core.provider.workStation;

import com.jdl.basic.api.domain.workStation.WorkStationFloorGridQuery;
import com.jdl.basic.api.domain.workStation.WorkStationFloorGridVo;
import com.jdl.basic.api.service.workStation.WorkAbnormalGridBindingJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.workStation.WorkAbnormalGridBindingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liwenji
 * @description TODO
 * @date 2022-08-10 18:49
 */
@Slf4j
@Service
public class WorkAbnormalGridBindingJsfServiceImpl implements WorkAbnormalGridBindingJsfService {

    @Autowired
    @Qualifier("workAbnormalGridBindingService")
    private WorkAbnormalGridBindingService workAbnormalGridBindingService;

    @Override
    public Result<PageDto<WorkStationFloorGridVo>> queryListDistinct(WorkStationFloorGridQuery query) {
        return workAbnormalGridBindingService.queryListDistinct(query);
    }

    @Override
    public Result<List<WorkStationFloorGridVo>> queryListBySite(WorkStationFloorGridQuery query) {
        return workAbnormalGridBindingService.queryListBySite(query);
    }
}
