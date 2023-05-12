package com.jdl.basic.provider.core.dao.cross;

import com.jdl.basic.api.domain.cross.*;

import java.util.List;

public interface SortCrossDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(SortCrossDetail record);

    int insertSelective(SortCrossDetail record);

    SortCrossDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SortCrossDetail record);

    int updateByPrimaryKey(SortCrossDetail record);

    /**
     * 分页查询
     * @param query
     * @return
     */
    List<SortCrossDetail> queryPage(SortCrossQuery query);

    /**
     * 获取总数
     * @param query
     * @return
     */
    long queryCount(SortCrossQuery query);

    /**
     * 根据id批量更新状态
     * @param request
     * @return
     */
    int updateEnableByIds(SortCrossUpdateRequest request);

    /**
     * 查询未初始化的信息
     * @return
     * @param queryLimit
     * @param limit
     */
    List<SortCrossDetail> queryNotInit(Integer dmsId);

    /**
     * 通过Id更新数据
     * @param sortCrossDetail
     * @return
     */
    int updateById(SortCrossDetail sortCrossDetail);

    /**
     * 分页查询场地滑道信息
     * @param query
     * @return
     */
    List<String> queryCrossDataByDmsCode(CrossPageQuery query);

    /**
     * 查询场地滑道总数
     * @param query
     * @return
     */
    Long queryCrossDataCount(CrossPageQuery query);

    /**
     * 查询滑道被启用的笼车总数
     * @param query
     * @return
     */
    Long queryTableTrolleyCount(TableTrolleyQuery query);
    /**
     * 分页查询滑道被启用的笼车
     * @param query
     * @return
     */
    List<TableTrolleyJsfDto> queryTableTrolleyList(TableTrolleyQuery query);

    /**
     * 根据始发目的地获取滑道笼车信息
     * @return
     */
    List<TableTrolleyJsfDto> queryTableTrolley(TableTrolleyQuery query);

    /**
     * 根据场地流向查询滑道笼车信息
     * @param tableTrolleyQuery
     * @return
     */
    List<TableTrolleyJsfDto> queryCrossCodeTableTrolleyBySiteFlow(TableTrolleyQuery tableTrolleyQuery);
    /**
     * 根据滑道笼车信息查询场地流向
     */
    List<TableTrolleyJsfDto> querySiteFlowByCrossCodeTableTrolley(TableTrolleyQuery tableTrolleyQuery);
}
