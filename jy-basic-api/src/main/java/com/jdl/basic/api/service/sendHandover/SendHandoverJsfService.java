package com.jdl.basic.api.service.sendHandover;

import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverDetail;
import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * @author liwenji
 * @description 发货交接清单
 * @date 2022-09-07 19:39
 */
public interface SendHandoverJsfService {

    /**
     * 新增
     */
    Result<Boolean> insert(SendTripartiteHandoverDetail detail);

    /**
     * 分页获取该场地信息 根据最后操作时间进行排序
     * @param query
     * @return
     */
    Result<PageDto<SendTripartiteHandoverDetail>> getPageOrderByOperateTime(SendTripartiteHandoverQuery query);

    /**
     * 获取该场地信息 根据最后操作时间进行排序 limit 15
     * @param siteCode
     * @return
     */
    Result<List<SendTripartiteHandoverDetail>> getListOrderByOperateTimeLimit(Integer siteCode);

    /**
     * 获取该场地总数
     * @param query
     * @return
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
     * @return
     */
    Result<Boolean> deleteByIds(List<SendTripartiteHandoverDetail> deleteDatas);

    /**
     * 导入
     * @param datas
     * @return
     */
    Result<Boolean> importDatas(List<SendTripartiteHandoverDetail> datas);

    /**
     * 根据ID更新热点值
     * @param id
     * @return
     */
    Result<Boolean> updateLastOperateTimeById(Long id);

    /**
     * 根据id获取明细
     * @param id
     * @return
     */
    Result<SendTripartiteHandoverDetail> getInfoById(Long id);
}
