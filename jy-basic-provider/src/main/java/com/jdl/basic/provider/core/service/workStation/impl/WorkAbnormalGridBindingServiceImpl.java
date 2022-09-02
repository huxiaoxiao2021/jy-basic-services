package com.jdl.basic.provider.core.service.workStation.impl;


import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.dao.workStation.WorkAbnormalGridDao;
import com.jdl.basic.provider.core.dao.workStation.WorkStationGridDao;
import com.jdl.basic.provider.core.service.workStation.WorkAbnormalGridBindingService;
import com.jdl.basic.provider.common.Jimdb.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.jdl.basic.common.utils.StringHelper.EXCP_AREA;
import static com.jdl.basic.common.utils.StringHelper.FLOOR;

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

    @Resource
    @Qualifier("JimdbCacheService")
    private CacheService cacheService;

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
            if (Objects.equals(EXCP_AREA,d.getAreaType())) {
                continue;
            }
            data.add(d);
        }
        WorkStationFloorGridQuery queryChecked = new WorkStationFloorGridQuery();
        queryChecked.setSiteCode(query.getSiteCode());
        List<WorkStationBinding> checks = workAbnormalGridDao.queryBindingList(queryChecked);
        WorkStationGridQuery queryFloor = new WorkStationGridQuery();
        queryFloor.setSiteCode(query.getSiteCode());
        List<Integer> defaultCheck = new ArrayList<>();
        WorkStationBindingEditVo r = new WorkStationBindingEditVo();
        HashMap<Object, HashMap> tree = getTree(data, defaultCheck, checks, query);
        r.setDefaultChecks(defaultCheck);
        r.setWorkStationBindingVo(getWorkStationBindingVo(tree));
        return result.setData(r);
    }

    private List<WorkStationBindingVo> getWorkStationBindingVo(HashMap<Object, HashMap> tree) {
        List<WorkStationBindingVo> res = new ArrayList<>();
        for (Object k : tree.keySet()) {
            WorkStationBindingVo floor = new WorkStationBindingVo();
            floor.setLabel(FLOOR+String.valueOf(k));
            HashMap ar = tree.get(k);
            List<WorkStationBindingVo> areas = new ArrayList<>();
            for (Object a : ar.keySet()) {
                WorkStationBindingVo area = new WorkStationBindingVo();
                area.setLabel((String) a);
                area.setChildren((List<WorkStationBindingVo>) ar.get(a));
                areas.add(area);
            }
            floor.setChildren(areas);
            res.add(floor);
        }
        return res;
    }

    private HashMap<Object, HashMap> getTree(List<WorkStationGrid> data, List<Integer> defaultCheck, List<WorkStationBinding> ckecks, WorkStationFloorGridQuery query) {
        int id =0;
        HashMap res = new HashMap<>();
        for (WorkStationGrid d : data) {
            if (!res.containsKey(d.getFloor())){
                res.put(d.getFloor(),new HashMap<>());
            }
            HashMap floor = (HashMap) res.get(d.getFloor());
            if (StringUtils.isEmpty(d.getAreaCode())){
                continue;
            }
            if (!floor.containsKey(d.getAreaCode())){
                floor.put(d.getAreaCode(),new ArrayList<>());
            }

            ArrayList<WorkStationBindingVo> grids = (ArrayList) floor.get(d.getAreaCode());
            WorkStationBindingVo grid = new WorkStationBindingVo();
            grid.setLabel(d.getGridName() + "  " + d.getGridCode());
            grid.setFloor(d.getFloor());
            grid.setGridCode(d.getGridCode());
            for (WorkStationBinding ckeck : ckecks) {
                if (ckeck.getGridCode().equals(d.getGridCode())
                        && ckeck.getFloor().equals(d.getFloor())){
                    if (ckeck.getExcpFloor().equals(query.getExcpFloor())&&ckeck.getExcpGridCode().equals(query.getExcpGridCode())){
                        grid.setId(id);
                        defaultCheck.add(id);
                        id++;
                    }else {
                        grid.setDisabled(Boolean.TRUE);
                    }
                }
            }
            grids.add(grid);
        }
        return res;
    }

