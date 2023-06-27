package com.jdl.basic.provider.core.service.user.impl;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.user.*;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.user.UserWorkGridDao;
import com.jdl.basic.provider.core.service.user.UserService;
import com.jdl.basic.provider.core.service.user.UserWorkGridService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserWorkGridServiceImpl implements UserWorkGridService {

    @Autowired
    private UserWorkGridDao userWorkGridDao;

    @Autowired
    private UserService userService;

    @Override
    public Result<List<UserWorkGrid>> batchQueryUserWorkGridByGridKey(UserWorkGridBatchRequest request) {
        Result<List<UserWorkGrid>> result = Result.success();
        if (CollectionUtils.isEmpty(request.getUserWorkGrids())) {
            return result.toFail("网格主键不能为空！");
        }
        return result.setData(userWorkGridDao.batchQueryUserWorkGridByGridKey(request));
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.batchInsert", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    @Transactional
    public Result<Boolean> batchInsert(UserWorkGridBatchRequest request) {
        Result<Boolean> result = Result.success();
        if (request.getUserWorkGrids() == null) {
            return result.toFail("插入记录不能为空！");
        }
        List<JyUser> users = getUsers(request.getUserWorkGrids());
        JyUserBatchRequest batchRequest = new JyUserBatchRequest();
        batchRequest.setUsers(users);
        Result<List<JyUser>> userResult = userService.queryByUserIds(batchRequest);
        if (userResult.isFail()) {
            return result.toFail(userResult.getMessage());
        }
        for(JyUser user : userResult.getData()) {
            if (user.getGridDistributeFlag()) {
                return result.toFail(String.format("用户【%s】已分配网格！", user.getUserErp()));
            }
        }
        if (userWorkGridDao.batchInsert(request) > 0) {
            result.setData(Boolean.TRUE);
            batchRequest.setGridDistributeFlag(JyUserDistributeStatusEnum.DISTRIBUTED.getFlag());
            userService.batchUpdateByUserIds(batchRequest);
        } else {
            result.toFail("插入记录失败！");
        }
        return result;
    }

    private List<JyUser> getUsers(List<UserWorkGrid> userWorkGrids) {
        return userWorkGrids.stream().map(item -> {
            JyUser user = new JyUser();
            user.setId(item.getUserId());
            return user;
        }).collect(Collectors.toList());
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.userWorkGrids", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchDelete(UserWorkGridBatchRequest request) {
        Result<Boolean> result = Result.success();

        if (request.getUserWorkGrids() == null) {
            return result.toFail("删除记录不能为空！");
        }
        if (StringUtils.isEmpty(request.getWorkGridKey())) {
            return result.toFail("网格主键不能为空！");
        }
        if (request.getUpdateTime() == null) {
            return result.toFail("修改时间不能为空！");
        }
        if(StringUtils.isEmpty(request.getUpdateUserErp())) {
            return result.toFail("修改人erp不能为空！");
        }
        if (StringUtils.isEmpty(request.getUpdateUserName())) {
            return result.toFail("修改人姓名不能为空！");
        }

        List<JyUser> users = getUsers(request.getUserWorkGrids());
        JyUserBatchRequest batchRequest = new JyUserBatchRequest();
        batchRequest.setUsers(users);
        batchRequest.setGridDistributeFlag(JyUserDistributeStatusEnum.UNDISTRIBUTED.getFlag());
        // 移出网格 将人员分配状态修改未分配
        userService.batchUpdateByUserIds(batchRequest);
        result.setData(userWorkGridDao.batchDelete(request) > 0);
        return result;
    }

    @Override
    public Result<List<UserWorkGrid>> queryByUserIds(UserWorkGridBatchRequest request) {
        Result<List<UserWorkGrid>> result = Result.success();
        if (CollectionUtils.isEmpty(request.getUserWorkGrids())) {
            return result.setData(new ArrayList<>());
        }
        result.setData(userWorkGridDao.queryByUserIds(request));
        return result;
    }

    @Override
    public Result<List<JyUser>> getWorkGridDistributedStaff(UserWorkGridRequest request) {
        Result<List<JyUser>> result = Result.success();
        if (StringUtils.isEmpty(request.getWorkGridKey())) {
            return result.toFail("网格主键不能为空！");
        }
        request.setWorkGridKey(request.getWorkGridKey().trim());
        List<UserWorkGrid> userWorkGrids = userWorkGridDao.queryByWorkGridKey(request);
        if (CollectionUtils.isNotEmpty(userWorkGrids)) {
            List<JyUser> users = getUsers(userWorkGrids);
            JyUserBatchRequest batchRequest = new JyUserBatchRequest();
            batchRequest.setUsers(users);
            Result<List<JyUser>> userResult =  userService.queryByUserIds(batchRequest);
            if (userResult.getData() != null) {
                result.setData(userResult.getData());
            } else {
                result.toFail(userResult.getMessage());
            }
        } else {
            result.setData(new ArrayList<>());
        }
        return result;
    }

    @Override
    public Result<List<UserWorkGrid>> queryDeletedUserWorkGrid(UserWorkGridRequest request) {
        Result<List<UserWorkGrid>> result = Result.success();
        if (StringUtils.isEmpty(request.getWorkGridKey())) {
            return result.toFail("网格主键不能为空！");
        }
        if (request.getUpdateTime() == null) {
            return result.toFail("查询更新时间不能为空！");
        }
        System.out.println(request.getClass().getName());
        System.out.println(request.getWorkGridKey());
        return result.setData(userWorkGridDao.queryDeletedUserWorkGrid(request));
    }
}
