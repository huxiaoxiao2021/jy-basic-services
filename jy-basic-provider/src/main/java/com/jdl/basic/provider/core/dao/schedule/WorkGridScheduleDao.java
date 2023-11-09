package com.jdl.basic.provider.core.dao.schedule;

import com.jdl.basic.api.domain.schedule.*;

import java.util.List;

public interface WorkGridScheduleDao {
    int deleteByPrimaryKey(Long id);

    int insert(WorkGridSchedule record);

    int insertSelective(WorkGridSchedule record);

    WorkGridSchedule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WorkGridSchedule record);

    int updateByPrimaryKey(WorkGridSchedule record);

    List<WorkGridSchedule> batchQueryByWorkGridKey(WorkGridScheduleBatchRequest request);

    boolean batchDeleteByWorkGridKey(WorkGridScheduleBatchRequest request);

    boolean batchInsert(WorkGridScheduleBatchRequest request);

    List<WorkGridSchedule> querySiteScheduleByCondition(WorkGridScheduleRequest request);

    WorkGridSchedule queryWorkGridScheduleByKey(WorkGridScheduleRequest request);

    List<WorkGridSchedule> listWorkGridScheduleByKeys(BatchWorkGridScheduleQueryDto dto);

    WorkGridSchedule queryWorkGridScheduleByName(WorkGridScheduleRequest request);

    boolean cleanWorkGridScheduleOldTime(BatchCleanOldTimeRequest request);

    List<WorkGridSchedule> queryTodayDeletedSiteSchedule(WorkGridScheduleRequest request);
}
