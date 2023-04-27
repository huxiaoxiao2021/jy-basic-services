package com.jdl.basic.provider.core.dao.workStation;

import com.jdl.basic.api.domain.workStation.*;

import java.util.List;


/**
 * 网格流向明细离线统计表--Dao接口
 */
public interface WorkGridFlowDetailOfflineDao {


    Long queryCount(WorkGridFlowDetailOfflineQuery query);

    List<WorkGridFlowDetailOffline> queryList(WorkGridFlowDetailOfflineQuery query);

}
