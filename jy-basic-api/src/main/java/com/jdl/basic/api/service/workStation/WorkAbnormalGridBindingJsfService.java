package com.jdl.basic.api.service.workStation;


import com.jdl.basic.api.domain.workStation.*;
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
     * 查询数据总数
     * @param query
     * @return
     */
    Result<Long> queryDistinctCount(WorkStationFloorGridQuery query);

    /**
     * 根据场地查询信息
     * @param query
     * @return
     */
    Result<WorkStationBindingEditVo> queryListBySite(WorkStationFloorGridQuery query);
    /**
     * 获取异常网格
     * @param query
     * @return
     */
    Result<WorkStationFloorGridVo> getAbnormalGrid(WorkStationFloorGridQuery query);

    /**
     * 插入一条数据
     * @param insertData
     * @return
     */
    Result<Boolean> insert(List<WorkStationBinding> insertData);

    /**
     * 查询绑定数据
     * @param query
     * @return
     */
    Result<List<WorkStationBindingVo>> queryBindingList(WorkStationFloorGridQuery query);

    /**
     * 更新数据
     */
    Result<Boolean> update(List<WorkStationBinding> data);

    /**
     * 导出数据
     * @param query
     * @return
     */
    Result<List<WorkStationFloorGridVo>> queryListForExport(WorkStationFloorGridQuery query);

}
