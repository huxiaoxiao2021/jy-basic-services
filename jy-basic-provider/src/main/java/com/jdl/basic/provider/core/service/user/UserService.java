package com.jdl.basic.provider.core.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.*;

import java.util.List;

public interface UserService {

  /**
   * 查询用户信息
   */
  JyUser queryUserInfo(JyUser condition);

  /**
   * 保存用户信息
   * @param jyUser
   * @return
   */
  int saveUser(JyUser jyUser);

  /**
   * 根据主键更新用户信息
   * @param jyUser
   * @return
   */
  int updateUser(JyUser jyUser);

  /**
   * 根据条件查询员工
   * @param siteCode
   * @return
   */
  Result<List<JyUser>> searchUserBySiteCode(Integer siteCode);

  /**
   * 根据员工id批量查询员工
   * @param request
   * @return
   */
  Result<List<JyUser>> queryByUserIds(JyUserBatchRequest request);

  /**
   * 根据用户id批量修改
   * @param request
   * @return
   */
  Result<Boolean> batchUpdateByUserIds(JyUserBatchRequest request);

  /**
   * 查询场地下未分配人员数量
   * @param dto
   * @return
   */
  Result<Integer> queryUndistributedCountBySiteCode(JyUserQueryDto dto);

  /**
   * 查询场地下入职时间或离职时间大于给定时间的用户数
   * @param dto
   * @return
   */
  Result<UserChangeDto> queryDifference(JyUserQueryDto dto);

  /**
   * 从给定的id中筛选出已离职的
   * @param request
   * @return
   */
  Result<List<JyUser>> batchQueryQuitUserByUserId(JyUserBatchRequest request);

  List<JyUser> queryUnDistributedUserList(UnDistributedUserQueryDto dto);
  /**
   * 查询人员岗位下列表
   * @param dto
   * @return
   */
  Result<List<JyUser>> queryUserListBySiteAndPosition(JyUserQueryDto dto);

  /**
   * 查询网格下某工种未分配员工
   * @param dto
   * @return
   */
  Result<List<JyUser>> queryNatureUndistributedUsers(JyUserQueryDto dto);

  /**
   * 校验员工是否属于拣运
   * @param dto
   * @return
   */
  Result<Boolean> checkUserBelongToJy(JyUserQueryDto dto);
  JyUserDto queryByUserErp(JyUserQueryDto jyUserQueryDto);
}
