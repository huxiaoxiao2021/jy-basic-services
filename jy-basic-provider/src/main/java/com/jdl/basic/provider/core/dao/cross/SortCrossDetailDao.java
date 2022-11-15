package com.jdl.basic.provider.core.dao.cross;

import com.jdl.basic.api.domain.cross.SortCrossDetail;
import com.jdl.basic.api.domain.cross.SortCrossQuery;
import com.jdl.basic.api.domain.cross.SortCrossUpdateRequest;

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
     */
    List<SortCrossDetail> queryNotInit(Integer queryLimit);

    /**
     * 通过Id更新数据
     * @param sortCrossDetail
     * @return
     */
    int updateById(SortCrossDetail sortCrossDetail);
}
