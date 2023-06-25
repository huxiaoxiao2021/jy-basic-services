package com.jdl.basic.api.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridBatchRequest;
import com.jdl.basic.api.domain.user.UserWorkGridRequest;

import java.util.List;

public interface UserWorkGridJsfService {

    Result<List<UserWorkGrid>> batchQueryUserWorkGridByGridKey(UserWorkGridBatchRequest request);

    Result<Boolean> batchInsert(UserWorkGridBatchRequest request);

    Result<Boolean> batchDelete(UserWorkGridBatchRequest request);

    Result<List<UserWorkGrid>> queryByUserIds(UserWorkGridBatchRequest request);

    Result<List<JyUser>> getWorkGridDistributedStaff(UserWorkGridRequest request);

    Result<List<UserWorkGrid>> queryDeletedUserWorkGrid(UserWorkGridRequest request);
}
