package com.jdl.basic.api.service.workStation;


import com.jdl.basic.api.domain.workStation.WorkStationFloorGridQuery;
import com.jdl.basic.api.domain.workStation.WorkStationFloorGridVo;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * @author liwenji
 * @description 网格异常关联--JsfService接口
 * @date 2022-08-10 18:23
 */
public interface WorkAbnormalGridBindingJsfService {
    /**
     * 按条件分页查询报表
     * @param query
     * @return
     */
    Result<PageDto<WorkStationFloorGridVo>> queryListDistinct(WorkStationFloorGridQuery query);
    /**
     * 查询场地信息
     * @param query
     * @return
     */
    Result<List<WorkStationFloorGridVo>> queryListBySite(WorkStationFloorGridQuery query);
}
