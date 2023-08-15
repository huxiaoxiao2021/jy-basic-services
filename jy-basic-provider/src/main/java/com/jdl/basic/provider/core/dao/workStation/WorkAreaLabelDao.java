package com.jdl.basic.provider.core.dao.workStation;

import com.jdl.basic.api.domain.workStation.WorkArea;
import com.jdl.basic.api.domain.workStation.WorkAreaLabel;
import java.util.List;

public interface WorkAreaLabelDao {

  int deleteByPrimaryKey(Long id);

  int insert(WorkAreaLabel record);

  int insertSelective(WorkAreaLabel record);

  WorkAreaLabel selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(WorkAreaLabel record);

  int updateByPrimaryKey(WorkAreaLabel record);

  List<WorkAreaLabel> listLabelByAreaCode(String areaCode);

  int deleteByAreaCode(WorkArea workArea);

  int batchInsert(List<WorkAreaLabel> workAreaLabels);
}
