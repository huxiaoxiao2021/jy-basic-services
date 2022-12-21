package com.jdl.basic.provider.core.service.workStation;

import com.jdl.basic.api.domain.workStation.SiteWaveSchedule;
import com.jdl.basic.api.domain.workStation.SiteWaveScheduleQuery;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface SiteWaveScheduleService {

    Result<Boolean> importDatas(List<SiteWaveSchedule> dataList);

    /**
     * 逻辑分页查询
     * @param query
     * @return
     */
    Result<List<SiteWaveSchedule>> queryPageList(SiteWaveScheduleQuery query);

    /**
     * 进行逻辑分页查询后，查询班次时间细节用于转化成一条记录用于前端展示
     * @param schedule
     * @return
     */
    Result<List<SiteWaveSchedule>> queryPageDetail(SiteWaveSchedule schedule);

    Result<Long> queryTotalCount(SiteWaveScheduleQuery query);
}
