package com.jdl.basic.provider.core.service.user.impl;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.user.*;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.user.UserWorkGridDao;
import com.jdl.basic.provider.core.service.user.UserService;
import com.jdl.basic.provider.core.service.user.UserWorkGridService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserWorkGridServiceImpl implements UserWorkGridService {

    @Autowired
    private UserWorkGridDao userWorkGridDao;

    @Autowired
    private UserService userService;

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.batchQueryUserWorkGridByGridKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<UserWorkGrid>> batchQueryUserWorkGridByGridKey(UserWorkGridBatchRequest request) {
        Result<List<UserWorkGrid>> result = Result.success();
        if (CollectionUtils.isEmpty(request.getUserWorkGrids())) {
            return result.toFail("网格主键不能为空！");
        }
        // 场地网格数量不会超200
        if (request.getUserWorkGrids().size() > 200) {
            return result.toFail(String.format("根据网格主键查询人员分配网格超出查询上限,查询量【%s】,上限【%s】", request.getUserWorkGrids().size(), 200));
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
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.batchDelete", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchDelete(UserWorkGridBatchRequest request) {
        Result<Boolean> result = Result.success();

        if (request.getUserWorkGrids() == null) {
            return result.toFail("删除记录不能为空！");
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
        if (userWorkGridDao.batchDelete(request) > 0) {
            userService.batchUpdateByUserIds(batchRequest);
            result.setData(Boolean.TRUE);
        } else {
            result.toFail("删除记录失败！");
        }
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.queryByUserIds", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<UserWorkGrid>> queryByUserIds(UserWorkGridBatchRequest request) {
        Result<List<UserWorkGrid>> result = Result.success();
        if (CollectionUtils.isEmpty(request.getUserWorkGrids())) {
            return result.setData(new ArrayList<>());
        }
        // 目前场地没有超出1000人的场地
        if (request.getUserWorkGrids().size() > 1000) {
            return result.toFail(String.format("根据用户id查询人员分配网格超出查询上限,查询量【%s】,上限【%s】", request.getUserWorkGrids().size(), 1000));
        }
        result.setData(userWorkGridDao.queryByUserIds(request));
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.getWorkGridDistributedStaff", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
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
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.batchQueryDeletedUserWorkGrid", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<UserWorkGrid>> batchQueryDeletedUserWorkGrid(UserWorkGridBatchRequest request) {
        Result<List<UserWorkGrid>> result = Result.success();
        if (CollectionUtils.isEmpty(request.getUserWorkGrids())) {
            return result.toFail("网格主键不能为空！");
        }
        if (request.getUpdateTime() == null) {
            return result.toFail("查询更新时间不能为空！");
        }
        // 最多查询50个网格 平均每个网格一天删除20条记录
        if (request.getUserWorkGrids().size() > 1000) {
            return result.toFail(String.format("根据网格主键查询被删除人员分配网格超出查询上限,查询量【%s】,上限【%s】", request.getUserWorkGrids().size(), 1000));
        }
        return result.setData(userWorkGridDao.batchQueryDeletedUserWorkGrid(request));
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.removeFromGridByUserId", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public boolean removeFromGridByUserId(RemoveUserDto removeUserDto) {
        return userWorkGridDao.removeFromGridByUserId(removeUserDto) >0;
    }
}
