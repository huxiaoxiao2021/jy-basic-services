package com.jdl.basic.provider.core.service.user.impl;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.JyUserBatchRequest;
import com.jdl.basic.api.domain.user.JyUserQueryCondition;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.provider.core.dao.user.JyUserDao;
import com.jdl.basic.provider.core.service.user.UserService;
import com.jdl.basic.rpc.exception.JYBasicRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
  public Result<List<JyUser>> searchUserByCondition(JyUserQueryCondition condition) {
    Result<List<JyUser>> result = Result.success();
    if (condition.getSiteCode() == null) {
      return result.toFail("场地编码不能为空！");
    }
    result.setData(jyUserDao.searchUserByCondition(condition));
    return result;
  }

  @Override
  public Result<List<JyUser>> queryByUserIds(JyUserBatchRequest request) {
    Result<List<JyUser>> result = Result.success();
    if (request.getUsers() == null) {
      return result.setData(new ArrayList<>());
    }
    result.setData(jyUserDao.queryByUserIds(request));
    return result;
  }

//  @Override
//  public Result<List<JyUser>> getUnDistributedStaff(JyUserQueryCondition condition) {
//    Result<List<JyUser>> result = Result.success();
//    if (condition.getSiteCode() == null) {
//      return result.toFail("场地编码不能为空！");
//    }
//    if (condition.getNature() == null) {
//      return result.toFail("工种类型不能为空！");
//    }
//    return result.setData(jyUserDao.getUnDistributedStaff(condition));
//  }

  @Override
  public Result<Boolean> batchUpdateByUserIds(JyUserBatchRequest request) {
    Result<Boolean> result = Result.success();
    if (request.getUsers() == null) {
      return result.toFail("更新用户不能为null！");
    }
    if (request.getGridDistributeFlag() == null) {
      return result.toFail("用户网格分配状态不能为空！");
    }
    return result.setData(jyUserDao.batchUpdateByUserIds(request) > 0);
  }

  @Override
  public Result<Integer> queryUndistributedCountBySiteCode(JyUserQueryCondition condition) {
    Result<Integer> result = Result.success();
    if (condition.getSiteCode() == null) {
      return result.toFail("场地编码不能为空！");
    }
    return result.setData(jyUserDao.queryUndistributedCountBySiteCode(condition));
  }

  @Override
  public Result<List<JyUser>> queryDifference(JyUserQueryCondition condition) {
    Result<List<JyUser>> result = Result.success();
    if (condition.getSiteCode() == null) {
      return result.toFail("场地编码不能为空！");
    }
    if (condition.getCreateTime() == null) {
      return result.toFail("创建时间不能为空！");
    }
    if (condition.getUpdateTime() == null) {
      return result.toFail("更新时间不能为空！");
    }
    return result.setData(jyUserDao.queryDifference(condition));
  }
}
