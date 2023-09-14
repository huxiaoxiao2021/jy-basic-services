package com.jdl.basic.api.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.*;

import java.util.List;

public interface UserWorkGridJsfService {

    Result<List<UserWorkGrid>> batchQueryUserWorkGridByGridKey(UserWorkGridBatchRequest request);

    Result<Boolean> batchInsert(UserWorkGridBatchRequest request);

    Result<Boolean> batchDelete(UserWorkGridBatchRequest request);

    Result<Boolean> batchUpdateUserWorkGrid(UserWorkGridBatchUpdateRequest request);

    Result<List<UserWorkGrid>> queryByUserIds(UserWorkGridBatchRequest request);

    Result<List<JyUserDto>> getWorkGridDistributedStaff(UserWorkGridRequest request);

    Result<List<UserWorkGrid>> batchQueryDeletedUserWorkGrid(UserWorkGridBatchRequest request);

    /**
     * 批量调出人员网格信息
     *
     * @param request
     * @return
     */
    Result<Boolean> batchTransferUserWorkGrid(UserWorkGridBatchUpdateRequest request);
}
