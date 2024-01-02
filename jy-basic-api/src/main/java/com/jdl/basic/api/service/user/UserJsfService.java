package com.jdl.basic.api.service.user;

import com.jd.dms.java.utils.sdk.base.PageData;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.*;

import java.util.List;

public interface UserJsfService {
    /**
     * 查询场地下的所有员工
     * 工种类型可选
     * @param dto
     * @return
     */
    Result<List<JyUserDto>> searchUserBySiteCode(JyUserQueryDto dto);

    /**
     * 根据id批量查询员工
     * @param request
     * @return
     */
    Result<List<JyUserDto>> queryByUserIds(JyUserBatchRequest request);


    Result<JyUserDto> queryByUserErp(JyUserQueryDto jyUserQueryDto);
    /**
     * 查询场地下未分配员工数量
     * @param dto
     * @return
     */
    Result<Integer> queryUndistributedCountBySiteCode(JyUserQueryDto dto);

    /**
     * 查询给定时间范围入职跟离职的员工
     * @param dto
     * @return
     */
    Result<UserChangeDto> queryDifference(JyUserQueryDto dto);

    /**
     * 根据id批量查询离职员工
     * @param request
     * @return
     */
    Result<List<JyUserDto>> batchQueryQuitUserByUserId(JyUserBatchRequest request);

    /**
     * 查询未分配到网格的人员列表
     * @param unDistributedUserQueryDto
     * @return
     */
    Result<List<JyUser>> queryUnDistributedUserList(UnDistributedUserQueryDto unDistributedUserQueryDto);

    /**
     * 查询场地已分配网格人员
     * @param jyUserQueryDto
     * @return
     */
    Result<List<JyUser>> queryDistributedUserList(JyUserQueryDto jyUserQueryDto);

    /**
     * 查询人员岗位下列表
     * @param dto
     * @return
     */
    Result<List<JyUserDto>> queryUserListBySiteAndPosition(JyUserQueryDto dto);

    /**
     * 查询某工种未分配网格的员工
     * @param dto
     * @return
     */
    Result<List<JyUserDto>> queryNatureUndistributedUsers(JyUserQueryDto dto);

    /**
     * 判断当前登录人是否是拣运人员
     * @param dto
     * @return
     */
    Result<Boolean> checkUserBelongToJy(JyUserQueryDto dto);

    JyUser queryUserInfo(JyUser condition);

    Result<List<JyJobType>> getAllJobTypeList();

    /**
     * 保存三方人员信息
     * @param dto
     * @return
     */
    Result saveJyThirdpartyUser(JyThirdpartyUserSaveDto dto);


    Result addJyThirdpartyUserOne(JyThirdpartyUser jyThirdpartyUser);

    /**
     * 查询任务/任务明细下的三方人员信息
     * @param dto
     * @return
     */
    Result<PageData<JyThirdpartyUser>> queryJyThirdpartyUserUnderTask(JyThirdpartyUserQueryDto dto);


    /**
     * 查询某日某场地某工种的三方人员列表(查询条件 日期  场地 工种)
     */
    Result<List<JyThirdpartyUser>> queryJyThirdpartyUser(JyTpUserScheduleQueryDto jyTpUserScheduleQueryDto);


    /**
     * 查询场地负责人
     * @param roleQueryDto
     * @return
     */
    Result<List<JyUserDto>> querySiteLeader(RoleQueryDto roleQueryDto);

    Result<List<JyThirdpartyUser>> queryJyThirdpartyUserByCondition(JyThirdpartyUser jyThirdpartyUser);

    Result updateJyThirdpartyUserYn(JyThirdpartyUser jyThirdpartyUser);


    /**
     * 查询某个场地储备任务的人员数量
     * @param JyThirdpartyUser
     * @return
     */
    Result<Long> countTpUserByTaskDetail(JyThirdpartyUser JyThirdpartyUser);


    //查询多个 储备任务明细的 人员数量
    Result<List<ReserveTaskDetailAgg>> countTpUserGroupByNature(ReserveTaskDetailAggQuery query);


    Result<JyThirdpartyUser> queryTpUserByUserCode(JyThirdpartyUser jyThirdpartyUser);

}
