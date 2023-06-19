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
  Result<List<JyUser>> searchUserByCondition(JyUserQueryCondition condition);

  /**
   * 根据员工id以及条件批量查询员工
   * @param request
   * @return
   */
  Result<List<JyUser>> queryByUserIds(JyUserBatchRequest request);

  Result<Boolean> batchUpdateByUserIds(JyUserBatchRequest request);

  Result<Integer> queryUndistributedCountBySiteCode(JyUserQueryCondition condition);

  Result<List<JyUser>> queryDifference(JyUserQueryCondition condition);

  List<JyUser> queryUnDistributedUserList(UnDistributedUserQueryDto dto);
}
