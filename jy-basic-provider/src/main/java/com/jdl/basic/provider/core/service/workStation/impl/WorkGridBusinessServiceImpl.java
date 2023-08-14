package com.jdl.basic.provider.core.service.workStation.impl;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionQuery;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.common.contants.BaseContants;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.workMapFunc.JyWorkMapFuncConfigService;
import com.jdl.basic.provider.core.service.workStation.WorkGridBusinessService;
import com.jdl.basic.provider.core.service.workStation.WorkGridFlowDirectionService;
import com.jdl.basic.provider.core.service.workStation.WorkStationGridService;
import com.jdl.basic.provider.dto.FlowDirectionFunctionConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 场地网格流向业务表--JsfService接口实现
 *
 * @author ext.lishaotan5
 * @date 2023年08月10日 15:51:56
 */
@Slf4j
@Service("workGridBusinessService")
public class WorkGridBusinessServiceImpl implements WorkGridBusinessService {

    @Autowired
    @Qualifier("workGridFlowDirectionService")
    private WorkGridFlowDirectionService workGridFlowDirectionService;

    @Autowired
    @Qualifier("jyWorkMapFuncConfigService")
    private JyWorkMapFuncConfigService jyWorkMapFuncConfigService;

    @Autowired
    @Qualifier("baseMajorManager")
    private BaseMajorManager baseMajorManager;

    @Autowired
    @Qualifier("workStationGridService")
    private WorkStationGridService workStationGridService;

