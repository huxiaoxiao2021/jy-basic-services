package com.jdl.basic.provider.core.provider.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.*;
import com.jdl.basic.api.enums.UserJobTypeEnum;
import com.jdl.basic.api.service.user.UserJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.provider.core.service.user.UserService;
import java.util.Date;

import com.jdl.basic.provider.core.service.user.UserWorkGridService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("userJsfServiceImpl")
public class UserJsfServiceImpl implements UserJsfService {
    @Autowired
    private UserService userService;
    @Autowired
    UserWorkGridService userWorkGridService;
    @Override
    public Result<List<JyUserDto>> searchUserBySiteCode(JyUserQueryDto dto) {
        return convertToResult(userService.searchUserBySiteCode(dto.getSiteCode()));
    }

    @Override
    public Result<List<JyUserDto>> queryByUserIds(JyUserBatchRequest request) {
        return convertToResult(userService.queryByUserIds(request));
    }

  @Override
  public Result<JyUserDto> queryByUserErp(JyUserQueryDto jyUserQueryDto) {
    return Result.success(userService.queryByUserErp(jyUserQueryDto));
  }

  @Override
    public Result<Integer> queryUndistributedCountBySiteCode(JyUserQueryDto dto) {
        return userService.queryUndistributedCountBySiteCode(dto);
    }

  @Override
  public Result<UserChangeDto> queryDifference(JyUserQueryDto dto) {
    return userService.queryDifference(dto);
  }

    @Override
    public Result<List<JyUserDto>> batchQueryQuitUserByUserId(JyUserBatchRequest request) {
        return convertToResult(userService.batchQueryQuitUserByUserId(request));
    }
  @Override
  public Result<List<JyUser>> queryUnDistributedUserList(UnDistributedUserQueryDto dto) {
    checkUnDistributedUserQueryDto(dto);
    //PageHelper.startPage(dto.getPageNo(),dto.getPageSize());
    List<JyUser> jyUserList = userService.queryUnDistributedUserList(dto);
    if (CollectionUtils.isNotEmpty(jyUserList)){
      return Result.success(jyUserList);
    }
    return Result.fail("未查询到相关的用户信息");
  }


    @Override
    public Result<List<JyUser>> queryDistributedUserList(JyUserQueryDto jyUserQueryDto) {
        //查询场地人员列表
        Result<List<JyUser>> rs =userService.searchUserBySiteCode(jyUserQueryDto.getSiteCode());
        if (ObjectHelper.isNotNull(rs) && rs.isSuccess() && CollectionUtils.isNotEmpty(rs.getData())){
            List<JyUser> jyUsers =rs.getData();
            List<UserWorkGrid> userWorkGrids =jyUsers.stream().map(jyUser ->
            {
                UserWorkGrid userWorkGrid =new UserWorkGrid();
                userWorkGrid.setUserId(jyUser.getId());
                return userWorkGrid;
            }).collect(Collectors.toList());
            UserWorkGridBatchRequest userWorkGridBatchRequest = new UserWorkGridBatchRequest();
            userWorkGridBatchRequest.setUserWorkGrids(userWorkGrids);
            Result<List<UserWorkGrid>> result =userWorkGridService.queryByUserIds(userWorkGridBatchRequest);
            if (ObjectHelper.isNotNull(result) && result.isSuccess() && CollectionUtils.isNotEmpty(result.getData())){
                jyUsers =jyUsers.stream().map(jyUser -> assetWorkGridKey(jyUser,result.getData())).filter(jyUser -> ObjectHelper.isNotNull(jyUser.getWorkGridKey())).collect(Collectors.toList());
            }
            return rs;
        }
        return Result.success();
    }

    private JyUser assetWorkGridKey(JyUser jyUser, List<UserWorkGrid> userWorkGrids) {
        for (UserWorkGrid userWorkGrid :userWorkGrids){
            if (userWorkGrid.getUserId().equals(jyUser.getId())){
                jyUser.setWorkGridKey(userWorkGrid.getWorkGridKey());
                break;
            }
        }
        return jyUser;
    }


    private void checkUnDistributedUserQueryDto(UnDistributedUserQueryDto dto) {
    if (ObjectHelper.isEmpty(dto.getPageNo())) {
      dto.setPageNo(Constants.DEFAULT_PAGE_NO);
    }
    if (ObjectHelper.isEmpty(dto.getPageSize())){
      dto.setPageSize(Constants.DEFAULT_PAGE_SIZE_QUERY_USER);
    }
    if (ObjectHelper.isEmpty(dto.getCheckDay())){
      dto.setCheckDay(DateHelper.getDateOfyyMMdd2(DateHelper.addDays(new Date(),Constants.USER_SIGN_CHECK_DAYS)));
    }
  }

	@Override
	public Result<List<JyUserDto>> queryUserListBySiteAndPosition(JyUserQueryDto dto) {
		return convertToResult(userService.queryUserListBySiteAndPosition(dto));
	}

    @Override
    public Result<List<JyUserDto>> queryNatureUndistributedUsers(JyUserQueryDto dto) {
        return convertToResult(userService.queryNatureUndistributedUsers(dto));
    }

    private Result<List<JyUserDto>> convertToResult(Result<List<JyUser>> inputResult) {
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
        UserJobTypeEnum userJobTypeEnum = UserJobTypeEnum.getJyJobEnumByNature(user.getNature());
        if (userJobTypeEnum != null) {
            dto.setNature(String.valueOf(userJobTypeEnum.getJyJobTypeCode()));
            return dto;
        }
        log.warn("获取工种枚举为空  入参{}", user.getNature());
        return dto;
    }
}
