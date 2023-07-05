package com.jdl.basic.provider.core.dao.user;

import com.jdl.basic.api.domain.user.RemoveUserDto;
import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridBatchRequest;
import com.jdl.basic.api.domain.user.UserWorkGridRequest;

import java.util.List;

public interface UserWorkGridDao {

    /**
     * 根据网格主键查询网格分配记录
     * @param request
     * @return
     */
    List<UserWorkGrid> queryByWorkGridKey(UserWorkGridRequest request);

    /**
     * 批量插入
     * @param request
     * @return
     */
    int batchInsert(UserWorkGridBatchRequest request);

    /**
     * 批量查询
     * @param request
     * @return
     */
    int batchDelete(UserWorkGridBatchRequest request);

    /**
     * 根据用户id批量查询网格分配记录
     * @param request
     * @return
     */
    List<UserWorkGrid> queryByUserIds(UserWorkGridBatchRequest request);

    /**
     * 根据网格主键批量查询网格分配记录
     * @param request
     * @return
     */
    List<UserWorkGrid> batchQueryUserWorkGridByGridKey(UserWorkGridBatchRequest request);

    /**
     * 批量查询从updateTime至今已删除的网格分配记录
     * @param request
     * @return
     */
    List<UserWorkGrid> batchQueryDeletedUserWorkGrid(UserWorkGridBatchRequest request);

    int updateAfterInsertOrDelete(UserWorkGridRequest request);

    int removeFromGridByUserId(RemoveUserDto removeUserDto);
}
