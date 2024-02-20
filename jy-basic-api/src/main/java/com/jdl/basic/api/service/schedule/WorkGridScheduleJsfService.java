package com.jdl.basic.api.service.schedule;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.schedule.*;

import java.util.List;

public interface WorkGridScheduleJsfService {

    /**
     * 根据网格key批量查询网格的所有班次时间
     * @param request
     * @return
     */
    Result<List<WorkGridSchedule>> batchQueryByWorkGridKey(WorkGridScheduleBatchRequest request);

    /**
     * 根据网格key批量删除网格的所有班次时间
     * @param request
     * @return
     */
    @Deprecated
    Result<Boolean> batchDeleteByWorkGridKey(WorkGridScheduleBatchRequest request);

    /**
     * 批量插入班次时间
     * @param request
     * @return
     */
    Result<Boolean> batchInsert(WorkGridScheduleBatchRequest request);

    /**
     * 批量修改班次时间
     * @param request
     * @return
     */
    Result<Boolean> batchUpdateWorkGridSchedule(WorkGridScheduleBatchUpdateRequest request);

    /**
     * 根据条件修改场地下的班次时间
     * @param request
     * @return
     */
    Result<List<WorkGridSchedule>> querySiteScheduleByCondition(WorkGridScheduleRequest request);

    /**
     * 根据班次key查询班次
     * @param request
     * @return
     */
    Result<WorkGridSchedule> queryWorkGridScheduleByKey(WorkGridScheduleRequest request);

    /**
     * 根据班次key批量查询班次
     * @param dto
     * @return
     */
    Result<List<WorkGridSchedule>> listWorkGridScheduleByKeys(BatchWorkGridScheduleQueryDto dto);

    /**
     * 根据网格和班次名称查询班次
     * @param request
     * @return
     */
    Result<WorkGridSchedule> queryWorkGridScheduleByName(WorkGridScheduleRequest request);

    /**
     * 清空旧班次时间
     * @param request
     * @return
     */
    Result<Boolean> cleanWorkGridScheduleOldTime(BatchCleanOldTimeRequest request);

    /**
     * 查询网格今天被删除的班次
     * @param request
     * @return
     */
    Result<List<WorkGridSchedule>> queryTodayDeletedSiteSchedule(WorkGridScheduleRequest request);

    /**
     * 根据提供的时间查询所提供时间内的有效班次时间
     * @param request
     * @return
     */
    Result<List<ScheduleValidTimeDto>> listValidCutWorkGridScheduleByTime(ValidWorkGridScheduleRequest request);
}
