package com.jdl.basic.provider.core.service.workStation.impl;


import com.jdl.basic.api.domain.workStation.WorkStationAttendPlanQuery;
import com.jdl.basic.api.domain.workStation.WorkStationFloorGridQuery;
import com.jdl.basic.api.domain.workStation.WorkStationFloorGridVo;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.workStation.WorkStationGridDao;
import com.jdl.basic.provider.core.service.workStation.WorkAbnormalGridBindingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liwenji
 * @description 网格异常关联实现类
 * @date 2022-08-10 19:30
 */
@Slf4j
@Service("workAbnormalGridBindingService")
public class WorkAbnormalGridBindingServiceImpl implements WorkAbnormalGridBindingService {

    @Resource
    private WorkStationGridDao workStationGridDao;

    @Override
    public Result<PageDto<WorkStationFloorGridVo>> queryListDistinct(WorkStationFloorGridQuery query) {
        Result<PageDto<WorkStationFloorGridVo>> result = Result.success();
        Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
        if(!checkResult.isSuccess()){
            return Result.fail(checkResult.getMessage());
        }
        PageDto<WorkStationFloorGridVo> pageData = new PageDto<>(query.getPageNumber(), query.getPageSize());
        Long totalCount = workStationGridDao.queryDistinctCount(query);
        if(totalCount != null && totalCount > 0){
            pageData.setResult(getWorkStationFloorGridVoList(workStationGridDao.queryListDistinct(query)));
            pageData.setTotalRow(totalCount.intValue());
        } else {
            pageData.setResult(new ArrayList<WorkStationFloorGridVo>());
            pageData.setTotalRow(0);
        }
        result.setData(pageData);
        return result;
    }

    @Override
    public Result<List<WorkStationFloorGridVo>> queryListBySite(WorkStationFloorGridQuery query) {
        Result<List<WorkStationFloorGridVo>> result = Result.success();
        if (query.getSiteCode() == null) {
            return Result.fail("未选择场地");
        }
        WorkStationFloorGridQuery gridQuery = new WorkStationFloorGridQuery();
        gridQuery.setSiteCode(query.getSiteCode());
        gridQuery.setPageNumber(0);
        result.setData(getWorkStationFloorGridVoList(workStationGridDao.queryListDistinct(gridQuery)));
        return result;
    }

    private List<WorkStationFloorGridVo> getWorkStationFloorGridVoList(List<WorkStationGrid> workStationGrids) {
        List<WorkStationFloorGridVo> result = new ArrayList<>();
        for (WorkStationGrid data : workStationGrids) {
            WorkStationFloorGridVo workStationFloorGridVo = new WorkStationFloorGridVo();
            workStationFloorGridVo.setFloor(data.getFloor());
            workStationFloorGridVo.setGridNo(data.getGridNo());
            workStationFloorGridVo.setGridCode(data.getGridCode());
            workStationFloorGridVo.setGridName(data.getGridName());
            workStationFloorGridVo.setOrgCode(data.getOrgCode());
            workStationFloorGridVo.setOrgName(data.getOrgName());
            workStationFloorGridVo.setSiteCode(data.getSiteCode());
            workStationFloorGridVo.setSiteName(data.getSiteName());
            workStationFloorGridVo.setAreaName(data.getAreaName());
            workStationFloorGridVo.setAreaCode(data.getAreaCode());
            result.add(workStationFloorGridVo);
        }
        return result;
    }

    /**
     * 查询参数校验
     * @param query
     * @return
     */
    public Result<Boolean> checkParamForQueryPageList(WorkStationFloorGridQuery query){
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
