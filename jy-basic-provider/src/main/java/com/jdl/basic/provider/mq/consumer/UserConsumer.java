package com.jdl.basic.provider.mq.consumer;

import com.jd.jmq.common.message.Message;
import com.jd.joyqueue.client.springboot2.annotation.JmqListener;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.enums.UserSatusEnum;
import com.jdl.basic.common.utils.BeanUtils;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.service.cross.SortCrossService;
import com.jdl.basic.provider.core.service.user.UserService;
import com.jdl.basic.provider.dto.SortCrossModifyDto;
import com.jdl.basic.provider.dto.UserInfoBusinessDTO;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class UserConsumer {

  @Autowired
  UserService userService;


  @JmqListener(id = "basic-consumer", topics = {"${mq.consumer.topic.wlUserInfo}"})
  public void onMessage(List<Message> messages) {
    Message message =messages.get(0);
    String content = message.getText();
    log.info("consumer wlUserInfo topic: {}，data：{}", message.getTopic(), content);
    if (StringUtils.isEmpty(content)) {
      return;
    }
    UserInfoBusinessDTO userInfo = JsonHelper.toObject(content, UserInfoBusinessDTO.class);
    if (ObjectHelper.isNotNull(userInfo)) {
      JyUser condition = assembleUser(userInfo);
      //加锁防重
      JyUser exitUser = userService.queryUserInfo(condition);
      int rs = 0;
      Date now = new Date();
      if (ObjectHelper.isNotNull(exitUser)) {
        condition.setId(exitUser.getId());
        condition.setUpdateTime(now);
        rs = userService.updateUser(condition);
      } else {
        condition.setCreateTime(now);
        condition.setUpdateTime(now);
        rs = userService.saveUser(condition);
      }
      if (rs <= 0) {
        log.error("同步用户数据失败");
      }
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
    //user.setQuitActionDate(userInfo.getQuitActionDate());
    user.setNature(userInfo.getNature());
    user.setNatureDesc(userInfo.getNatureDesc());
    user.setPositionCode(userInfo.getPositionCode());
    user.setPositionName(userInfo.getPositionName());
    return user;
  }

}
