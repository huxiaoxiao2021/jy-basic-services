package com.jdl.basic.provider.core.service.cross;

import com.jdl.basic.api.domain.cross.*;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.provider.dto.SortCrossModifyDto;

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

    /**
     * 分页查询场地滑道列表数据
     * @param query
     * @return
     */
    CrossDataJsfResp queryCrossDataByDmsCode(CrossPageQuery query);

    /**
     * 通过道口号分页查询笼车编号
     * @param query
     * @return
     */
    TableTrolleyJsfResp queryTableTrolleyListByCrossCode(TableTrolleyQuery query);
    

    /**
     * 根据条件查询滑道笼车流向信息
     * @param query
     * @return
     */
    TableTrolleyJsfResp queryTableTrolley(TableTrolleyQuery query);

    /**
     * 同步滑道笼车配置信息
     * @param sortCrossModifyDto
     * @return
     */
    boolean syncSortCross(SortCrossModifyDto sortCrossModifyDto);    
}
