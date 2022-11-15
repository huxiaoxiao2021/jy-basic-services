package com.jdl.basic.provider.core.service.cross;

import com.jdl.basic.api.domain.cross.SortCrossDetail;
import com.jdl.basic.api.domain.cross.SortCrossQuery;
import com.jdl.basic.api.domain.cross.SortCrossUpdateRequest;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * @author liwenji
 * @date 2022-11-09 10:47
 */
public interface SortCrossService {

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageDto<SortCrossDetail> queryPage(SortCrossQuery query);

    /**
     * 根据id批量更新
     * @param request
     * @return
     */
    boolean updateEnableByIds(SortCrossUpdateRequest request);

    /**
     * 查询没有初始化的数据
     * @param queryLimit
     * @return
     */
    List<SortCrossDetail> queryNotInit(Integer queryLimit);

    /**
     * 初始化数据
     * @param sortCrossDetail
     * @return
     */
    int initSiteTypeById(SortCrossDetail sortCrossDetail);
}
