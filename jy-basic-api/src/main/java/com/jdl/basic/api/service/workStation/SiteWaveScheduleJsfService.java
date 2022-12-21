package com.jdl.basic.api.service.workStation;

import com.jdl.basic.api.domain.workStation.SiteWaveSchedule;
import com.jdl.basic.api.domain.workStation.SiteWaveScheduleQuery;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface SiteWaveScheduleJsfService {

    Result<Boolean> importDatas(List<SiteWaveSchedule> dataList);

    Result<List<SiteWaveSchedule>> queryPageList(SiteWaveScheduleQuery query);

    Result<List<SiteWaveSchedule>> queryPageDetail(SiteWaveSchedule schedule);

    Result<Long> queryTotalCount(SiteWaveScheduleQuery query);
}
