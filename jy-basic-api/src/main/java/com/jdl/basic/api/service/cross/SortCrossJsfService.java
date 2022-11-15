package com.jdl.basic.api.service.cross;

import com.jdl.basic.api.domain.cross.SortCrossDetail;
import com.jdl.basic.api.domain.cross.SortCrossQuery;
import com.jdl.basic.api.domain.cross.SortCrossUpdateRequest;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * @author liwenji
 * @date 2022-11-09 10:43
 */
public interface SortCrossJsfService {

    /**
     * 分页查询
     * @param query
     * @return
     */
    Result<PageDto<SortCrossDetail>> queryPage(SortCrossQuery query);

    /**
     * 根据id批量修改启用状态
     * @param request
     * @return
     */
    Result<Boolean> updateEnableByIds(SortCrossUpdateRequest request);

    /**
     * 初始化网点数据
     * @return
     */
    Result<Boolean> initSortCross();
    
}
