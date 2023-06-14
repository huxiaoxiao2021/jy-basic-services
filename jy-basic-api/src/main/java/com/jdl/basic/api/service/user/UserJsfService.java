package com.jdl.basic.api.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.JyUserBatchRequest;
import com.jdl.basic.api.domain.user.JyUserQueryCondition;

import java.util.List;

public interface UserJsfService {
    Result<List<JyUser>> searchUserByCondition(JyUserQueryCondition condition);

    Result<List<JyUser>> queryByUserIds(JyUserBatchRequest request);

    Result<Integer> queryUndistributedCountBySiteCode(JyUserQueryCondition condition);

    Result<List<JyUser>> queryDifference(JyUserQueryCondition condition);
}
