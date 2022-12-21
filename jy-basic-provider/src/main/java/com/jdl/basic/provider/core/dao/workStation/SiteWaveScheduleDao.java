package com.jdl.basic.provider.core.dao.workStation;

import com.jdl.basic.api.domain.workStation.SiteWaveSchedule;
import com.jdl.basic.api.domain.workStation.SiteWaveScheduleQuery;

import java.util.List;

public interface SiteWaveScheduleDao {

    /**
     * 插入一条出勤计划--15条记录
     * @param siteWaveSchedule
     * @return
     */
    int batchInsert(List<SiteWaveSchedule> siteWaveSchedule);

    SiteWaveSchedule queryOldDataByBusinessKey(SiteWaveSchedule siteWaveSchedule);

    int deleteOldDataByBusinessKey(SiteWaveSchedule siteWaveSchedule);

    List<SiteWaveSchedule> queryPageList(SiteWaveScheduleQuery query);

    List<SiteWaveSchedule> queryPageDetail(SiteWaveSchedule siteWaveSchedule);

    List<Long> queryTotalCount(SiteWaveScheduleQuery query);

}
