package com.jdl.basic.api.service.workStation;

import com.jdl.basic.api.domain.workStation.SiteAttendPlanQuery;
import com.jdl.basic.api.domain.workStation.SiteAttendPlanVo;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;


import java.util.List;

public interface SiteAttendPlanJsfService {
    Result<Boolean> importDatas(List<SiteAttendPlanVo> dataList);

    Result<PageDto<SiteAttendPlanVo>> queryPageList(SiteAttendPlanQuery query);

    Result<Boolean> confirmOneRecord(SiteAttendPlanVo vo);

    Result<Boolean> confirmRecords(List<SiteAttendPlanVo> dataList);

    Result<Long> queryTotalCount(SiteAttendPlanQuery query);
}
