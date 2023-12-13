package com.jdl.basic.provider.core.dao.user;
import com.jdl.basic.api.domain.attBlackList.AttendanceBlackList;
import com.jdl.basic.api.domain.attBlackList.AttendanceBlackListCondition;
import com.jdl.basic.api.domain.user.JyThirdpartyUser;
import com.jdl.basic.api.domain.user.JyTpUserScheduleQueryDto;

import java.util.List;

public interface JyThirdpartyUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(JyThirdpartyUser record);

    int insertSelective(JyThirdpartyUser record);

    JyThirdpartyUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(JyThirdpartyUser record);

    int updateByPrimaryKey(JyThirdpartyUser record);

    int batchInsert(List<JyThirdpartyUser> jyThirdpartyUserList);

    List<JyThirdpartyUser> queryJyThirdpartyUser(JyTpUserScheduleQueryDto jyTpUserScheduleQueryDto);
    List<JyThirdpartyUser> queryJyThirdpartyUserByCondition(JyThirdpartyUser query);

    int updateJyThirdpartyUserYn(JyThirdpartyUser query);
}