//    private List<WorkStationBindingVo> getTree(List<WorkStationGrid> data,List<Integer> floor, List<WorkStationBinding> ckecks,List<Integer> defultCheck,WorkStationFloorGridQuery query){
//        Integer id = 0;
//        List<WorkStationBindingVo> res = new ArrayList<>();
//        for (Integer f : floor) {
//            List<String> areas = new ArrayList<>();
//            WorkStationBindingVo flo = new WorkStationBindingVo();
//            flo.setLabel(FLOOR+f);
//            flo.setChildren(new ArrayList<>());
//            for (WorkStationGrid d : data) {
//                if (Objects.equals(d.getFloor(), f)&&!areas.contains(d.getAreaCode())) {
//                    if (StringUtils.isEmpty(d.getAreaCode())){
//                        areas.add(d.getAreaCode());
//                    }
//                    WorkStationBindingVo area = new WorkStationBindingVo();
//                    area.setLabel(d.getAreaName() + "  " + d.getAreaCode());
//                    area.setChildren(new ArrayList<>());
//                    for (WorkStationGrid g : data) {
//                        if (Objects.equals(g.getFloor(), f)&&Objects.equals(d.getAreaCode(),g.getAreaCode())) {
//                            WorkStationBindingVo grid = new WorkStationBindingVo();
//                            grid.setFloor(f);
//                            grid.setLabel(g.getGridName() + "  " + g.getGridCode());
//                            grid.setGridCode(g.getGridCode());
//                            for (WorkStationBinding ckeck : ckecks) {
//                                if (ckeck.getGridCode().equals(g.getGridCode())
//                                    && ckeck.getFloor().equals(g.getFloor())){
//                                    if (ckeck.getExcpFloor().equals(query.getExcpFloor())&&ckeck.getExcpGridCode().equals(query.getExcpGridCode())){
//                                        grid.setId(id);
//                                        defultCheck.add(id);
//                                        id++;
//                                    }else {
//                                        grid.setDisabled(Boolean.TRUE);
//                                    }
//                                }
//                            }
//                            area.getChildren().add(grid);
//                        }
//                    }
//                    flo.getChildren().add(area);
//                }
//            }
//            res.add(flo);
//        }
//        return res;
//    }
    @Override
    public Result<Boolean> insert(WorkStationBinding insertData) {
        Result<Boolean> result = Result.success();
        if (insertData.getSiteCode() == null) {
            return Result.fail("场地编号为空！");
        }
        if (StringUtils.isEmpty(insertData.getGridCode())){
            return Result.fail("网格编号为空！");
        }
        if (insertData.getFloor() == null){
            return Result.fail("楼层为空！");
        }
        if (StringUtils.isEmpty(insertData.getExcpGridCode())){
            return Result.fail("异常网格编号为空！");
        }
        if (insertData.getExcpFloor() == null){
            return Result.fail("异常楼层为空！");
        }
        String key = insertData.getSiteCode().toString() + insertData.getFloor() + insertData.getGridCode();
        try {
            boolean getLock = cacheService.setNx(key, 1 + "", 60, TimeUnit.SECONDS);
            if(!getLock){
                result.toFail("操作太快，正在处理中");
                return result;
            }
            long count = workAbnormalGridDao.queryCount(insertData);
            if (count > 0) {
                workAbnormalGridDao.update(insertData);
            } else {
                workAbnormalGridDao.insert(insertData);
            }
        } finally {
            cacheService.del(key);
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
        Set<Integer> floor = new HashSet<>();
        for (WorkStationBinding d : info) {
            floor.add(d.getFloor());
            WorkStationFloorGridQuery gridQuery = new WorkStationFloorGridQuery();
            gridQuery.setPageNumber(0);
            gridQuery.setSiteCode(query.getSiteCode());
            gridQuery.setFloor(d.getFloor());
            gridQuery.setGridCode(d.getGridCode());
            gridQuery.setAreaType(query.getAreaType());
            data.addAll(workStationGridDao.queryListDistinct(gridQuery));
        }
        return Result.success(getLookTree(data,floor));
    }
    private List<WorkStationBindingVo> getLookTree(List<WorkStationGrid> data,Set<Integer> floor){
        Integer id = 0;
        List<WorkStationBindingVo> res = new ArrayList<>();
        for (Integer f : floor) {
            List<String> areas = new ArrayList<>();
            WorkStationBindingVo flo = new WorkStationBindingVo();
            flo.setLabel(FLOOR+f);
            flo.setChildren(new ArrayList<>());
            for (WorkStationGrid d : data) {
                if (Objects.equals(d.getFloor(), f)&&!areas.contains(d.getAreaCode())) {
                    if (StringUtils.isEmpty(d.getAreaCode())){
                        areas.add(d.getAreaCode());
                    }
                    WorkStationBindingVo area = new WorkStationBindingVo();
                    area.setLabel(d.getAreaName() + " " + d.getAreaCode());
                    area.setChildren(new ArrayList<>());
                    for (WorkStationGrid g : data) {
                        if (Objects.equals(g.getFloor(), f)&&Objects.equals(d.getAreaCode(),g.getAreaCode())) {
                            WorkStationBindingVo grid = new WorkStationBindingVo();
                            grid.setFloor(f);
                            grid.setLabel(g.getGridName() + g.getGridCode());
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
    public Result<Boolean> update(List<WorkStationBinding> updateData) {
        Result<Boolean> result = Result.success();
        for (WorkStationBinding data : updateData) {
            if (data.getSiteCode() == null) {
                return Result.fail("场地编号为空！");
            }
            if (StringUtils.isEmpty(data.getGridCode())){
                return Result.fail("网格编号为空！");
            }
            if (data.getFloor() == null){
                return Result.fail("楼层为空！");
            }
            if (StringUtils.isEmpty(data.getExcpGridCode())){
                return Result.fail("异常网格编号为空！");
            }
            if (data.getExcpFloor() == null){
                return Result.fail("异常楼层为空！");
            }
            workAbnormalGridDao.updateByGridCode(data);
        }
        return result;
    }

    @Override
    public WorkStationFloorGridVo getAbnormalGrid(WorkStationFloorGridQuery query) {
        return workAbnormalGridDao.getAbnormalGrid(query);
    }

    @Override
    public Result<List<WorkStationFloorGridVo>> queryListForExport(WorkStationFloorGridQuery query) {
        query.setPageNumber(0);
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
            if (workAbnormalGridDao.queryCountUnDelete(query) > 0) {
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
