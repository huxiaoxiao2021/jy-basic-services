package com.jdl.basic.provider.core.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.JyUserBatchRequest;
import com.jdl.basic.api.domain.user.JyUserQueryCondition;
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
        JyUserQueryCondition condition = new JyUserQueryCondition();
        condition.setSiteCode(910);
        Date time = DateUtils.addDays(new Date(), -1);
        condition.setCreateTime(time);
        condition.setUpdateTime(time);
        Result<List<JyUser>> result = userService.queryDifference(condition);
        log.info("{}", JsonHelper.toJSONString(result));
    }

    @Test
    public void queryUndistributedCountBySiteCode() {
        JyUserQueryCondition condition = new JyUserQueryCondition();
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
}
