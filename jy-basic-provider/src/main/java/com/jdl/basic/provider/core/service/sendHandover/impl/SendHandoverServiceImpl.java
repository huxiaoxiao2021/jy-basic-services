package com.jdl.basic.provider.core.service.sendHandover.impl;

import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverDetail;
import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverQuery;
import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkStationAttendPlan;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.sendHandover.SendTripartiteHandoverDetailDao;
import com.jdl.basic.provider.core.service.sendHandover.SendHandoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    public Result<List<SendTripartiteHandoverDetail>> getListOrderByOperateTime(Integer siteCode) {
        SendTripartiteHandoverQuery query = new SendTripartiteHandoverQuery();
        query.setLimit(LIMIT);
        query.setSiteCode(siteCode);
        return Result.success(sendTripartiteHandoverDetailDao.queryListOrderByOperateTime(query));
    }

    @Override
    public Result<Integer> queryCountBySiteCode(SendTripartiteHandoverQuery query) {
        return Result.success(sendTripartiteHandoverDetailDao.queryCount(query));
    }

    @Override
    public Result<List<SendTripartiteHandoverDetail>> queryListForExport(SendTripartiteHandoverQuery query) {
        Result<List<WorkStationAttendPlan>> result = Result.success();
        Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
        if(!checkResult.isSuccess()){
            return Result.fail(checkResult.getMessage());
        }
        return Result.success(sendTripartiteHandoverDetailDao.getListForExport(query));
    }

    @Override
    public Result<Boolean> deleteByIds(List<SendTripartiteHandoverDetail> deleteDatas) {
        DeleteRequest<SendTripartiteHandoverDetail> deleteIds = new DeleteRequest<>();
        deleteIds.setDataList(deleteDatas);
        return Result.success(sendTripartiteHandoverDetailDao.deleteByIds(deleteIds)>0);
    }

    @Override
    public Result<Boolean> importDatas(List<SendTripartiteHandoverDetail> datas) {
        for (SendTripartiteHandoverDetail data : datas) {
            if (sendTripartiteHandoverDetailDao.insert(data)<1){
                return Result.fail("导入失败");
            }
        }
        return Result.success(Boolean.TRUE);
    }

    @Override
    public Result<Boolean> updateLastOperateTimeById(Long id) {
        SendTripartiteHandoverDetail query = new SendTripartiteHandoverDetail();
        query.setId(id);
        query.setLastOperateTime(new Date());
        return Result.success(sendTripartiteHandoverDetailDao.updateLastOperateTimeById(query)>0);
    }

    @Override
    public Result<SendTripartiteHandoverDetail> getInfoById(Long id) {
        SendTripartiteHandoverQuery query = new SendTripartiteHandoverQuery();
        query.setId(id);
        return Result.success(sendTripartiteHandoverDetailDao.getInfoById(query).get(0));
    }

    public Result<Boolean> checkParamForQueryPageList(SendTripartiteHandoverQuery query){
        Result<Boolean> result = Result.success();
        if(query.getPageSize() == null
                || query.getPageSize() <= 0) {
            query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
        }
        query.setOffset(0);
        query.setLimit(query.getPageSize());
        if(query.getPageNumber() > 0) {
            query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
        }

        return result;
    }
}
