package com.jdl.basic.provider.core.provider.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.JyUserBatchRequest;
import com.jdl.basic.api.domain.user.JyUserQueryCondition;
import com.jdl.basic.api.service.user.UserJsfService;
import com.jdl.basic.provider.core.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service("userJsfServiceImpl")
public class UserJsfServiceImpl implements UserJsfService {
    @Autowired
    private UserService userService;
    @Override
    public Result<List<JyUser>> searchUserByCondition(JyUserQueryCondition condition) {
        return userService.searchUserByCondition(condition);
    }

    @Override
    public Result<List<JyUser>> queryByUserId(JyUserBatchRequest request) {
        return userService.queryByUserIds(request);
    }

    @Override
    public Result<Integer> queryUndistributedCountBySiteCode(JyUserQueryCondition condition) {
        return userService.queryUndistributedCountBySiteCode(condition);
    }

    @Override
    public Result<List<JyUser>> queryDifference(JyUserQueryCondition condition) {
        return userService.queryDifference(condition);
    }
}
