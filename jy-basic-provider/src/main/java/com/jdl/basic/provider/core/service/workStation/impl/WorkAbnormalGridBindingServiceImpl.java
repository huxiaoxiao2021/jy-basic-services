package com.jdl.basic.provider.core.service.workStation.impl;


import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.workStation.WorkAbnormalGridDao;
import com.jdl.basic.provider.core.dao.workStation.WorkStationGridDao;
import com.jdl.basic.provider.core.service.workStation.WorkAbnormalGridBindingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    private WorkAbnormalGridDao workAbnormalGridDao;

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
    public Result<List<WorkStationBindingVo>> queryListBySite(WorkStationFloorGridQuery query) {
        Result<List<WorkStationBindingVo>> result = Result.success();
        if (query.getSiteCode() == null) {
            return Result.fail("未选择场地");
        }
        WorkStationFloorGridQuery gridQuery = new WorkStationFloorGridQuery();
        gridQuery.setSiteCode(query.getSiteCode());
        gridQuery.setPageNumber(0);
        List<WorkStationGrid> data = workStationGridDao.queryListDistinct(gridQuery);
        WorkStationGridQuery queryFloor = new WorkStationGridQuery();
        queryFloor.setSiteCode(query.getSiteCode());
        List<WorkStationGrid> floor = workStationGridDao.queryGridFloorDictList(queryFloor);
        return result.setData(getTree(data,floor));
    }

    private List<WorkStationBindingVo> getTree(List<WorkStationGrid> data,List<WorkStationGrid> floor){
        List<WorkStationBindingVo> res = new ArrayList<>();
        for (WorkStationGrid f : floor) {
            WorkStationBindingVo flo = new WorkStationBindingVo();
            flo.setLabel("楼层"+f.getFloor());
            flo.setChildren(new ArrayList<>());
            for (WorkStationGrid d : data) {
                if (Objects.equals(d.getFloor(), f.getFloor())&&!flo.getChildren().contains(d.getAreaName())) {
                    WorkStationBindingVo area = new WorkStationBindingVo();
                    area.setLabel(d.getAreaName());
                    area.setChildren(new ArrayList<>());
                    for (WorkStationGrid g : data) {
                        if (Objects.equals(g.getFloor(), f.getFloor())&&Objects.equals(d.getAreaCode(),g.getAreaCode())) {
                            WorkStationBindingVo grid = new WorkStationBindingVo();
                            grid.setFloor(f.getFloor());
                            grid.setLabel(g.getGridName());
                            grid.setGridCode(g.getGridCode());
                            area.getChildren().add(grid);
                        }
                    }
                    flo.getChildren().add(area);
                }
            }
            res.add(flo);
        }
        return res;
    }
    @Override
    public Result<Boolean> insert(WorkStationBinding insertData) {
        long count = workAbnormalGridDao.queryOne(insertData);
        if (count > 0) {
            return Result.success(Boolean.TRUE);
        }
        workAbnormalGridDao.insert(insertData);
        return Result.success(Boolean.TRUE);
    }

    @Override
    public Result<List<WorkStationBindingVo>> queryBindingList(WorkStationFloorGridQuery query) {
        Result<List<WorkStationBindingVo>> result = Result.success();
        List<WorkStationBindingVo> data = workAbnormalGridDao.queryBindingList(query);
        List<Integer> floor =workAbnormalGridDao.queryFloor(query);
        return null;
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
