package com.jdl.basic.provider.core.service.user;

import com.jdl.basic.api.domain.user.*;

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

    /**
     * 根据身份证查询三方人员
     * @param queryDto
     * @return
     */
    JyThirdpartyUser getUserByIdCarNum(JyUserQueryDto queryDto);

}
