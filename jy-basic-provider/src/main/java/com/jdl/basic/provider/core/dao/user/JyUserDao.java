package com.jdl.basic.provider.core.dao.user;

import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.JyUserBatchRequest;
import com.jdl.basic.api.domain.user.JyUserQueryCondition;

import java.util.List;

public interface JyUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(JyUser record);

    int insertSelective(JyUser record);

    JyUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(JyUser record);

    int updateByPrimaryKey(JyUser record);

    JyUser queryUserInfo(JyUser condition);

    /**
     * 查询场地下的用户
     * @param condition
     * @return
     */
    List<JyUser> searchUserBySiteCode(JyUserQueryCondition condition);

    /**
     * 根据userId批量查询用户
     * @param request
     * @return
     */
    List<JyUser> queryByUserIds(JyUserBatchRequest request);

    /**
     * 根据userId批量修改
     * @param request
     * @return
     */
    int batchUpdateByUserIds(JyUserBatchRequest request);

    /**
     * 查询场地下未分配网格的用户数
     * @param condition
     * @return
     */
    Integer queryUndistributedCountBySiteCode(JyUserQueryCondition condition);

    /**
     *
     * @param condition
     * @return
     */
    List<JyUser> queryDifference(JyUserQueryCondition condition);

    /**
     * 从给出的用户ID中筛选出离职的
     * @param request
     * @return
     */
    List<JyUser> batchQueryQuitUserByUserId(JyUserBatchRequest request);
}