    @Autowired
    private FlowDirectionFunctionConfiguration flowDirectionFunction;

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridBusinessServiceImpl.queryDockCodeByFlowDirection", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    @Cache(key = "WorkGridBusinessServiceImpl.queryDockCodeByFlowDirection@args0:@args1:@args2", memoryEnable = true, memoryExpiredTime = 2 * 60 * 1000
            , redisEnable = true, redisExpiredTime = 2 * 60 * 1000)
    public Result<List<String>> queryDockCodeByFlowDirection(Integer flowDirectionType, Integer startSiteID, Integer endSiteID) {
        Result<List<String>> result = new Result<>();
        //获取网格信息
        Result<WorkGridFlowDirectionQuery> workGridFlowDirectionQuery = getWorkStation(flowDirectionType, startSiteID, endSiteID);
        if (!result.isSuccess()) {
            result.setCode(result.getCode());
            result.setMessage(result.getMessage());
            return result;
        }
        //3、根据ref_work_key去work_station 网格工序信息表中查询business_key
        //4、根据场地编码、business_key、ref_work_grid_key去work_station_grid 场地网格工序信息表查询 获取到月台号 和 business_key
        //3、4合并获取月台号
        List<String> dockCOde = workStationGridService.queryDockCodeByFlowDirection(workGridFlowDirectionQuery.getData());
        result.setData(dockCOde);
        return result;
    }

    /**
     * 查询网格信息
     * @param flowDirectionType
     * @param startSiteID
     * @param endSiteID
     * @return
     */
    private Result<WorkGridFlowDirectionQuery> getWorkStation(Integer flowDirectionType, Integer startSiteID, Integer endSiteID) {
        Result<WorkGridFlowDirectionQuery> result = new Result<>();
        //1、根据传入的类型去jy_work_map_func_config 拣运APP功能和工序映射表中查询出ref_work_key
        List<String> refWorkKeyList = getRefWorkKey(flowDirectionType);
        //获取流向的查询条件
        WorkGridFlowDirectionQuery workGridFlowDirectionQuery = getWorkGridFlowDirectionQuery(flowDirectionType, startSiteID, endSiteID);
        //2、根据传入的流向：发货地ID、目的地ID去work_grid_flow_direction  场地网格流向表 获取 ref_work_grid_key
        List<String> refWorkGridKey = workGridFlowDirectionService.queryRefWorkGridKeyByFlowDirection(workGridFlowDirectionQuery);
        result = checkParam(workGridFlowDirectionQuery);
        if (!result.isSuccess()) {
            result.setCode(result.getCode());
            result.setMessage(result.getMessage());
            return result;
        }
        workGridFlowDirectionQuery.setRefWorkGridKeyList(refWorkGridKey);
        workGridFlowDirectionQuery.setRefWorkKeyList(refWorkKeyList);
        workGridFlowDirectionQuery.setPageSize(100);
        result.setData(workGridFlowDirectionQuery);
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridBusinessServiceImpl.queryPhoneByDockCode", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    @Cache(key = "WorkGridBusinessServiceImpl.queryByDockCode@args0@args1@args2@args3", memoryEnable = true, memoryExpiredTime = 2 * 60 * 1000
            , redisEnable = true, redisExpiredTime = 2 * 60 * 1000)
    public Result<List<WorkStationGrid>> queryPhoneByDockCodeForTms(Integer flowDirectionType, Integer startSiteID, Integer endSiteID, Integer dockCode) {
        Result<List<WorkStationGrid>> result = new Result<>();
        //获取网格信息
        Result<WorkGridFlowDirectionQuery> workGridFlowDirectionQuery = getWorkStation(flowDirectionType, startSiteID, endSiteID);
        if (!result.isSuccess()) {
            result.setCode(result.getCode());
            result.setMessage(result.getMessage());
            return result;
        }
        workGridFlowDirectionQuery.getData().setDockCode(dockCode);
        List<WorkStationGrid> WorkStationGrids = workStationGridService.queryPhoneByDockCodeForTms(workGridFlowDirectionQuery.getData());
        result.setData(WorkStationGrids);
        return result;
    }


    /**
     * 去jy_work_map_func_config 拣运APP功能和工序映射表中查询出ref_work_key
     *
     * @param flowDirectionType
     * @return
     */
    private List<String> getRefWorkKey(Integer flowDirectionType) {
        List<String> functionList = getFunctionByFlowDirection(flowDirectionType);
        if (CollectionUtils.isEmpty(functionList)) {
            return null;
        }
        return jyWorkMapFuncConfigService.queryByFuncCodeList(functionList);
    }

    /**
     * 根据流向获取功能编码
     *
     * @param flowDirectionType
     * @return
     */
    private List<String> getFunctionByFlowDirection(Integer flowDirectionType) {
        return flowDirectionFunction.getFunctionByFlowDirection(flowDirectionType);
    }

    /**
     * 获取流向的查询条件
     *
     * @param flowDirectionType
     * @param startSiteID
     * @param endSiteID
     * @return
     */
    private WorkGridFlowDirectionQuery getWorkGridFlowDirectionQuery(Integer flowDirectionType, Integer startSiteID, Integer endSiteID) {

        WorkGridFlowDirectionQuery workGridFlowDirectionQuery = new WorkGridFlowDirectionQuery();

        workGridFlowDirectionQuery.setFlowDirectionType(flowDirectionType);
        if (flowDirectionType.equals(BaseContants.NUMBER_ONE)) {
            workGridFlowDirectionQuery.setSiteCode(startSiteID);
            workGridFlowDirectionQuery.setFlowSiteCode(endSiteID);
        } else {
            workGridFlowDirectionQuery.setSiteCode(endSiteID);
            workGridFlowDirectionQuery.setFlowSiteCode(startSiteID);
        }
        return workGridFlowDirectionQuery;
    }

    /**
     * 参数校验
     *
     * @param workGridFlowDirectionQuery
     * @return
     */
    private Result<WorkGridFlowDirectionQuery> checkParam(WorkGridFlowDirectionQuery workGridFlowDirectionQuery) {
        Result<WorkGridFlowDirectionQuery> result = new Result<>();
        if (ObjectHelper.isEmpty(workGridFlowDirectionQuery.getSiteCode())) {
            return result.toFail("场地编码有误");
        }
        if (CollectionUtils.isEmpty(workGridFlowDirectionQuery.getRefWorkKeyList())) {
            return result.toFail("功能表获取数据有误");
        }
        if (CollectionUtils.isEmpty(workGridFlowDirectionQuery.getRefWorkGridKeyList())) {
            return result.toFail("流向表获取数据有误");
        }
        return result;
    }
}


