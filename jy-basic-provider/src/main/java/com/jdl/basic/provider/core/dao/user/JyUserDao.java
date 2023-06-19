package com.jdl.basic.provider.core.dao.user;

import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.JyUserBatchRequest;
import com.jdl.basic.api.domain.user.JyUserQueryCondition;

import com.jdl.basic.api.domain.user.UnDistributedUserQueryDto;
import java.util.List;

public interface JyUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(JyUser record);

    int insertSelective(JyUser record);

    JyUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(JyUser record);

    int updateByPrimaryKey(JyUser record);

    JyUser queryUserInfo(JyUser condition);

    List<JyUser> searchUserByCondition(JyUserQueryCondition condition);

    List<JyUser> queryByUserIds(JyUserBatchRequest request);

    int batchUpdateByUserIds(JyUserBatchRequest request);

    Integer queryUndistributedCountBySiteCode(JyUserQueryCondition condition);

    List<JyUser> queryDifference(JyUserQueryCondition condition);

    List<JyUser> queryUnDistributedUserList(UnDistributedUserQueryDto dto);
}
