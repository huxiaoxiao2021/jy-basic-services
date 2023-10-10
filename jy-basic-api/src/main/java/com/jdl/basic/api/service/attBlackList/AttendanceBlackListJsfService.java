package com.jdl.basic.api.service.attBlackList;


import com.jdl.basic.api.domain.attBlackList.AttendanceBlackList;
import com.jdl.basic.api.domain.attBlackList.AttendanceBlackListCondition;
import com.jdl.basic.api.domain.ncWhiteList.NCWhiteListQuery;
import com.jdl.basic.api.dto.ncWhiteList.NCWhiteListDTO;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

public interface AttendanceBlackListJsfService {
    Result<Integer> add(AttendanceBlackList attendanceBlackList);

    Result<Integer> modify(AttendanceBlackList attendanceBlackList);

    Result<Integer> remove(int id);

    Result<PageDto<AttendanceBlackList>> queryByCondition(AttendanceBlackListCondition query);

    Result<AttendanceBlackList> queryByUerCode(String userCode);

    Result<Integer> countByCondition(AttendanceBlackListCondition query);

    Result<Integer> checkCountCondition(AttendanceBlackListCondition query);

}
