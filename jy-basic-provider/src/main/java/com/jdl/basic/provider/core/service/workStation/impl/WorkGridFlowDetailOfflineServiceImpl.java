package com.jdl.basic.provider.core.service.workStation.impl;

import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOffline;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOfflineQuery;
import com.jdl.basic.api.domain.workStation.WorkStationAttendPlan;
import com.jdl.basic.api.domain.workStation.WorkStationAttendPlanQuery;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.workStation.WorkGridFlowDetailOfflineDao;
import com.jdl.basic.provider.core.service.workStation.WorkGridFlowDetailOfflineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("workGridFlowDetailOfflineService")
public class WorkGridFlowDetailOfflineServiceImpl implements WorkGridFlowDetailOfflineService {

    @Autowired
    private WorkGridFlowDetailOfflineDao workGridFlowDetailOfflineDao;

    @Override
    public Result<PageDto<WorkGridFlowDetailOffline>> queryPageList(WorkGridFlowDetailOfflineQuery query) {
        Result<PageDto<WorkGridFlowDetailOffline>> result = Result.success();
        Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
        if(!checkResult.isSuccess()){
            return Result.fail(checkResult.getMessage());
        }
        PageDto<WorkGridFlowDetailOffline> pageData = new PageDto<>(query.getPageNumber(), query.getPageSize());
        Long totalCount = workGridFlowDetailOfflineDao.queryCount(query);
        if(totalCount != null && totalCount > 0){
            List<WorkGridFlowDetailOffline> dataList = workGridFlowDetailOfflineDao.queryList(query);
            pageData.setResult(dataList);
            pageData.setTotalRow(totalCount.intValue());
        }else {
            pageData.setResult(new ArrayList<WorkGridFlowDetailOffline>());
            pageData.setTotalRow(0);
        }

        result.setData(pageData);
        return result;
    }
    public Result<Boolean> checkParamForQueryPageList(WorkGridFlowDetailOfflineQuery query){
        Result<Boolean> result = Result.success();
        if(query.getPageSize() == null
                || query.getPageSize() <= 0) {
            query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
        }
        if(query.getDt() == null) {
        	query.setDt(DateFormatUtils.format(DateHelper.addDays(new Date(), -1), DateHelper.DATE_FORMAT_YYYY_MM_DD));
        }
        query.setOffset(0);
        query.setLimit(query.getPageSize());
        if(query.getPageNumber() > 0) {
            query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
        }

        return result;
    }

}
