package com.jdl.basic.provider.core.service.user.impl;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.UserWorkGrid;
import com.jdl.basic.api.domain.user.UserWorkGridRequest;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.user.UserWorkGridDao;
import com.jdl.basic.provider.core.service.user.UserService;
import com.jdl.basic.provider.core.service.user.UserWorkGridService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserWorkGridServiceImpl implements UserWorkGridService {

    @Autowired
    private UserWorkGridDao userWorkGridDao;

    @Autowired
    private UserService userService;

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.queryPageList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<UserWorkGrid>> queryPageList(UserWorkGridRequest request) {
        Result<List<UserWorkGrid>> result = Result.success();
        Result<Void> checkResult = checkRequest(request);
        if (checkResult.isFail()) {
            return result.toFail(checkResult.getMessage());
        }
        result.setData(userWorkGridDao.queryPageList(request));
        return result;
    }

    private Result<Void> checkRequest(UserWorkGridRequest request) {
        Result<Void> result = Result.success();
        if (request.getPageNumber() < 1) {
            return result.toFail("分页查询当前页不能小于1！");
        }
        return result;
    }


    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.queryRecordDetail", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<UserWorkGrid>> queryRecordDetail(String workGridKey) {
        Result<List<UserWorkGrid>> result = Result.success();
        if (StringUtils.isEmpty(workGridKey)) {
            return result.toFail("网格主键不能为空!");
        }
        result.setData(userWorkGridDao.queryRecordDetail(workGridKey));
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.queryDifference", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<UserWorkGrid>> queryDifference(UserWorkGridRequest request) {
        Result<List<UserWorkGrid>> result = Result.success();
        if (StringUtils.isEmpty(request.getWorkGridKey())) {
            return result.toFail("网格主键不能为空！");
        }
        if (request.getWorkGridKey().trim().equals("")) {
            return result.toFail("网格主键为空字符串！");
        }
        if (request.getCreateTime() == null) {
            return result.toFail("人员分配到网格记录创建时间不能为空！");
        }
        if (request.getUpdateTime() == null) {
            return result.toFail("人员分配到网格记录更新时间不能为空！");
        }
        result.setData(userWorkGridDao.queryDifference(request));
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.queryTotal", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Long> queryTotal(UserWorkGridRequest request) {
        Result<Long> result = Result.success();
        result.setData(userWorkGridDao.queryTotal(request));
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.batchInsert", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchInsert(List<UserWorkGrid> userWorkGrids) {
        Result<Boolean> result = Result.success();
        result.setData(userWorkGridDao.batchInsert(userWorkGrids) > 0);
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridServiceImpl.userWorkGrids", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchDelete(List<UserWorkGrid> userWorkGrids) {
        Result<Boolean> result = Result.success();
        if (CollectionUtils.isEmpty(userWorkGrids)) {
            return result.toFail("删除记录不能为空！");
        }
        UserWorkGrid userWorkGrid = userWorkGrids.get(0);
        String workGridKey = userWorkGrid.getWorkGridKey();
        Date updateTime = userWorkGrid.getUpdateTime();
        String updateUserErp = userWorkGrid.getUpdateUserErp();
        String updateUserName = userWorkGrid.getUpdateUserName();

        if (StringUtils.isEmpty(workGridKey)) {
            return result.toFail("网格主键不能为空！");
        }
        if (updateTime == null) {
            return result.toFail("修改时间不能为空！");
        }
        if(StringUtils.isEmpty(updateUserErp)) {
            return result.toFail("修改人erp不能为空！");
        }
        if (StringUtils.isEmpty(updateUserName)) {
            return result.toFail("修改人姓名不能为空！");
        }
        result.setData(userWorkGridDao.batchDelete(userWorkGrids, workGridKey, updateTime, updateUserErp, updateUserName) > 0);
        return result;
    }

    @Override
    public Result<List<UserWorkGrid>> queryByUserIds(List<Long> userIds) {
        Result<List<UserWorkGrid>> result = Result.success();
        if (CollectionUtils.isEmpty(userIds)) {
            return result.toFail("用户id不能为空！");
        }
        result.setData(userWorkGridDao.queryByUserIds(userIds));
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
        if (userWorkGrids != null) {
            List<Long> userIds = userWorkGrids.stream().map(UserWorkGrid::getUserId).collect(Collectors.toList());
            Result<List<JyUser>> userResult =  userService.queryByUserIds(userIds);
            if (userResult.getData() != null) {
                result.setData(userResult.getData());
            } else {
                result.toFail(userResult.getMessage());
            }
        }
        return result;
    }
}
