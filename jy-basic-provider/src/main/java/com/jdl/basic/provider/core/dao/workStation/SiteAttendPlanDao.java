package com.jdl.basic.provider.core.dao.workStation;

import com.jdl.basic.api.domain.workStation.SiteAttendPlan;
import com.jdl.basic.api.domain.workStation.SiteAttendPlanQuery;
import com.jdl.basic.api.domain.workStation.SiteAttendPlanVo;

import java.util.List;

public interface SiteAttendPlanDao {

    int insert(SiteAttendPlan insertData);

    SiteAttendPlan queryOldDataByBusinessKey(SiteAttendPlan siteAttendPlan);

    int deleteOldDataByBusinessKey(SiteAttendPlan siteAttendPlan);

    List<SiteAttendPlan> queryPageList(SiteAttendPlanQuery query);

    List<SiteAttendPlan> queryPageDetail(SiteAttendPlan siteAttendPlan);

    List<Long> queryTotalCount(SiteAttendPlanQuery query);

    Integer confirmOneRecord(SiteAttendPlanVo vo);
}
