package com.jdl.basic.provider.core.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.user.*;
import com.jdl.basic.api.enums.JyJobTypeEnum;
import com.jdl.basic.api.enums.UserJobTypeEnum;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.BeanUtils;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.provider.JYBasicRpcException;
import com.jdl.basic.provider.config.cache.CacheService;
import com.jdl.basic.provider.config.ducc.DuccPropertyConfiguration;
import com.jdl.basic.provider.core.dao.user.JyUserDao;
import com.jdl.basic.provider.core.service.user.UserService;
import com.jdl.basic.provider.core.service.user.model.JyUserQueryCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  JyUserDao jyUserDao;

  @Autowired
  CacheService jimdbCacheService;

  @Autowired
  private DuccPropertyConfiguration duccPropertyConfiguration;

  @Override
  public JyUser queryUserInfo(JyUser condition) {
    if (ObjectHelper.isEmpty(condition.getUserErp())) {
     throw new JYBasicRpcException("查询参数错误：erp为空！");
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
    List<JyUser> jyUsers;
    Map<String, String> jsonMap = jimdbCacheService.hGetAll(String.format(CacheKeyConstants.CACHE_KEY_SEARCH_SITE_USER, siteCode));
    if (jsonMap != null && !jsonMap.isEmpty()) {
      jyUsers = jsonMap.values().stream().map(item -> JSON.parseObject(item, JyUser.class)).collect(Collectors.toList());
    } else {
      jyUsers = jyUserDao.searchUserBySiteCode(siteCode);
      Map<String, JyUser> idToUserMap;
      idToUserMap = jyUsers.stream().collect(Collectors.toMap((item) -> item.getId().toString(), item -> item));
      jimdbCacheService.hMSetEx(String.format(CacheKeyConstants.CACHE_KEY_SEARCH_SITE_USER, siteCode), idToUserMap, 30L, TimeUnit.MINUTES);
    }
    result.setData(jyUsers);
    return result;
  }

  @Override
  @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserServiceImpl.queryByUserIds", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
  public Result<List<JyUser>> queryByUserIds(JyUserBatchRequest request) {
    Result<List<JyUser>> result = Result.success();
    if (request.getUsers() == null) {
      return result.setData(new ArrayList<>());
    }
    // 查询场景针对某一个网格 限制300
    if (request.getUsers().size() > 512) {
      return result.toFail(String.format("根据用户ID批量查询人员分配网格超出查询上限,查询量【%s】,上限【%s】", request.getUsers().size(), 512));
    }
    result.setData(jyUserDao.queryByUserIds(request));
    return result;
  }


  @Override
  @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserServiceImpl.batchUpdateByUserIds", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
  public Result<Boolean> batchUpdateByUserIds(JyUserBatchRequest request) {
    Result<Boolean> result = Result.success();
    if (CollectionUtils.isEmpty(request.getUsers())) {
      return result.toFail("更新用户不能为空！");
    }
    if (request.getGridDistributeFlag() == null) {
      return result.toFail("用户网格分配状态不能为空！");
    }
    if (request.getSiteCode() == null) {
      return result.toFail("更新用户场地编码不能为空！");
    }
    Boolean bool = jyUserDao.batchUpdateByUserIds(request) > 0;
    Integer siteCode = request.getSiteCode();
    Map<String, String> map = jimdbCacheService.hGetAll(String.format(CacheKeyConstants.CACHE_KEY_SEARCH_SITE_USER, siteCode));
    // 更新JimDb中的缓存数据
    if (bool) {
      String[] fieldKeys = request.getUsers().stream().map(item -> String.valueOf(item.getId())).toArray(String[]::new);
      List<String> json = jimdbCacheService.hMGet(String.format(CacheKeyConstants.CACHE_KEY_SEARCH_SITE_USER, siteCode), fieldKeys);
      if (CollectionUtils.isNotEmpty(json)) {
        Map<String, JyUser> idToUserMap = json.stream().filter(Objects::nonNull).map(item -> JSON.parseObject(item, JyUser.class))
                .collect(Collectors.toMap(item -> String.valueOf(item.getId()), item -> item));
        for (JyUser user: request.getUsers()) {
          JyUser jyUser = idToUserMap.get(String.valueOf(user.getId()));
          if (jyUser != null) {
            jyUser.setGridDistributeFlag(user.getGridDistributeFlag());
          }
        }
        jimdbCacheService.hMSetEx(String.format(CacheKeyConstants.CACHE_KEY_SEARCH_SITE_USER, siteCode), idToUserMap, 30L, TimeUnit.MINUTES);
      }
    }
    return result.setData(bool);
  }

  @Override
  @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserServiceImpl.queryUndistributedCountBySiteCode", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
  public Result<Integer> queryUndistributedCountBySiteCode(JyUserQueryDto dto) {
    Result<Integer> result = Result.success();
    JyUserQueryCondition condition = convertQuery(dto);
    if (condition.getSiteCode() == null) {
      return result.toFail("场地编码不能为空！");
    }
    return result.setData(jyUserDao.queryUndistributedCountBySiteCode(condition));
  }

  @Override
  @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserServiceImpl.queryDifference", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
  public Result<UserChangeDto> queryDifference(JyUserQueryDto dto) {
    Result<UserChangeDto> result = Result.success();
    JyUserQueryCondition condition = convertQuery(dto);
    if (condition.getSiteCode() == null) {
      return result.toFail("场地编码不能为空！");
    }
    if (condition.getQuitActionDate() == null) {
      return result.toFail("离职时间不能为空！");
    }
    if (condition.getEntryDate() == null) {
      return result.toFail("入职时间不能为空！");
    }
    UserChangeDto userChangeDto = new UserChangeDto();
    userChangeDto.setTotalNew(jyUserDao.queryNewUserCountByTime(condition));
    userChangeDto.setTotalResign(jyUserDao.queryQuitUserCountByTime(condition));
    return result.setData(userChangeDto);
  }

  @Override
  @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserServiceImpl.batchQueryQuitUserByUserId", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
  public Result<List<JyUser>> batchQueryQuitUserByUserId(JyUserBatchRequest request) {
    Result<List<JyUser>> result = Result.success();
    if (CollectionUtils.isEmpty(request.getUsers())) {
      return result.setData(new ArrayList<>());
    }
    // 最多查询50个网格 平均每个网格一天删除20条记录
    if (request.getUsers().size() > 1000) {
      return result.toFail(String.format("人员分配网格超出查询上限,查询量【%s】,上限【%s】", request.getUsers().size(), 1000));
    }
    return result.setData(jyUserDao.batchQueryQuitUserByUserId(request));
  }


  @Override
  @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserServiceImpl.queryUnDistributedUserList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
  public List<JyUser> queryUnDistributedUserList(UnDistributedUserQueryDto dto) {
    return jyUserDao.queryUnDistributedUserList(dto);
  }

@Override
@JProfiler(jKey = Constants.UMP_APP_NAME + ".UserServiceImpl.queryUserListBySiteAndPosition", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
public Result<List<JyUser>> queryUserListBySiteAndPosition(JyUserQueryDto dto) {
	Result<List<JyUser>> result = Result.success();
    JyUserQueryCondition condition = convertQuery(dto);
    return result.setData(jyUserDao.queryUserListBySiteAndPosition(condition));
}

  @Override
  @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserServiceImpl.queryNatureUndistributedUsers", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
  public Result<List<JyUser>> queryNatureUndistributedUsers(JyUserQueryDto dto) {
    Result<List<JyUser>> result = Result.success();
    JyUserQueryCondition condition = convertQuery(dto);
    if (condition.getSiteCode() == null) {
      return result.toFail("场地编码不能为空！");
    }
    if (CollectionUtils.isEmpty(condition.getNatureList())) {
      return result.toFail("工种不能为空！");
    }
    return result.setData(jyUserDao.queryNatureUndistributedUsers(condition));
  }

  @Override
  public Result<Boolean> checkUserBelongToJy(JyUserQueryDto dto) {
    Result<Boolean> result = Result.success();
    result.setData(Boolean.FALSE);
    if (StringUtils.isEmpty(dto.getUserErp())) {
      return result.toFail("人员erp不能为空！");
    }
    JyUser condition = new JyUser();
    condition.setUserErp(dto.getUserErp());
    JyUser jyUser = jyUserDao.queryUserInfo(condition);
    if (jyUser == null) {
      return result;
    }

    String organizationFullPath = jyUser.getOrganizationFullPath();
    // 判断是否包含拣运机构ID
    List<String> jyOrganizationCodeList = getJyOrganizationCodeList();
    log.info("获取ducc的值：{}", JsonHelper.toJSONString(jyOrganizationCodeList));
    for (String organizationCode : jyOrganizationCodeList) {
      if (organizationFullPath.contains(organizationCode)) {
        result.setData(Boolean.TRUE);
        return result;
      }
    }
    return result;
  }

  private List<String> getJyOrganizationCodeList() {
    if (com.jdl.basic.common.utils.StringUtils.isEmpty(duccPropertyConfiguration.getJyOrganizationCodeStr())) {
      return new ArrayList<>();
    }else {
      return Arrays.asList(duccPropertyConfiguration.getJyOrganizationCodeStr().split(";"));
    }
  }

  @Override
  public JyUserDto queryByUserErp(JyUserQueryDto jyUserQueryDto) {
    JyUser jyUser =jyUserDao.queryByUserErp(jyUserQueryDto);
    if (ObjectHelper.isNotNull(jyUser)){
      JyUserDto jyUserDto = BeanUtils.copy(jyUser,JyUserDto.class);
      UserJobTypeEnum userJobTypeEnum = UserJobTypeEnum.getJyJobEnumByNature(jyUser.getNature());
      if (userJobTypeEnum != null) {
        jyUserDto.setNature(String.valueOf(userJobTypeEnum.getJyJobTypeCode()));
      }
      return jyUserDto;
    }
    return null;
  }

  private JyUserQueryCondition convertQuery(JyUserQueryDto dto) {
    JyUserQueryCondition condition = new JyUserQueryCondition();
    condition.setId(dto.getId());
    condition.setUserErp(dto.getUserErp());
    condition.setSex(dto.getSex());
    condition.setEntryDate(dto.getEntryDate());
    condition.setOrganizationCode(dto.getOrganizationCode());
    condition.setUserStatus(dto.getUserStatus());
    condition.setSiteCode(dto.getSiteCode());
    condition.setSiteType(dto.getSiteType());
    condition.setQuitActionDate(dto.getQuitActionDate());
    JyJobTypeEnum jobTypeEnum = JyJobTypeEnum.getJobTypeEnum(dto.getJobType());
    condition.setNatureList(jobTypeEnum == null ? new ArrayList<>() : jobTypeEnum.getUserJobTypeCodeList());
    condition.setPositionCode(dto.getPositionCode());
    condition.setPositionName(dto.getPositionName());
    condition.setGridDistributeFlag(dto.getGridDistributeFlag());
    condition.setCreateTime(dto.getCreateTime());
    condition.setUpdateTime(dto.getUpdateTime());
    return condition;
  }
}
