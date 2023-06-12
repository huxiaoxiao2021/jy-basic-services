package com.jdl.basic.provider.core.dao.user;

import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridRequest;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserWorkGridDao {
    List<UserWorkGrid> queryPageList(UserWorkGridRequest request);

    List<UserWorkGrid> queryRecordDetail(String workGridKey);

    List<UserWorkGrid> queryDifference(UserWorkGridRequest request);

    long queryTotal(UserWorkGridRequest request);

    int batchInsert(List<UserWorkGrid> userWorkGrids);

    int batchDelete(@Param("list") List<UserWorkGrid> userWorkGrids, @Param("workGridKey") String workGridKey,
                    @Param("updateTime")Date updateTime, @Param("updateUserErp") String updateUserErp, @Param("updateUserName") String updateUserName);

    List<UserWorkGrid> queryByUserIds(List<Long> userIds);

    List<UserWorkGrid> queryByWorkGridKey(UserWorkGridRequest request);
}