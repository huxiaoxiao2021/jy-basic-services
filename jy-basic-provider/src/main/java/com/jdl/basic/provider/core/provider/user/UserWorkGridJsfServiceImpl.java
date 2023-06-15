package com.jdl.basic.provider.core.provider.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridBatchRequest;
import com.jdl.basic.api.domain.user.UserWorkGridRequest;
import com.jdl.basic.api.service.user.UserWorkGridJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.service.user.UserWorkGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userWorkGridJsfServiceImpl")
public class UserWorkGridJsfServiceImpl implements UserWorkGridJsfService {
    @Autowired
    private UserWorkGridService userWorkGridService;

    @Override
    public Result<List<UserWorkGrid>> queryByCondition(UserWorkGridRequest request) {
        return userWorkGridService.queryByCondition(request);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridJsfServiceImpl.queryDifference", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<UserWorkGrid>> queryDifference(UserWorkGridRequest request) {
        return userWorkGridService.queryDifference(request);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridJsfServiceImpl.batchInsert", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchInsert(UserWorkGridBatchRequest request) {
        return userWorkGridService.batchInsert(request);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridJsfServiceImpl.batchDelete", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchDelete(UserWorkGridBatchRequest request) {
        return userWorkGridService.batchDelete(request);
    }

    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridJsfServiceImpl.queryByUserIdsWithCondition", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<UserWorkGrid>> queryByUserIdsWithCondition(UserWorkGridBatchRequest request) {
        return userWorkGridService.queryByUserIdsWithCondition(request);
    }

    @Override
    public Result<List<JyUser>> getWorkGridDistributedStaff(UserWorkGridRequest request) {
        return userWorkGridService.getWorkGridDistributedStaff(request);
    }

}
