package com.jdl.basic.provider.core.service.user.impl;

import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.provider.core.dao.user.JyUserDao;
import com.jdl.basic.provider.core.service.user.UserService;
import com.jdl.basic.rpc.exception.JYBasicRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  JyUserDao jyUserDao;

  @Override
  public JyUser queryUserInfo(JyUser condition) {
    if (ObjectHelper.isEmpty(condition.getUserErp())
        || ObjectHelper.isEmpty(condition.getEntryDate())
        || ObjectHelper.isEmpty(condition.getNature())) {
      throw new JYBasicRpcException("查询参数错误：erp、入职时间、用工种类不能为空！");
    }
    return jyUserDao.queryUserInfo(condition);
  }

  @Override
  public int saveUser(JyUser jyUser) {
    return jyUserDao.insertSelective(jyUser);
  }

  @Override
  public int updateUser(JyUser jyUser) {
    return jyUserDao.updateByPrimaryKeySelective(jyUser);
  }
}
