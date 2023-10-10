package com.jdl.basic.provider.core.dao.attBlackList;


import com.jdl.basic.api.domain.attBlackList.AttendanceBlackList;
import com.jdl.basic.api.domain.attBlackList.AttendanceBlackListCondition;

import java.util.List;

public interface AttendanceBlackListDao {
    int insert(AttendanceBlackList attendanceBlackList);

    int updateById(AttendanceBlackList attendanceBlackList);

    int deleteById(int id);

    List<AttendanceBlackList> queryByCondition(AttendanceBlackListCondition query);

    AttendanceBlackList queryByUserCode(String userCode);

    Long countByCondition(AttendanceBlackListCondition query);

    Long checkCountCondition(AttendanceBlackListCondition query);
}
