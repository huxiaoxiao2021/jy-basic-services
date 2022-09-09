package com.jdl.basic.provider.core.dao.sendHandover;

import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverDetail;
import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverQuery;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * @author liwenji
 * @description TODO
 * @date 2022-09-07 21:13
 */
public interface SendTripartiteHandoverDetailDao {
    /**
     * 新增
     * @param detail
     * @return
     */
    int insert(SendTripartiteHandoverDetail detail);

    /**
     * 根据条件查询总数
     * @param query
     * @return
     */
    int queryCount(SendTripartiteHandoverQuery query);

    /**
     * 根据条件获取三方交接明细
     * @param query
     * @return
     */
    List<SendTripartiteHandoverDetail> getPageOrderByOperateTime(SendTripartiteHandoverQuery query);

    /**
     * 获取三方交接明细 limit 15
     * @param query
     * @return
     */
    List<SendTripartiteHandoverDetail> queryListOrderByOperateTime(SendTripartiteHandoverQuery query);
}
