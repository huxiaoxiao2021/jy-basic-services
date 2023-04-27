package com.jdl.basic.provider.core.service.workStation;

import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOffline;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOfflineQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

/**
 * 三定网格流向明细离线统计接口--Service接口
 */
public interface WorkGridFlowDetailOfflineService {

    /**
     * 按条件分页查询
     * @param query
     * @return
     */
    Result<PageDto<WorkGridFlowDetailOffline>> queryPageList(WorkGridFlowDetailOfflineQuery query);
}
