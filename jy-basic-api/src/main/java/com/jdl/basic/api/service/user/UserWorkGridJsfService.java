package com.jdl.basic.api.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridRequest;

import java.util.List;

public interface UserWorkGridJsfService {
    Result<List<UserWorkGrid>> queryPageList(UserWorkGridRequest request);

    Result<List<UserWorkGrid>> queryRecordDetail(String workGridKey);

    Result<List<UserWorkGrid>> queryDifference(UserWorkGridRequest request);

    Result<Long> queryTotal(UserWorkGridRequest request);

    Result<Boolean> batchInsert(List<UserWorkGrid> userWorkGrids);

    Result<Boolean> batchDelete(List<UserWorkGrid> userWorkGrids);

    Result<List<UserWorkGrid>> queryByUserIds(List<Long> userIds);

    Result<List<JyUser>> getWorkGridDistributedStaff(UserWorkGridRequest request);
}
