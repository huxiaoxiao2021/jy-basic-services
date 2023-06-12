package com.jdl.basic.provider.core.service.user.impl;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.provider.core.dao.user.JyUserDao;
import com.jdl.basic.provider.core.service.user.UserService;
import com.jdl.basic.rpc.exception.JYBasicRpcException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

  @Override
  @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserServiceImpl.searchUserBySiteCode", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
  public Result<List<JyUser>> searchUserBySiteCode(Integer siteCode) {
    Result<List<JyUser>> result = Result.success();
    if (siteCode == null) {
      return result.toFail("场地编码不能为空！");
    }
    result.setData(jyUserDao.searchUserBySiteCode(siteCode));
    return result;
  }

  @Override
  public Result<List<JyUser>> queryByUserIds(List<Long> ids) {
    Result<List<JyUser>> result = Result.success();
    if (CollectionUtils.isEmpty(ids)) {
      return result.toFail("用户Id不能为空！");
    }
    result.setData(jyUserDao.queryByUserIds(ids));
    return result;
  }

  @Override
  public Result<List<JyUser>> getUnDistributedStaff(Integer siteCode, Integer jobType) {
    return null;
  }
}
