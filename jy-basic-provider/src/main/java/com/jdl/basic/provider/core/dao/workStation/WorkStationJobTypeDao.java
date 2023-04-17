package com.jdl.basic.provider.core.dao.workStation;


import com.jdl.basic.provider.core.po.WorkStationJobTypePO;

import java.util.List;

public interface WorkStationJobTypeDao {

    int insert(WorkStationJobTypePO record);

    int insertSelective(WorkStationJobTypePO record);

    int batchInsert(List<WorkStationJobTypePO> record);

    WorkStationJobTypePO selectByPrimaryKey(Long id);

    List<WorkStationJobTypePO> selectByBusinessKey(String businessKey);

    int updateByPrimaryKeySelective(WorkStationJobTypePO record);

    int updateByPrimaryKey(WorkStationJobTypePO record);

    int deleteByRefBusinessKey(String refBusinessKey);

    List<WorkStationJobTypePO> selectAll();
}