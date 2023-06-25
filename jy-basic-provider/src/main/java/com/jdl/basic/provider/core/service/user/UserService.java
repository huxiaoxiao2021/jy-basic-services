package com.jdl.basic.provider.core.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.JyUserBatchRequest;
import com.jdl.basic.api.domain.user.JyUserQueryCondition;

import com.jdl.basic.api.domain.user.UnDistributedUserQueryDto;
import java.util.List;

public interface UserService {

  /**
   * 查询用户信息 (erp、用工种类、入职时间)
   */
  JyUser queryUserInfo(JyUser condition);

  /**
   * 保存用户信息
   * @param jyUser
   * @return
   */
  int saveUser(JyUser jyUser);

  /**
   * 根据主键更新用户信息
   * @param jyUser
   * @return
   */
  int updateUser(JyUser jyUser);

  /**
   * 根据条件查询员工
   * @param condition
   * @return
   */
  Result<List<JyUser>> searchUserBySiteCode(JyUserQueryCondition condition);

  /**
   * 根据员工id以及条件批量查询员工
   * @param request
   * @return
   */
  Result<List<JyUser>> queryByUserIds(JyUserBatchRequest request);

  /**
   * 根据用户id批量修改
   * @param request
   * @return
   */
  Result<Boolean> batchUpdateByUserIds(JyUserBatchRequest request);

  /**
   * 查询场地下未分配人员数量
   * @param condition
   * @return
   */
  Result<Integer> queryUndistributedCountBySiteCode(JyUserQueryCondition condition);

  /**
   * 查询入职时间或离职时间大于给定时间的用户
   * @param condition
   * @return
   */
  Result<List<JyUser>> queryDifference(JyUserQueryCondition condition);

  /**
   * 从给定的id中筛选出已离职的
   * @param request
   * @return
   */
  Result<List<JyUser>> batchQueryQuitUserByUserId(JyUserBatchRequest request);

  List<JyUser> queryUnDistributedUserList(UnDistributedUserQueryDto dto);
}
