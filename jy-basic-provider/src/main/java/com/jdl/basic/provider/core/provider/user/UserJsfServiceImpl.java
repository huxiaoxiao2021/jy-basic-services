package com.jdl.basic.provider.core.provider.user;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.*;
import com.jdl.basic.api.enums.UserJobTypeEnum;
import com.jdl.basic.api.service.user.UserJsfService;
import com.jdl.basic.api.utils.JyUserUtils;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.provider.JYBasicRpcException;
import com.jdl.basic.provider.core.service.jyJobType.JyJobTypeService;
import com.jdl.basic.provider.core.service.user.ThirdpartyUseService;
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
    @Autowired
    ThirdpartyUseService thirdpartyUseService;
    @Autowired
    JyJobTypeService jyJobTypeService;
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
        checkDistributedUserQueryDto(jyUserQueryDto);
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
                jyUsers =jyUsers.stream().map(jyUser -> assetWorkGridKey(jyUser,result.getData())).filter(jyUser -> filterGridAndJobType(jyUser,jyUserQueryDto)).collect(Collectors.toList());
                rs.setData(jyUsers);
            }
            return rs;
        }
        return Result.success();
    }

    private void checkDistributedUserQueryDto(JyUserQueryDto jyUserQueryDto) {
        if (!ObjectHelper.isNotNull(jyUserQueryDto.getSiteCode())){
            throw new JYBasicRpcException("参数错误：缺失场地编码！");
        }
        if (!ObjectHelper.isNotNull(jyUserQueryDto.getJobType())){
            throw new JYBasicRpcException("参数错误：确实用工种类编码！");
        }
    }

    private  Boolean filterGridAndJobType(JyUser jyUser,JyUserQueryDto jyUserQueryDto) {
        if (ObjectHelper.isNotNull(jyUser.getWorkGridKey()) && ObjectHelper.isNotNull(UserJobTypeEnum.getJyJobEnumByNature(jyUser.getNature())) && jyUserQueryDto.getJobType().equals(UserJobTypeEnum.getJyJobEnumByNature(jyUser.getNature()).getJyJobTypeCode())){
            return true;
        }
        return false;
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

    @Override
    public Result<Boolean> checkUserBelongToJy(JyUserQueryDto dto) {
        return userService.checkUserBelongToJy(dto);
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

    @Override
    public JyUser queryUserInfo(JyUser condition) {
        return userService.queryUserInfo(condition);
    }

    @Override
    public Result<List<JyJobType>> getAllJobTypeList() {
        List<JyJobType> jobTypeList = new ArrayList<>();
        for (com.jdl.basic.api.domain.jyJobType.JyJobType jyJobType : jyJobTypeService.queryALlList()) {
            JyJobType jobType = new JyJobType();
            jobType.setCode(jyJobType.getCode());
            jobType.setName(jyJobType.getName());
            jobTypeList.add(jobType);
        }
        return Result.success(jobTypeList);
    }

    @Override
    public Result saveJyThirdpartyUser(JyThirdpartyUserSaveDto dto) {
        int rs =thirdpartyUseService.batchInsert(dto.getJyThirdpartyUserList());
        if (rs >0){
            return Result.success();
        }
        return Result.fail("三方人员信息保存失败！");
    }

    @Override
    public Result addJyThirdpartyUserOne(JyThirdpartyUser jyThirdpartyUser) {
        int rs =thirdpartyUseService.add(jyThirdpartyUser);
        if (rs >0){
            return Result.success();
        }
        return Result.fail("三方人员信息保存失败！");
    }

    @Override
    public Result<PageData<JyThirdpartyUser>> queryJyThirdpartyUserUnderTask(JyThirdpartyUserQueryDto dto) {
        checkJyThirdpartyUserQueryDto(dto);
        List<JyThirdpartyUser> jyThirdpartyUserList;

        Page page =PageHelper.startPage(dto.getPageNo(),dto.getPageSize());
        if (ObjectHelper.isNotNull(dto.getTaskDetailBizId())){
            jyThirdpartyUserList =thirdpartyUseService.queryJyThirdpartyUserByDetailBizId(dto.getTaskDetailBizId());
        }else {
            jyThirdpartyUserList =thirdpartyUseService.queryJyThirdpartyUserByTaskBizId(dto.getTaskBizId());
        }

        if (CollectionUtils.isNotEmpty(jyThirdpartyUserList)){
            PageData pageData =new PageData();
            pageData.setPageNumber(dto.getPageNo());
            pageData.setPageSize(dto.getPageSize());
            pageData.setTotal(page.getTotal());
            pageData.setRecords(jyThirdpartyUserList);
            return Result.success(pageData);
        }
        return Result.success();
    }

    @Override
    public Result<List<JyThirdpartyUser>> queryJyThirdpartyUser(JyTpUserScheduleQueryDto jyTpUserScheduleQueryDto) {
        return Result.success(userService.queryJyThirdpartyUser(jyTpUserScheduleQueryDto));
    }

    @Override
    public Result<JyThirdpartyUser> queryTpUserReserveInfo(JyTpUserScheduleQueryDto jyTpUserScheduleQueryDto) {
        return Result.success(thirdpartyUseService.queryTpUserReserveInfo(jyTpUserScheduleQueryDto));
    }

    @Override
    public Result<List<JyUserDto>> querySiteLeader(RoleQueryDto roleQueryDto) {
        return Result.success(userService.queryUserByPositionCode(roleQueryDto));
    }

    @Override
    public Result<List<JyThirdpartyUser>> queryJyThirdpartyUserByCondition(JyThirdpartyUser jyThirdpartyUser) {
        return Result.success(thirdpartyUseService.queryJyThirdpartyUserByCondition(jyThirdpartyUser));
    }

    @Override
    public Result updateJyThirdpartyUserYn(JyThirdpartyUser jyThirdpartyUser) {
        int rs =thirdpartyUseService.updateJyThirdpartyUserYn(jyThirdpartyUser);
        if (rs >0){
            return Result.success();
        }
        return Result.fail("更新JyThirdpartyUser删除状态失败！");
    }

    @Override
    public Result<Long> countTpUserByTaskDetail(JyThirdpartyUser jyThirdpartyUser) {
        return Result.success(thirdpartyUseService.countTpUserByTaskDetail(jyThirdpartyUser));
    }

    @Override
    public Result<List<ReserveTaskDetailAgg>> countTpUserGroupByNature(ReserveTaskDetailAggQuery query) {
        return Result.success(thirdpartyUseService.countTpUserGroupByNature(query));
    }

    private void checkJyThirdpartyUserQueryDto(JyThirdpartyUserQueryDto dto) {
    }

    @Override
    public Result<JyThirdpartyUser> queryTpUserByUserCode(JyThirdpartyUser jyThirdpartyUser) {
        checkQueryTpUserByUserCodeDto(jyThirdpartyUser);
        return Result.success(thirdpartyUseService.queryTpUserByUserCode(jyThirdpartyUser));
    }

    private void checkQueryTpUserByUserCodeDto(JyThirdpartyUser jyThirdpartyUser) {
        if (ObjectHelper.isEmpty(jyThirdpartyUser.getUserCode())){
            throw new JYBasicRpcException("参数错误：缺失身份证号码！");
        }
        if (ObjectHelper.isEmpty(jyThirdpartyUser.getSiteCode())){
            throw new JYBasicRpcException("参数错误：缺失场地编码！");
        }
        if (ObjectHelper.isEmpty(jyThirdpartyUser.getDeadlineTime())){
            throw new JYBasicRpcException("参数错误：缺失查询日期！");
        }
    }

    @Override
    public Result<JyUserDto> getUserByErpOrIdNum(JyUserQueryDto queryDto) {
        if (JyUserUtils.isIdCard(queryDto.getUserUniqueCode())) {
            JyThirdpartyUser thirdpartyUser = thirdpartyUseService.getUserByIdCarNum(queryDto);
            return Result.success(convertJyUserDto(thirdpartyUser));
        }

        JyUser condition = new JyUser();
        condition.setUserErp(queryDto.getUserUniqueCode());
        JyUser user = userService.queryUserInfo(condition);
        return Result.success(convertUserDto(user));
    }

    private JyUserDto convertJyUserDto(JyThirdpartyUser thirdpartyUser) {
        if (thirdpartyUser == null) {
            return null;
        }
        JyUserDto dto = new JyUserDto();
        dto.setUserCode(thirdpartyUser.getUserCode());
        dto.setUserName(thirdpartyUser.getUserName());
        dto.setNature(thirdpartyUser.getNature());
        dto.setSiteCode(thirdpartyUser.getSiteCode());
        return dto;
    }
}
