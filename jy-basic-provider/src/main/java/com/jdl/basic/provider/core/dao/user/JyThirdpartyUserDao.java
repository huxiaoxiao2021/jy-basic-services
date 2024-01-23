package com.jdl.basic.provider.core.dao.user;
import com.jdl.basic.api.domain.attBlackList.AttendanceBlackList;
import com.jdl.basic.api.domain.attBlackList.AttendanceBlackListCondition;
import com.jdl.basic.api.domain.user.*;

import java.util.List;

public interface JyThirdpartyUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(JyThirdpartyUser record);

    int insertSelective(JyThirdpartyUser record);

    JyThirdpartyUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(JyThirdpartyUser record);

    int updateByPrimaryKey(JyThirdpartyUser record);

    int batchInsert(List<JyThirdpartyUser> jyThirdpartyUserList);

    List<JyThirdpartyUser> queryJyThirdpartyUserByCondition(JyThirdpartyUser query);

    List<JyThirdpartyUser> queryForRepeat(JyThirdpartyUser query);

    int updateJyThirdpartyUserYn(JyThirdpartyUser query);

    Long countTpUserByTaskDetail(JyThirdpartyUser jyThirdpartyUser);

    List<ReserveTaskDetailAgg> countTpUserGroupByNature(ReserveTaskDetailAggQuery query);

    List<JyThirdpartyUser> queryJyThirdpartyUserByDetailBizId(String taskDetailBizId);

    List<JyThirdpartyUser> queryJyThirdpartyUserByTaskBizId(String taskBizId);

    JyThirdpartyUser queryTpUserByUserCode(JyThirdpartyUser jyThirdpartyUser);

    List<JyThirdpartyUser> queryJyThirdpartyUserUnderDacuTask(JyTpUserScheduleQueryDto jyTpUserScheduleQueryDto);

    List<JyThirdpartyUser> queryJyThirdpartyUserUnderNormalTask(JyTpUserScheduleQueryDto jyTpUserScheduleQueryDto);

    JyThirdpartyUser queryTpUserByUserCodeUnderDacuTask(JyTpUserScheduleQueryDto jyTpUserScheduleQueryDto);

    JyThirdpartyUser queryTpUserByUserCodeUnderNormalTask(JyTpUserScheduleQueryDto jyTpUserScheduleQueryDto);

    JyThirdpartyUser getUserByUserCode(JyUserQueryDto queryDto);
}