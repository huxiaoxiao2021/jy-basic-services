package com.jdl.basic.provider.core.service.workStation;

import com.jdl.basic.api.domain.workStation.SiteAttendPlan;
import com.jdl.basic.api.domain.workStation.SiteAttendPlanQuery;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface SiteAttendPlanService {

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

    /**
     * 出勤计划确认
     * @param dataList
     * @return
     */
    Result<Boolean> confirmRecords(List<SiteAttendPlan> dataList);

    Result<Long> queryTotalCount(SiteAttendPlanQuery query);

}
