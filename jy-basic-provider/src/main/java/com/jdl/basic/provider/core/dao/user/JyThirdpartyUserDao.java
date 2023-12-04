package com.jdl.basic.provider.core.dao.user;
import com.jdl.basic.api.domain.user.JyThirdpartyUser;

import java.util.List;

public interface JyThirdpartyUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(JyThirdpartyUser record);

    int insertSelective(JyThirdpartyUser record);

    JyThirdpartyUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(JyThirdpartyUser record);

    int updateByPrimaryKey(JyThirdpartyUser record);

    int batchInsert(List<JyThirdpartyUser> jyThirdpartyUserList);
}