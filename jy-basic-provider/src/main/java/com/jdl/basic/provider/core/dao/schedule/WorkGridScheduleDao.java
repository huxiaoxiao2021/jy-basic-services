package com.jdl.basic.provider.core.dao.schedule;

import com.jdl.basic.api.domain.schedule.BatchWorkGridScheduleQueryDto;
import com.jdl.basic.api.domain.schedule.WorkGridSchedule;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleBatchRequest;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleRequest;

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
}
