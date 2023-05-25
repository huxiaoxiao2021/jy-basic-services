package com.jdl.basic.provider.core.dao.user;

import com.jdl.basic.api.domain.user.UserWorkGrid;

public interface UserWorkGridDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserWorkGrid record);

    int insertSelective(UserWorkGrid record);

    UserWorkGrid selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserWorkGrid record);

    int updateByPrimaryKey(UserWorkGrid record);
}