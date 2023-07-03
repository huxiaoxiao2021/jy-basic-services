package com.jdl.basic.provider.core.dao.workStation;

import com.jdl.basic.api.domain.workStation.SiteAttendPlan;
import com.jdl.basic.api.domain.workStation.SiteAttendPlanQuery;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SiteAttendPlanDao {

    int batchInsert(List<SiteAttendPlan> insertData);

    SiteAttendPlan queryOldDataByBusinessKey(SiteAttendPlan siteAttendPlan);
    
    int deleteOldDataByBusinessKey(SiteAttendPlan siteAttendPlan);
    
    List<SiteAttendPlan> queryPageList(@Param("query") SiteAttendPlanQuery query, @Param("dates") List<Date> dates);
    
    List<SiteAttendPlan> queryPageDetail(SiteAttendPlan siteAttendPlan);
    
    List<Long> queryTotalCount(@Param("query") SiteAttendPlanQuery query, @Param("dates") List<Date> dates);
    
    Integer confirmOneRecord(SiteAttendPlan vo);

    /**
     * 刷数-分页查询
     * @param startId
     * @return
     */
    List<SiteAttendPlan> brushQueryAllByPage(Integer startId);

    /**
     * 刷数-批量更新
     * @param list
     * @return
     */
    Integer brushUpdateById(List<SiteAttendPlan> list);
}
