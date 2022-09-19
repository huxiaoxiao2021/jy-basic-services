package com.jdl.basic.provider.core.service.sendHandover;

import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverDetail;
import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * @author liwenji
 * @description 发货交接清单
 * @date 2022-09-07 19:58
 */
public interface SendHandoverService {

    /**
     * 新增
     * @param detail
     * @return
     */
    Result<Integer> insert(SendTripartiteHandoverDetail detail);

    /**
     * 分页获取该场地信息 根据最后操作时间进行排序
     * @param query
     * @return
     */
    Result<PageDto<SendTripartiteHandoverDetail>> getPageOrderByOperateTime(SendTripartiteHandoverQuery query);

    /**
     * 获取该场地信息 根据最后操作时间进行排序 limit
     * @param query
     * @return
     */
    Result<List<SendTripartiteHandoverDetail>> getListOrderByOperateTime(Integer query);

    /**
     * 获取该场地的信息总数
     * @return
     * @param query
     */
    Result<Integer> queryCountBySiteCode(SendTripartiteHandoverQuery query);

    /**
     * 导出
     * @param query
     * @return
     */
    Result<List<SendTripartiteHandoverDetail>> queryListForExport(SendTripartiteHandoverQuery query);

    /**
     * 批量删除
     * @param deleteDatas
     * @param erp
     * @return
     */
    Result<Boolean> deleteByIds(List<SendTripartiteHandoverDetail> deleteDatas, String erp);

    /**
     * 导入
     * @param datas
     * @return
     */
    Result<List<Long>> importDatas(List<SendTripartiteHandoverDetail> datas);

    /**
     * 根据ID更新热点值
     * @param id
     * @return
     */
    Result<Boolean> updateLastOperateTimeById(List<Long> id);

    /**
     * 根据id 获取详细信息
     * @param id
     * @return
     */
    Result<SendTripartiteHandoverDetail> getInfoById(Long id);

    /**
     * 根据ID更新审批状态
     * @param id
     * @param approvalStatus
     * @return
     */
    Result<Boolean> updateApprovalStatusById(Long id, Integer approvalStatus);
}
