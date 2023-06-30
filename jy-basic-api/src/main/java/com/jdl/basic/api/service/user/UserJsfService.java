package com.jdl.basic.api.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.JyUserBatchRequest;
import com.jdl.basic.api.domain.user.JyUserQueryDto;
import com.jdl.basic.api.domain.user.UnDistributedUserQueryDto;

import java.util.List;

public interface UserJsfService {
    Result<List<JyUser>> searchUserBySiteCode(JyUserQueryDto dto);

    Result<List<JyUser>> queryByUserIds(JyUserBatchRequest request);

    Result<Integer> queryUndistributedCountBySiteCode(JyUserQueryDto dto);

    Result<List<JyUser>> queryDifference(JyUserQueryDto dto);

    Result<List<JyUser>> batchQueryQuitUserByUserId(JyUserBatchRequest request);

    /**
     * 查询未分配到网格的人员列表
     * @param unDistributedUserQueryDto
     * @return
     */
    Result<List<JyUser>> queryUnDistributedUserList(UnDistributedUserQueryDto unDistributedUserQueryDto);
    /**
     * 查询人员岗位下列表
     * @param condition
     * @return
     */
    Result<List<JyUser>> queryUserListBySiteAndPosition(JyUserQueryDto dto);

    Result<List<JyUser>> queryNatureUndistributedUsers(JyUserQueryDto dto);

}
