package com.jdl.basic.provider.core.provider.workStation;

import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.api.service.workStation.WorkGridBusinessJsfService;
import com.jdl.basic.common.contants.BaseContants;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.workMapFunc.JyWorkMapFuncConfigService;
import com.jdl.basic.provider.core.service.workStation.WorkGridFlowDirectionService;
import com.jdl.basic.provider.core.service.workStation.WorkStationGridService;
import com.jdl.basic.provider.dto.FlowDirectionFunctionConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 场地网格流向业务表--JsfService接口实现
 *
 * @author ext.lishaotan5
 * @date 2023年08月10日 15:51:56
 */
@Slf4j
@Service("workGridBusinessJsfServiceImpl")
public class WorkGridBusinessJsfServiceImpl implements WorkGridBusinessJsfService {

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
    public List<WorkStationGrid> queryDockCodeByFlowDirection(DockCodeAndPhoneQuery dockCodeAndPhoneQuery) {
        //1、根据传入的类型去jy_work_map_func_config 拣运APP功能和工序映射表中查询出ref_work_key
        List<String> refWorkKeyList = getRefWorkKey(dockCodeAndPhoneQuery);
        //获取流向的查询条件
        WorkGridFlowDirectionQuery workGridFlowDirectionQuery = getWorkGridFlowDirectionQuery(dockCodeAndPhoneQuery);
        //2、根据传入的流向：发货地ID、目的地ID去work_grid_flow_direction  场地网格流向表 获取 ref_work_grid_key
        List<String> refWorkGridKey = getRefWorkGridKey(workGridFlowDirectionQuery);
        //3、根据ref_work_key去work_station 网格工序信息表中查询business_key
        //4、根据场地编码、business_key、ref_work_grid_key去work_station_grid 场地网格工序信息表查询 获取到月台号 和 business_key
        //3、4合并获取月台号
        List<WorkStationGrid> dockCOde = getDockCOde(workGridFlowDirectionQuery.getSiteCode(), refWorkKeyList, refWorkGridKey);
        return dockCOde;
    }


    /**
     * 去jy_work_map_func_config 拣运APP功能和工序映射表中查询出ref_work_key
     *
     * @param dockCodeAndPhoneQuery
     * @return
     */
    private List<String> getRefWorkKey(DockCodeAndPhoneQuery dockCodeAndPhoneQuery) {
        List<String> functionList = getFunctionByFlowDirection(dockCodeAndPhoneQuery);
        if (CollectionUtils.isEmpty(functionList)) {
            return null;
        }
        return jyWorkMapFuncConfigService.queryByFuncCodeList(functionList);
    }

    /**
     * 根据流向获取功能编码
     *
     * @param dockCodeAndPhoneQuery
     * @return
     */
    private List<String> getFunctionByFlowDirection(DockCodeAndPhoneQuery dockCodeAndPhoneQuery) {
        return flowDirectionFunction.getFunctionByFlowDirection(dockCodeAndPhoneQuery.getFlowDirectionType());
    }

    /**
     * 根据传入的流向：发货地ID和目的地ID去work_grid_flow_direction  场地网格流向表 获取 ref_work_grid_key
     *
     * @param workGridFlowDirectionQuery
     * @return
     */
    private List<String> getRefWorkGridKey(WorkGridFlowDirectionQuery workGridFlowDirectionQuery) {
        return workGridFlowDirectionService.queryRefWorkGridKeyByFlowDirection(workGridFlowDirectionQuery);
    }

    /**
     * 获取流向的查询条件
     *
     * @param dockCodeAndPhoneQuery
     * @return
     */
    private WorkGridFlowDirectionQuery getWorkGridFlowDirectionQuery(DockCodeAndPhoneQuery dockCodeAndPhoneQuery) {
        //根据传过来的siteId转换为网格的siteId
        WorkGridFlowDirectionQuery workGridFlowDirectionQuery = new WorkGridFlowDirectionQuery();
        BaseStaffSiteOrgDto startBaseSiteByDmsCode = baseMajorManager.getBaseSiteByDmsCode(dockCodeAndPhoneQuery.getStartSiteID());
        if (ObjectHelper.isEmpty(startBaseSiteByDmsCode)) {
            throw new IllegalArgumentException("发货地ID有误" + dockCodeAndPhoneQuery.getStartSiteID());
        }
        BaseStaffSiteOrgDto endBaseSiteByDmsCode = baseMajorManager.getBaseSiteByDmsCode(dockCodeAndPhoneQuery.getEndSiteID());
        if (ObjectHelper.isEmpty(endBaseSiteByDmsCode)) {
            throw new IllegalArgumentException("目的地ID有误" + dockCodeAndPhoneQuery.getEndSiteID());
        }
        workGridFlowDirectionQuery.setFlowDirectionType(dockCodeAndPhoneQuery.getFlowDirectionType());
        if (dockCodeAndPhoneQuery.getFlowDirectionType().equals(BaseContants.NUMBER_ONE)) {
            workGridFlowDirectionQuery.setSiteCode(startBaseSiteByDmsCode.getSiteCode());
            workGridFlowDirectionQuery.setFlowSiteCode(endBaseSiteByDmsCode.getSiteCode());
        } else {
            workGridFlowDirectionQuery.setSiteCode(endBaseSiteByDmsCode.getSiteCode());
            workGridFlowDirectionQuery.setFlowSiteCode(startBaseSiteByDmsCode.getSiteCode());
        }
        return workGridFlowDirectionQuery;
    }

    /**
     * 获取月台号
     *
     * @param siteCode
     * @param refWorkKeyList
     * @param refWorkGridKeyList
     */
    private List<WorkStationGrid> getDockCOde(Integer siteCode, List<String> refWorkKeyList, List<String> refWorkGridKeyList) {
        return workStationGridService.getDockCode(siteCode, refWorkKeyList, refWorkGridKeyList);
    }
}


