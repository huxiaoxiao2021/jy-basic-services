package com.jdl.basic.provider.core.provider.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.UserWorkGrid;
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
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridJsfServiceImpl.listWorkGridData", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<UserWorkGrid>> queryPageList(UserWorkGridRequest request) {
        return userWorkGridService.queryPageList(request);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridJsfServiceImpl.queryRecordDetail", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<UserWorkGrid>> queryRecordDetail(String workGridKey) {
        return userWorkGridService.queryRecordDetail(workGridKey);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridJsfServiceImpl.queryDifference", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<UserWorkGrid>> queryDifference(UserWorkGridRequest request) {
        return userWorkGridService.queryDifference(request);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridJsfServiceImpl.queryTotal", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Long> queryTotal(UserWorkGridRequest request) {
        return userWorkGridService.queryTotal(request);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridJsfServiceImpl.batchInsert", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchInsert(List<UserWorkGrid> userWorkGrids) {
        return userWorkGridService.batchInsert(userWorkGrids);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridJsfServiceImpl.batchDelete", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchDelete(List<UserWorkGrid> userWorkGrids) {
        return userWorkGridService.batchDelete(userWorkGrids);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridJsfServiceImpl.queryByUserId", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<UserWorkGrid>> queryByUserIds(List<Long> userIds) {
        return userWorkGridService.queryByUserIds(userIds);
    }

    @Override
    public Result<List<JyUser>> getWorkGridDistributedStaff(UserWorkGridRequest request) {
        return userWorkGridService.getWorkGridDistributedStaff(request);
    }

}
