package com.jdl.basic.provider.core.service.workStation;

import com.jdl.basic.api.domain.workStation.SiteWaveScheduleQuery;
import com.jdl.basic.api.domain.workStation.SiteWaveScheduleVo;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface SiteWaveScheduleService {

    Result<Boolean> importDatas(List<SiteWaveScheduleVo> dataList);

    Result<PageDto<SiteWaveScheduleVo>> queryPageList(SiteWaveScheduleQuery query);

    Result<Long> queryTotalCount(SiteWaveScheduleQuery query);
}
