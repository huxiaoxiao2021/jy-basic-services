package com.jdl.basic.provider.core.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.JyUserBatchRequest;
import com.jdl.basic.api.domain.user.JyUserQueryDto;
import com.jdl.basic.api.domain.user.UserChangeDto;
import com.jdl.basic.provider.core.service.user.model.JyUserQueryCondition;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.ApplicationLaunch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void queryDifference() {
        JyUserQueryDto condition = new JyUserQueryDto();
        condition.setSiteCode(910);
        condition.setEntryDate("2023-06-29");
        condition.setQuitActionDate("2023-06-30");
        Result<UserChangeDto> result = userService.queryDifference(condition);
        log.info("{}", JsonHelper.toJSONString(result));
    }

    @Test
    public void queryUndistributedCountBySiteCode() {
        JyUserQueryDto condition = new JyUserQueryDto();
        condition.setSiteCode(910);
        Result<Integer> result = userService.queryUndistributedCountBySiteCode(condition);
        log.info("{}", JsonHelper.toJSONString(result));
    }

    @Test
    public void batchUpdateByUserIds() {
        JyUserBatchRequest request = new JyUserBatchRequest();
        List<JyUser> list = new ArrayList<>();
        JyUser user = new JyUser();
        user.setId(5L);
        user.setGridDistributeFlag(false);
        list.add(user);
        request.setUsers(list);
        request.setGridDistributeFlag(false);
        Result<Boolean> result = userService.batchUpdateByUserIds(request);
        log.info("{}", JsonHelper.toJSONString(result));
    }

    @Test
    public void queryByUserIds() {
        JyUserBatchRequest request = new JyUserBatchRequest();
        List<JyUser> list = new ArrayList<>();
        JyUser user = new JyUser();
        user.setId(5L);
        user.setGridDistributeFlag(true);
        list.add(user);
        request.setUsers(list);
        Result<List<JyUser>> result = userService.queryByUserIds(request);
        log.info("{}", JsonHelper.toJSONString(result));
    }

    @Test
    public void searchUserBySiteCode() {
        JyUserQueryDto condition = new JyUserQueryDto();
        condition.setSiteCode(910);
        Result<List<JyUser>> result = userService.searchUserBySiteCode(condition.getSiteCode());
        log.info("{}", JsonHelper.toJSONString(result));
    }

    @Test
    public void batchQueryQuitUserByUserId() {
        JyUserBatchRequest request = new JyUserBatchRequest();
        List<JyUser> users = new ArrayList<>();
        JyUser user = new JyUser();
        user.setId(1L);
        users.add(user);
        request.setUsers(users);
        Result<List<JyUser>> result = userService.batchQueryQuitUserByUserId(request);
        log.info("{}", JsonHelper.toJSONString(result));
    }

    @Test
    public void queryNatureUndistributedUsers() {
        JyUserQueryDto condition = new JyUserQueryDto();
        condition.setSiteCode(910);
        condition.setJobType(1);
        Result<List<JyUser>> result = userService.queryNatureUndistributedUsers(condition);
        log.info("{}", JsonHelper.toJSONString(result));
    }
}
