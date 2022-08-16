package com.jdl.basic.provider.core.dao.workStation;

import com.jdl.basic.api.domain.workStation.WorkStationBinding;
import com.jdl.basic.api.domain.workStation.WorkStationBindingVo;
import com.jdl.basic.api.domain.workStation.WorkStationFloorGridQuery;
import com.jdl.basic.api.domain.workStation.WorkStationFloorGridVo;

import java.util.List;

/**
 * @author liwenji
 * @description 异常网格绑定 dao接口
 * @date 2022-08-14 7:54
 */
public interface WorkAbnormalGridDao {
    /**
     * 插入一条数据
     * @param insertData
     * @return
     */
    int insert(WorkStationBinding insertData);

    /**
     * 查询是否存在该条数据
     */
    long queryOne(WorkStationBinding query);

    /**
     * 查询绑定关系
     * @param query
     * @return
     */
    List<WorkStationBinding> queryBindingList(WorkStationFloorGridQuery query);

    /**
     * 更新绑定关系
     * @param data
     */
    void updateByGridCode(WorkStationBinding data);

    void update(WorkStationBinding insertData);
    List<Integer> queryFloor(WorkStationFloorGridQuery query);

    WorkStationFloorGridVo getAbnormalGrid(WorkStationFloorGridQuery query);

}
