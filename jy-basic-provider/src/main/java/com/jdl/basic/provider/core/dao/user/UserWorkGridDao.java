package com.jdl.basic.provider.core.dao.user;

import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridBatchRequest;
import com.jdl.basic.api.domain.user.UserWorkGridRequest;

import java.util.List;

public interface UserWorkGridDao {
    List<UserWorkGrid> queryRecordDetail(String workGridKey);

    List<UserWorkGrid> queryDifference(UserWorkGridRequest request);

    int batchInsert(UserWorkGridBatchRequest request);

    int batchDelete(UserWorkGridBatchRequest request);

    List<UserWorkGrid> queryByUserIdsWithCondition(UserWorkGridBatchRequest request);

    List<UserWorkGrid> queryByCondition(UserWorkGridRequest request);
}