package com.jdl.basic.provider.core.dao.sendHandover;

import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverDetail;
import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverQuery;
import com.jdl.basic.api.domain.sendHandover.UpdateRequest;
import com.jdl.basic.api.domain.workStation.DeleteRequest;

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
     * @param siteCode
     * @return
     */
    List<SendTripartiteHandoverDetail> queryListOrderByOperateTime(SendTripartiteHandoverQuery siteCode);

    /**
     * 导出
     * @param query
     * @return
     */
    List<SendTripartiteHandoverDetail> getListForExport(SendTripartiteHandoverQuery query);

    /**
     * 根据ID逻辑删除
     * @param data
     * @return
     */
    int deleteByIds(DeleteRequest<SendTripartiteHandoverDetail> data);

    /**
     * 根据ID更新热点值
     * @param id
     * @return
     */
    int updateLastOperateTimeById( DeleteRequest<Long>  id);

    /**
     * 根据ID获取三方交接明细
     * @param id
     * @return
     */
    SendTripartiteHandoverDetail getInfoById(SendTripartiteHandoverQuery id);

    /**
     * 根据ID更新审批状态
     * @param detail
     * @return
     */
    int updateApprovalStatusById(UpdateRequest<Long> detail);
}
