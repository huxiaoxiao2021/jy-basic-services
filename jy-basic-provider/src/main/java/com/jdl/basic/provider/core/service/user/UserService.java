package com.jdl.basic.provider.core.service.user;

import com.jdl.basic.api.domain.user.JyUser;

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
}
