package com.jdl.basic.provider.core.provider.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.user.*;
import com.jdl.basic.api.enums.UserJobTypeEnum;
import com.jdl.basic.api.service.user.UserWorkGridJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.service.user.UserWorkGridService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("userWorkGridJsfServiceImpl")
public class UserWorkGridJsfServiceImpl implements UserWorkGridJsfService {
    @Autowired
    private UserWorkGridService userWorkGridService;

    @Override
    public Result<List<UserWorkGrid>> batchQueryUserWorkGridByGridKey(UserWorkGridBatchRequest request) {
        return userWorkGridService.batchQueryUserWorkGridByGridKey(request);
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

    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridJsfServiceImpl.queryByUserIds", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<UserWorkGrid>> queryByUserIds(UserWorkGridBatchRequest request) {
        return userWorkGridService.queryByUserIds(request);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".UserWorkGridJsfServiceImpl.getWorkGridDistributedStaff", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<JyUserDto>> getWorkGridDistributedStaff(UserWorkGridRequest request) {
        return convertUResult(userWorkGridService.getWorkGridDistributedStaff(request));
    }

    private Result<List<JyUserDto>> convertUResult(Result<List<JyUser>> inputResult) {
        Result<List<JyUserDto>> result = Result.success();

        if (inputResult.isFail()) {
            return result.toFail(inputResult.getMessage());
        }
        List<JyUserDto> userDtos = new ArrayList<>();
        for (JyUser user : inputResult.getData()) {
            JyUserDto dto = convertUserDto(user);
            userDtos.add(dto);
        }
        result.setData(userDtos);
        return result;
    }

    private JyUserDto convertUserDto(JyUser user) {
        JyUserDto dto = new JyUserDto();
        BeanUtils.copyProperties(user, dto);
        UserJobTypeEnum userJobTypeEnum = UserJobTypeEnum.getJyJobCodeByNature(user.getNature());
        if (userJobTypeEnum != null) {
            dto.setJyJobCode(userJobTypeEnum.getJyJobTypeCode());
            return dto;
        }
        log.warn("获取工种枚举为空  入参{}", user.getNature());
        return dto;
    }

    @Override
    public Result<List<UserWorkGrid>> queryDeletedUserWorkGrid(UserWorkGridRequest request) {
        return userWorkGridService.queryDeletedUserWorkGrid(request);
    }

}
