package com.jdl.basic.provider.core.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridBatchRequest;
import com.jdl.basic.api.domain.user.UserWorkGridRequest;

import java.util.List;

public interface UserWorkGridService {

    Result<List<UserWorkGrid>> queryByCondition(UserWorkGridRequest request);

    Result<List<UserWorkGrid>> queryDifference(UserWorkGridRequest request);

    Result<Boolean> batchInsert(UserWorkGridBatchRequest request);

    Result<Boolean> batchDelete(UserWorkGridBatchRequest request);

    Result<List<UserWorkGrid>> queryByUserIdsWithCondition(UserWorkGridBatchRequest request);

    Result<List<JyUser>> getWorkGridDistributedStaff(UserWorkGridRequest request);
}
