package com.jdl.basic.provider.core.service.workStation.impl;


import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.dao.workStation.WorkAbnormalGridDao;
import com.jdl.basic.provider.core.dao.workStation.WorkStationGridDao;
import com.jdl.basic.provider.core.service.workStation.WorkAbnormalGridBindingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.BlockingDeque;

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
    public Result<Long> queryDistinctCount(WorkStationFloorGridQuery query) {
        return Result.success(workStationGridDao.queryDistinctCount(query));
    }

    @Override
    public Result<WorkStationBindingEditVo> queryListBySite(WorkStationFloorGridQuery query) {
        Result<WorkStationBindingEditVo> result = Result.success();
        if (query.getSiteCode() == null) {
            return Result.fail("未选择场地");
        }
        WorkStationFloorGridQuery gridQuery = new WorkStationFloorGridQuery();
        gridQuery.setSiteCode(query.getSiteCode());
        List<WorkStationGrid> grids = workStationGridDao.queryListDistinct(gridQuery);
        List<WorkStationGrid> data = new ArrayList<>();
        for (WorkStationGrid d : grids) {
            if (d.getExcpFlag() == 1) {
                continue;
            }
            data.add(d);
        }
        WorkStationFloorGridQuery queryChecked = new WorkStationFloorGridQuery();
        queryChecked.setSiteCode(query.getSiteCode());
        List<WorkStationBinding> checks = workAbnormalGridDao.queryBindingList(queryChecked);
        WorkStationGridQuery queryFloor = new WorkStationGridQuery();
        queryFloor.setSiteCode(query.getSiteCode());
        List<WorkStationGrid> floor = workStationGridDao.queryGridFloorDictList(queryFloor);
        List<Integer> newFloor = new ArrayList<>();
        for (WorkStationGrid f : floor) {
            newFloor.add(f.getFloor());
        }
        List<Integer> defultCheck = new ArrayList<>();
        WorkStationBindingEditVo r = new WorkStationBindingEditVo();
        r.setWorkStationBindingVo(getTree(data,newFloor,checks,defultCheck,query));
        r.setDefaultChecks(defultCheck);
        return result.setData(r);
    }
    private List<WorkStationBindingVo> getTree(List<WorkStationGrid> data,List<Integer> floor, List<WorkStationBinding> ckecks,List<Integer> defultCheck,WorkStationFloorGridQuery query){
        Integer id = 0;
        List<WorkStationBindingVo> res = new ArrayList<>();
        for (Integer f : floor) {
            List<String> areas = new ArrayList<>();
            WorkStationBindingVo flo = new WorkStationBindingVo();
            flo.setLabel("楼层"+f);
            flo.setChildren(new ArrayList<>());
            for (WorkStationGrid d : data) {
                if (Objects.equals(d.getFloor(), f)&&!areas.contains(d.getAreaCode())) {
                    if (StringUtils.isEmpty(d.getAreaCode())){
                        areas.add(d.getAreaCode());
                    }
                    WorkStationBindingVo area = new WorkStationBindingVo();
                    area.setLabel(d.getAreaName() + "  " + d.getAreaCode());
                    area.setChildren(new ArrayList<>());
                    for (WorkStationGrid g : data) {
                        if (Objects.equals(g.getFloor(), f)&&Objects.equals(d.getAreaCode(),g.getAreaCode())) {
                            WorkStationBindingVo grid = new WorkStationBindingVo();
                            grid.setFloor(f);
                            grid.setLabel(g.getGridName() + "  " + g.getGridCode());
                            grid.setGridCode(g.getGridCode());
                            for (WorkStationBinding ckeck : ckecks) {
                                if (ckeck.getGridCode().equals(g.getGridCode())
                                    && ckeck.getFloor().equals(g.getFloor())){
                                    if (ckeck.getExcpFloor().equals(query.getExcpFloor())&&ckeck.getExcpGridCode().equals(query.getExcpGridCode())){
                                        grid.setId(id);
                                        defultCheck.add(id);
                                        id++;
                                    }else {
                                        grid.setDisabled(Boolean.TRUE);
                                    }
                                }
                            }
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
            workAbnormalGridDao.update(insertData);
        }else {
            workAbnormalGridDao.insert(insertData);
        }
        return Result.success(Boolean.TRUE);
    }

    @Override
    public Result<List<WorkStationBindingVo>> queryBindingList(WorkStationFloorGridQuery query) {
        List<WorkStationBinding> info = workAbnormalGridDao.queryBindingList(query);
        if (CollectionUtils.isEmpty(info)) {
            return Result.success();
        }
        List<WorkStationGrid> data = new ArrayList<>();
        List<Integer> floor = new ArrayList<>();
        for (WorkStationBinding d : info) {
            floor.add(d.getFloor());
            WorkStationFloorGridQuery gridQuery = new WorkStationFloorGridQuery();
            gridQuery.setPageNumber(0);
            gridQuery.setSiteCode(query.getSiteCode());
            gridQuery.setFloor(d.getFloor());
            gridQuery.setGridCode(d.getGridCode());
            gridQuery.setExcpFlag(query.getExcpFlag());
            data.addAll(workStationGridDao.queryListDistinct(gridQuery));
        }
        return Result.success(getLookTree(data,floor));
    }
    private List<WorkStationBindingVo> getLookTree(List<WorkStationGrid> data,List<Integer> floor){
        Integer id = 0;
        List<WorkStationBindingVo> res = new ArrayList<>();
        for (Integer f : floor) {
            List<String> areas = new ArrayList<>();
            WorkStationBindingVo flo = new WorkStationBindingVo();
            flo.setLabel("楼层"+f);
            flo.setChildren(new ArrayList<>());
            for (WorkStationGrid d : data) {
                if (Objects.equals(d.getFloor(), f)&&!areas.contains(d.getAreaCode())) {
                    if (StringUtils.isEmpty(d.getAreaCode())){
                        areas.add(d.getAreaCode());
                    }
                    WorkStationBindingVo area = new WorkStationBindingVo();
                    area.setLabel(d.getAreaName());
                    area.setChildren(new ArrayList<>());
                    for (WorkStationGrid g : data) {
                        if (Objects.equals(g.getFloor(), f)&&Objects.equals(d.getAreaCode(),g.getAreaCode())) {
                            WorkStationBindingVo grid = new WorkStationBindingVo();
                            grid.setFloor(f);
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
    public Result<Boolean> update(List<WorkStationBinding> data) {
        for (WorkStationBinding d : data) {
            workAbnormalGridDao.updateByGridCode(d);
        }
        return Result.success();
    }

    @Override
    public WorkStationFloorGridVo getAbnormalGrid(WorkStationFloorGridQuery query) {
        return workAbnormalGridDao.getAbnormalGrid(query);
    }

    @Override
    public Result<List<WorkStationFloorGridVo>> queryListForExport(WorkStationFloorGridQuery query) {
        return Result.success(getWorkStationFloorGridVoList(workStationGridDao.queryListDistinct(query)));
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
            WorkStationBinding query = new WorkStationBinding();
            query.setSiteCode(data.getSiteCode());
            query.setExcpFloor(data.getFloor());
            query.setExcpGridCode(data.getGridCode());
            if (workAbnormalGridDao.queryOneUnDelete(query) > 0) {
                workStationFloorGridVo.setBinding(1);
            }else {
                workStationFloorGridVo.setBinding(0);
            }
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
