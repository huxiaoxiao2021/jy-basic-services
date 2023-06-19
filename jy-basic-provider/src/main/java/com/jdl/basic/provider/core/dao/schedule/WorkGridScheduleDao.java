package com.jdl.basic.provider.core.dao.schedule;

import com.jdl.basic.api.domain.schedule.WorkGridSchedule;
import com.jdl.basic.api.domain.schedule.WorkGridScheduleBatchRequest;

import java.util.List;

public interface WorkGridScheduleDao {
    int deleteByPrimaryKey(Long id);

    int insert(WorkGridSchedule record);

    int insertSelective(WorkGridSchedule record);

    WorkGridSchedule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WorkGridSchedule record);

    int updateByPrimaryKey(WorkGridSchedule record);

    List<WorkGridSchedule> queryRecordDetail(WorkGridScheduleBatchRequest request);
}