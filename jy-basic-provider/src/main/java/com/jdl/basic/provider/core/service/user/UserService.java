package com.jdl.basic.provider.core.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUser;

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

  Result<List<JyUser>> searchUserBySiteCode(Integer siteCode);

  Result<List<JyUser>> queryByUserIds(List<Long> ids);

  Result<List<JyUser>> getUnDistributedStaff(Integer siteCode, Integer jobType);
}
