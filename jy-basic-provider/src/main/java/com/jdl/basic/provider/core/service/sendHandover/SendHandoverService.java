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
    Result<List<SendTripartiteHandoverDetail>> getListOrderByOperateTime(SendTripartiteHandoverQuery query);
}
