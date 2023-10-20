package com.jdl.basic.provider.mq.consumer;

import static com.jdl.basic.common.contants.Constants.LOCK_EXPIRE;

import com.jd.jmq.common.message.Message;
import com.jd.joyqueue.client.springboot2.annotation.JmqListener;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.JyUserDistributeStatusEnum;
import com.jdl.basic.api.domain.user.RemoveUserDto;
import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridBatchRequest;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.enums.WorkSiteTypeEnum;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.JYBasicRpcException;
import com.jdl.basic.provider.config.lock.JimDbLock;
import com.jdl.basic.provider.core.dao.user.UserWorkGridDao;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.user.UserService;
import com.jdl.basic.provider.core.service.workStation.WorkGridService;
import com.jdl.basic.provider.dto.BasicBaseStaffDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class BasicBaseStaffChangeConsumer {

  @Autowired
  UserService userService;
  @Autowired
  JimDbLock jimDbLock;
  @Autowired
  BaseMajorManager baseMajorManager;
  @Autowired
  UserWorkGridDao userWorkGridDao;
  @Autowired
  WorkGridService workGridService;



  @JmqListener(id = "jmq2-consumer", topics = {"${mq.consumer.topic.basic_base_staff_change}"})
  public void onMessage(List<Message> messages) {
    for (Message message : messages) {
      String content = message.getText();
      log.info("consumer basic_base_staff_change topic: {}，data：{}", message.getTopic(), content);
      if (StringUtils.isEmpty(content)) {
        return;
      }
      BasicBaseStaffDTO baseStaffDTO = JsonHelper.toObject(content, BasicBaseStaffDTO.class);
      if (ObjectHelper.isNotNull(baseStaffDTO)) {
        if (ObjectHelper.isEmpty(baseStaffDTO.getAccountNumber()) || ObjectHelper.isEmpty(baseStaffDTO.getSiteCode())) {
          log.error("用户数据必要参数不全：{}", content);
          return;
        }
        JyUser condition = assembleUser(baseStaffDTO);
        String userLockKey = String.format(Constants.USER_LOCK_PREFIX, condition.getUserErp());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        if (!jimDbLock.lock(userLockKey, uuid, LOCK_EXPIRE, TimeUnit.SECONDS)) {
          throw new JYBasicRpcException("BasicBaseStaffChangeConsumer 未获取到锁,稍后重试！");
        }
        try {
          JyUser exitUser = userService.queryUserInfo(condition);
          Date now = new Date();
          if (ObjectHelper.isNotNull(exitUser)) {
            if (ObjectHelper.isNotNull(baseStaffDTO.getIsResign()) && Constants.YN_NO.equals(baseStaffDTO.getIsResign())){
              //青龙离职JD没离职--解除跟场地的关系，并且清除跟网格的关系数据
              log.info("青龙离职但是集团没有离职：{}",content);
              condition.setSiteCode(Constants.YN_NO);
              condition.setGridDistributeFlag(JyUserDistributeStatusEnum.UNDISTRIBUTED.getFlag());
            }
            else {
              //刷新场地数据
              //BaseStaffSiteOrgDto baseStaffSiteOrgDto = baseMajorManager.getBaseStaffByErp(condition.getUserErp());
              condition.setSiteCode(baseStaffDTO.getSiteCode());
              condition.setSiteName(baseStaffDTO.getSiteName());
              if (ObjectHelper.isNotNull(baseStaffDTO.getSubType())){
                WorkSiteTypeEnum siteTypeEnum = WorkSiteTypeEnum.getWorkingSiteTypeBySubType(baseStaffDTO.getSubType());
                if (ObjectHelper.isEmpty(siteTypeEnum)) {
                  log.info("非分拣中心类型场地数据：{}", content);
                  condition.setSiteType(WorkSiteTypeEnum.OTHER.getCode());
                }
                else {
                  condition.setSiteType(siteTypeEnum.getCode());
                }
              }
            }
            condition.setId(exitUser.getId());
            condition.setUpdateTime(now);
            //异动
            if (!baseStaffDTO.getSiteCode().equals(exitUser.getSiteCode())){
              condition.setGridDistributeFlag(JyUserDistributeStatusEnum.UNDISTRIBUTED.getFlag());
            }
            userService.updateUser(condition);

            //离职或者异动（换场地）
            if ((ObjectHelper.isNotNull(baseStaffDTO.getIsResign()) && Constants.YN_NO.equals(baseStaffDTO.getIsResign()))
                || !baseStaffDTO.getSiteCode().equals(exitUser.getSiteCode())){
              condition.setSiteCode(exitUser.getSiteCode());
              invalidateSiteUserCacheData(condition);
              condition.setSiteCode(baseStaffDTO.getSiteCode());
              invalidateSiteUserCacheData(condition);

              UserWorkGridBatchRequest userWorkGridBatchRequest = assembleUserWorkGridBatchRequest(exitUser);
              List<UserWorkGrid>  userWorkGridList =userWorkGridDao.queryByUserIds(userWorkGridBatchRequest);
              if (CollectionUtils.isNotEmpty(userWorkGridList)){
                for (UserWorkGrid userWorkGrid:userWorkGridList){
                  WorkGrid workGrid =workGridService.queryByWorkGridKey(userWorkGrid.getWorkGridKey());
                  if (ObjectHelper.isNotNull(workGrid) && ObjectHelper.isNotNull(workGrid.getSiteCode()) && workGrid.getSiteCode().equals(exitUser.getSiteCode())){
                    RemoveUserDto removeUserDto = assembleRemoveUserDto(exitUser, now);
                    userWorkGridDao.removeFromGridByUserId(removeUserDto);
                    log.info("青龙离职或者异动，移除场地后解除旧场地的网格所属关系");
                  }
                }
              }
            }
          }else {
            log.info("该员工已经在JD离职：{}",content);
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

  private RemoveUserDto assembleRemoveUserDto(JyUser exitUser, Date now) {
    RemoveUserDto removeUserDto =new RemoveUserDto();
    removeUserDto.setUserId(exitUser.getId());
    removeUserDto.setUpdateUserErp("sys");
    removeUserDto.setUpdateUserName("青龙离职移除");
    removeUserDto.setUpdateTime(now);
    return removeUserDto;
  }

  private UserWorkGridBatchRequest assembleUserWorkGridBatchRequest(JyUser exitUser) {
    UserWorkGridBatchRequest userWorkGridBatchRequest =new UserWorkGridBatchRequest();
    UserWorkGrid userWorkGrid =new UserWorkGrid();
    userWorkGrid.setUserId(exitUser.getId());
    List<UserWorkGrid> userWorkGrids =new ArrayList<>();
    userWorkGrids.add(userWorkGrid);
    userWorkGridBatchRequest.setUserWorkGrids(userWorkGrids);
    return userWorkGridBatchRequest;
  }

  private JyUser assembleUser(BasicBaseStaffDTO baseStaffDTO) {
    JyUser user = new JyUser();
    user.setUserErp(baseStaffDTO.getAccountNumber());
    return user;
  }

}
