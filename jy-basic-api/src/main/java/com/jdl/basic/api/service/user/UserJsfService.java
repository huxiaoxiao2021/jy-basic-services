package com.jdl.basic.api.service.user;

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
    Result<List<JyUserDto>> queryDifference(JyUserQueryDto dto);

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

}
