package com.jdl.basic.provider.core.dao.user;

import com.jdl.basic.api.domain.user.JyUser;

import java.util.List;

public interface JyUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(JyUser record);

    int insertSelective(JyUser record);

    JyUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(JyUser record);

    int updateByPrimaryKey(JyUser record);

    JyUser queryUserInfo(JyUser condition);

    List<JyUser> searchUserBySiteCode(Integer siteCode);

    List<JyUser> queryByUserIds(List<Long> ids);
}
