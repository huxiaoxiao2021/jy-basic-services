package com.jdl.basic.provider.core.service.sendHandover.impl;

import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverDetail;
import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverQuery;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.sendHandover.SendTripartiteHandoverDetailDao;
import com.jdl.basic.provider.core.service.sendHandover.SendHandoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liwenji
 * @description 发货交接清单
 * @date 2022-09-07 19:59
 */
@Service
public class SendHandoverServiceImpl implements SendHandoverService {

    @Autowired
    private SendTripartiteHandoverDetailDao sendTripartiteHandoverDetailDao;

    private static final Integer LIMIT = 15;

    @Override
    public Result<Integer> insert(SendTripartiteHandoverDetail detail) {
        return Result.success(sendTripartiteHandoverDetailDao.insert(detail));
    }

    @Override
    public Result<PageDto<SendTripartiteHandoverDetail>> getPageOrderByOperateTime(SendTripartiteHandoverQuery query) {
        if(query.getPageNumber() < Constants.YN_YES || query.getPageSize() <= Constants.YN_NO){
            return Result.fail("分页参数不合法");
        }
        PageDto<SendTripartiteHandoverDetail> pageDto = new PageDto<SendTripartiteHandoverDetail>();
        query.setLimit(query.getPageSize());
        query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
        int count = sendTripartiteHandoverDetailDao.queryCount(query);
        if(count > 0){
            pageDto.setTotalRow(count);
            pageDto.setResult(sendTripartiteHandoverDetailDao.getPageOrderByOperateTime(query));
        }else {
            pageDto.setResult(new ArrayList<SendTripartiteHandoverDetail>());
            pageDto.setTotalRow(0);
        }
        return Result.success(pageDto);
    }

    @Override
    public Result<List<SendTripartiteHandoverDetail>> getListOrderByOperateTime(SendTripartiteHandoverQuery query) {
        query.setLimit(LIMIT);
        return Result.success(sendTripartiteHandoverDetailDao.queryListOrderByOperateTime(query));
    }
}
