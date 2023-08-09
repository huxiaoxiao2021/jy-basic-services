package com.jdl.basic.provider.core.dao.workStation;

import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOffline;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOfflineQuery;

import java.util.List;


/**
 * 网格流向明细离线统计表--Dao接口
 */
public interface WorkGridFlowDetailOfflineDao {


    Long queryCount(WorkGridFlowDetailOfflineQuery query);

    List<WorkGridFlowDetailOffline> queryList(WorkGridFlowDetailOfflineQuery query);
    
    int updateRefWorkGridKeyById(WorkGridFlowDetailOffline updateData);

    /**
     * 刷数-分页查询
     * @param startId
     * @return
     */
    List<WorkGridFlowDetailOffline> brushQueryAllByPage(Integer startId);

    /**
     * 刷数-批量更新
     * @param list
     * @return
     */
    Integer brushUpdateById(List<WorkGridFlowDetailOffline> list);

}
