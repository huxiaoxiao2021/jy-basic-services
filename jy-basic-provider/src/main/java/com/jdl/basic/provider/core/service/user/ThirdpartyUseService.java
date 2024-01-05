package com.jdl.basic.provider.core.service.user;

import com.jdl.basic.api.domain.user.JyThirdpartyUser;
import com.jdl.basic.api.domain.user.JyTpUserScheduleQueryDto;
import com.jdl.basic.api.domain.user.ReserveTaskDetailAgg;
import com.jdl.basic.api.domain.user.ReserveTaskDetailAggQuery;

import java.util.List;

public interface ThirdpartyUseService {
    int batchInsert(List<JyThirdpartyUser> jyThirdpartyUserList);

    List<JyThirdpartyUser> queryJyThirdpartyUserByDetailBizId(String taskDetailBizId);

    List<JyThirdpartyUser> queryJyThirdpartyUserByTaskBizId(String taskBizId);

    int add(JyThirdpartyUser jyThirdpartyUser);

    List<JyThirdpartyUser> queryJyThirdpartyUserByCondition(JyThirdpartyUser query);

    int updateJyThirdpartyUserYn(JyThirdpartyUser query);

    Long countTpUserByTaskDetail(JyThirdpartyUser jyThirdpartyUser);

    List<ReserveTaskDetailAgg> countTpUserGroupByNature(ReserveTaskDetailAggQuery query);

    JyThirdpartyUser queryTpUserByUserCode(JyThirdpartyUser jyThirdpartyUser);

    JyThirdpartyUser queryTpUserReserveInfo(JyTpUserScheduleQueryDto jyTpUserScheduleQueryDto);

}
