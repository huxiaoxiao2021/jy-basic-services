package com.jdl.basic.provider.core.service.attBlackList;

import com.jdl.basic.api.domain.attBlackList.AttendanceBlackList;
import com.jdl.basic.api.domain.attBlackList.AttendanceBlackListCondition;

import java.util.List;

public interface AttendanceBlackListService {
    public int add(AttendanceBlackList attendanceBlackList);

    public int modify(AttendanceBlackList attendanceBlackList);

    public int removeById(int id);

    public List<AttendanceBlackList> queryByCondition(AttendanceBlackListCondition query);

    public AttendanceBlackList queryByUserCode(String userCode);

    public int countByCondition(AttendanceBlackListCondition query);

    public int checkCountCondition(AttendanceBlackListCondition query);

}
