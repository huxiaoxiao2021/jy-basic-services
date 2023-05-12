package com.jdl.basic.api.service.cross;

import com.jdl.basic.api.domain.cross.*;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

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
     * @param siteCode
     */
    Result<Boolean> initSortCross(Integer siteCode);

    /**
     * 分页查询场地滑道列表数据
     * @param query
     * @return
     */
    Result<CrossDataJsfResp> queryCrossDataByDmsCode(CrossPageQuery query);

    /**
     * 通过道口号分页查询笼车编号
     * @param query
     * @return
     */
    Result<TableTrolleyJsfResp> queryTableTrolleyListByCrossCode(TableTrolleyQuery query);

    /**
     * 通过场地分页查询笼车编号
     * @param query
     * @return
     */
    Result<TableTrolleyJsfResp> queryTableTrolleyListByDmsId(TableTrolleyQuery query);

    /**
     * 根据始发和目的地站点查询滑道笼车
     * @param query
     * @return
     */
    Result<TableTrolleyJsfResp> queryCTTByStartEndSiteCode(TableTrolleyQuery query);

    /**
     * 根据滑道笼车号获取流向信息
     * @param query
     * @return
     */
    Result<TableTrolleyJsfResp> queryCTTByCTTCode(TableTrolleyQuery query);

    /**
     * 初始化滑道笼车信息
     * @param detail
     * @return
     */
    Boolean initSiteType(SortCrossDetail detail);

    /**
     * 根据场地流向查询滑道笼车信息
     */
    Result<TableTrolleyJsfResp>  queryCrossCodeTableTrolleyBySiteFlow(TableTrolleyQuery tableTrolleyQuery);

}
