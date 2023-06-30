package com.jdl.basic.provider.core.provider.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.domain.user.JyUserBatchRequest;
import com.jdl.basic.api.domain.user.JyUserQueryDto;
import com.jdl.basic.api.domain.user.UnDistributedUserQueryDto;
import com.jdl.basic.api.service.user.UserJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.provider.core.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("userJsfServiceImpl")
public class UserJsfServiceImpl implements UserJsfService {
    @Autowired
    private UserService userService;
    @Override
    public Result<List<JyUser>> searchUserBySiteCode(JyUserQueryDto dto) {
        return userService.searchUserBySiteCode(dto);
    }

    @Override
    public Result<List<JyUser>> queryByUserIds(JyUserBatchRequest request) {
        return userService.queryByUserIds(request);
    }

    @Override
    public Result<Integer> queryUndistributedCountBySiteCode(JyUserQueryDto dto) {
        return userService.queryUndistributedCountBySiteCode(dto);
    }

  @Override
  public Result<List<JyUser>> queryDifference(JyUserQueryDto dto) {
    return userService.queryDifference(dto);
  }

    @Override
    public Result<List<JyUser>> batchQueryQuitUserByUserId(JyUserBatchRequest request) {
        return userService.batchQueryQuitUserByUserId(request);
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

  private void checkUnDistributedUserQueryDto(UnDistributedUserQueryDto dto) {
    if (ObjectHelper.isEmpty(dto.getPageNo())) {
      dto.setPageNo(Constants.DEFAULT_PAGE_NO);
    }
    if (ObjectHelper.isEmpty(dto.getPageSize())){
      dto.setPageSize(Constants.DEFAULT_PAGE_SIZE_QUERY_USER);
    }
  }

	@Override
	public Result<List<JyUser>> queryUserListBySiteAndPosition(JyUserQueryDto dto) {
		return userService.queryUserListBySiteAndPosition(dto);
	}

    @Override
    public Result<List<JyUser>> queryNatureUndistributedUsers(JyUserQueryDto dto) {
        return userService.queryNatureUndistributedUsers(dto);
    }
}
