package com.jdl.basic.api.service.workStation;

import com.jdl.basic.api.domain.workStation.SiteAttendPlan;
import com.jdl.basic.api.domain.workStation.SiteAttendPlanQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface SiteAttendPlanJsfService {
    Result<Boolean> importDatas(List<SiteAttendPlan> dataList);

    /**
     * 逻辑分页查询--查询页面显示数据条数
     * @param query
     * @return
     */
    Result<List<SiteAttendPlan>> queryPageList(SiteAttendPlanQuery query);

    /**
     * 逻辑分页查询--查询细节
     * @param plan
     * @return
     */
    Result<List<SiteAttendPlan>> queryPageDetail(SiteAttendPlan plan);

    Result<Boolean> confirmRecords(List<SiteAttendPlan> dataList);

    /**
     * 分页查询总数据条数
     * @param query
     * @return
     */
    Result<Long> queryTotalCount(SiteAttendPlanQuery query);
}
