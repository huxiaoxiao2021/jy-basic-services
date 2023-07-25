package com.jdl.basic.provider.core.dao.user;

import com.jdl.basic.api.domain.user.UserWorkGridAggs;

public interface UserWorkGridAggsDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserWorkGridAggs record);

    int insertSelective(UserWorkGridAggs record);

    UserWorkGridAggs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserWorkGridAggs record);

    int updateByPrimaryKey(UserWorkGridAggs record);
}