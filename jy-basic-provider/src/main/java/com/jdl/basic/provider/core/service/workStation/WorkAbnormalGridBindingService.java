package com.jdl.basic.provider.core.service.workStation;


import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * @author liwenji
 * @description 网格异常关联--Service接口
 * @date 2022-08-10 19:30
 */
public interface WorkAbnormalGridBindingService {
    /**
     * 按条件分页查询报表
     * @param query
     * @return
     */
    Result<PageDto<WorkStationFloorGridVo>> queryListDistinct(WorkStationFloorGridQuery query);

    Result<Long> queryDistinctCount(WorkStationFloorGridQuery query);
    /**
     * 查询场地信息
     * @param query
     * @return
     */
    Result<WorkStationBindingEditVo> queryListBySite(WorkStationFloorGridQuery query);

    /**
     * 插入一条数据
     * @param insertData
     * @return
     */
    Result<Boolean> insert(WorkStationBinding insertData);

    /**
     * 查询绑定数据
     * @param query
     * @return
     */
    Result<List<WorkStationBindingVo>> queryBindingList(WorkStationFloorGridQuery query);

    /**
     * 更新绑定信息
     * @param data
     * @return
     */
    Result<Boolean> update(List<WorkStationBinding> data);

    /**
     * 获取异常网格
     * @param query
     * @return
     */
    WorkStationFloorGridVo getAbnormalGrid(WorkStationFloorGridQuery query);

    /**
     * 同步删除关联关系
     * @param deleteData
     * @return
     */
    void deleteByGrid(WorkStationBinding deleteData);
    

    Result<List<WorkStationFloorGridVo>> queryListForExport(WorkStationFloorGridQuery query);
}
