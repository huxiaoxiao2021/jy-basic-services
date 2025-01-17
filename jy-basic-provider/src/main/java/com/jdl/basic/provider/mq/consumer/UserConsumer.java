package com.jdl.basic.provider.mq.consumer;

import com.jd.jmq.common.message.Message;
import com.jd.joyqueue.client.springboot2.annotation.JmqListener;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.RemoveUserDto;
import com.jdl.basic.api.enums.UserSatusEnum;
import com.jdl.basic.api.enums.WorkSiteTypeEnum;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.JYBasicRpcException;
import com.jdl.basic.provider.config.lock.JimDbLock;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.user.UserService;
import com.jdl.basic.provider.core.service.user.UserWorkGridService;
import com.jdl.basic.provider.dto.UserInfoBusinessDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.jdl.basic.common.contants.Constants.LOCK_EXPIRE;


@Component
@Slf4j
public class UserConsumer {

  @Autowired
  UserService userService;
  @Autowired
  UserWorkGridService userWorkGridService;
  @Autowired
  JimDbLock jimDbLock;
  @Autowired
  BaseMajorManager baseMajorManager;


  @JmqListener(id = "jmq4-consumer", topics = {"${mq.consumer.topic.wlUserInfo}"})
  public void onMessage(List<Message> messages) {
    for (Message message : messages) {
      String content = message.getText();
      log.info("consumer wlUserInfo topic: {}，data：{}", message.getTopic(), content);
      if (StringUtils.isEmpty(content)) {
        return;
      }
      UserInfoBusinessDTO userInfo = JsonHelper.toObject(content, UserInfoBusinessDTO.class);
      if (ObjectHelper.isNotNull(userInfo)) {
        if (ObjectHelper.isEmpty(userInfo.getUserName()) || ObjectHelper.isEmpty(userInfo.getEntryDate()) || ObjectHelper.isEmpty(userInfo.getNature())) {
          log.error("用户数据必要参数不全：{}", content);
          return;
        }

        if (ObjectHelper.isEmpty(userInfo.getSource())  || userInfo.getSource() == Constants.CONSTANT_NUMBER_TWO){
          log.error("数据源于账号系统，丢弃：{}",content);
          return;
        }

        JyUser condition = assembleUser(userInfo);
        String userLockKey = String.format(Constants.USER_LOCK_PREFIX, condition.getUserErp());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        if (!jimDbLock.lock(userLockKey, uuid, LOCK_EXPIRE, TimeUnit.SECONDS)) {
          throw new JYBasicRpcException("未获取到锁,稍后重试！");
        }
        try {
          JyUser exitUser = userService.queryUserInfo(condition);
          BaseStaffSiteOrgDto baseStaffSiteOrgDto = baseMajorManager.getBaseStaffByErp(userInfo.getUserName());
          if (ObjectHelper.isNotNull(baseStaffSiteOrgDto)) {
            condition.setSiteCode(baseStaffSiteOrgDto.getSiteCode());
            condition.setSiteName(baseStaffSiteOrgDto.getSiteName());
            WorkSiteTypeEnum siteTypeEnum = WorkSiteTypeEnum.getWorkingSiteTypeBySubType(baseStaffSiteOrgDto.getSubType());
            if (ObjectHelper.isEmpty(siteTypeEnum)) {
              log.info("非分拣中心类型场地数据：{}", content);
              condition.setSiteType(WorkSiteTypeEnum.OTHER.getCode());
              //return;
            }
            else {
            condition.setSiteType(siteTypeEnum.getCode());
            }
          }
          int rs = 0;
          Date now = new Date();
          if (ObjectHelper.isNotNull(exitUser)) {
            condition.setId(exitUser.getId());
            condition.setUpdateTime(now);
            rs = userService.updateUser(condition);
            //如果离职了把网格关系移除掉
            if (UserSatusEnum.QUIT.getCode().equals(userInfo.getStatus())){
              RemoveUserDto dto =new RemoveUserDto();
              dto.setUserId(exitUser.getId());
              dto.setUpdateUserErp("sys");
              dto.setUpdateUserName("系统离职");
              dto.setUpdateTime(new Date());
              userWorkGridService.removeFromGridByUserId(dto);
              invalidateSiteUserCacheData(condition);
            }
          } else if (UserSatusEnum.ONJOB.getCode().equals(condition.getUserStatus())){
            condition.setCreateTime(now);
            condition.setUpdateTime(now);
            rs = userService.saveUser(condition);
            invalidateSiteUserCacheData(condition);
          }
          else {
            return;
          }
          if (rs <= 0) {
            log.error("同步用户数据失败");
          }
        } finally {
          jimDbLock.releaseLock(userLockKey, uuid);
        }
      }
    }
  }

  private void invalidateSiteUserCacheData(JyUser condition) {
    try {
      if (ObjectHelper.isNotNull(condition) && ObjectHelper.isNotNull(condition.getSiteCode())){
        String key =String.format(CacheKeyConstants.CACHE_KEY_SEARCH_SITE_USER, condition.getSiteCode());
        if (jimDbLock.getRedisClient().exists(key)){
          jimDbLock.getRedisClient().del(key);
        }
      }
    } catch (Exception e) {
      log.error("invalidateSiteUserCacheData exception",e);
    }
  }

  private JyUser assembleUser(UserInfoBusinessDTO userInfo) {
    JyUser user = new JyUser();
    user.setUserErp(userInfo.getUserName());
    user.setUserName(userInfo.getRealName());
    user.setSex(userInfo.getSex());
    user.setEntryDate(DateHelper.getDateOfyyMMdd2(userInfo.getEntryDate()));
    user.setOrganizationCode(userInfo.getOrganizationCode());
    user.setOrganizationName(userInfo.getOrganizationName());
    user.setOrganizationFullName(userInfo.getOrganizationFullname());
    user.setUserStatus(
        UserSatusEnum.ONJOB.getCode().equals(userInfo.getStatus())
            ? UserSatusEnum.ONJOB.getCode()
            : UserSatusEnum.QUIT.getCode());
    if (ObjectHelper.isNotNull(userInfo.getQuitActionDate())){
      user.setQuitActionDate(DateHelper.getDateOfyyMMdd2(userInfo.getQuitActionDate()));
    }
    user.setNature(userInfo.getNature());
    user.setNatureDesc(ObjectHelper.isNotNull(userInfo.getNatureDesc())? userInfo.getNatureDesc() : "");
    user.setPositionCode(userInfo.getPositionCode());
    user.setPositionName(userInfo.getPositionName());
    user.setStdPositionCode(userInfo.getStdPositionCode());
    user.setStdPositionName(userInfo.getStdPositionName());
    user.setOrganizationFullPath(userInfo.getOrganizationFullPath());
    return user;
  }

}
