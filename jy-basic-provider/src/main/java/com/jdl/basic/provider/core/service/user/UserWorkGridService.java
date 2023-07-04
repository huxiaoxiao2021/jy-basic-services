package com.jdl.basic.provider.core.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridBatchRequest;
import com.jdl.basic.api.domain.user.UserWorkGridRequest;

import java.util.List;

public interface UserWorkGridService {

    /**
     * 根据网格主键批量查询网格分配记录
     * @param request
     * @return
     */
    Result<List<UserWorkGrid>> batchQueryUserWorkGridByGridKey(UserWorkGridBatchRequest request);

    /**
     * 批量插入
     * @param request
     * @return
     */
    Result<Boolean> batchInsert(UserWorkGridBatchRequest request);

    /**
     * 批量删除
     * @param request
     * @return
     */
    Result<Boolean> batchDelete(UserWorkGridBatchRequest request);

    /**
     * 根据用户id批量查询
     * @param request
     * @return
     */
    Result<List<UserWorkGrid>> queryByUserIds(UserWorkGridBatchRequest request);

    /**
     * 查询网格下已分配的用户
     * @param request
     * @return
     */
    Result<List<JyUser>> getWorkGridDistributedStaff(UserWorkGridRequest request);

    /**
     * 批量查询给定时间至今某网格已删除的网格分配记录
     * @param request
     * @return
     */
    Result<List<UserWorkGrid>> batchQueryDeletedUserWorkGrid(UserWorkGridBatchRequest request);
}
