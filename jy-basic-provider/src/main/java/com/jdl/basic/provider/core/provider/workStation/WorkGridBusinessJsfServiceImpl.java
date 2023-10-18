package com.jdl.basic.provider.core.provider.workStation;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.api.service.workStation.WorkGridBusinessJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.workStation.WorkGridBusinessService;
import lombok.extern.slf4j.Slf4j;
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
@Service("workGridBusinessJsfService")
public class WorkGridBusinessJsfServiceImpl implements WorkGridBusinessJsfService {

    @Autowired
    @Qualifier("workGridBusinessService")
    private WorkGridBusinessService workGridBusinessService;

    @Autowired
    @Qualifier("baseMajorManager")
    private BaseMajorManager baseMajorManager;

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridBusinessJsfService.queryDockCodeByFlowDirection", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP, JProEnum.FunctionError})
    public Result<List<String>> queryDockCodeByFlowDirection(DockCodeAndPhoneQuery dockCodeAndPhoneQuery) {
        Result<List<String>> result = new Result<>();
        //根据传过来的siteId转换为网格的siteId
        BaseStaffSiteOrgDto startBaseSiteByDmsCode = baseMajorManager.getBaseSiteByDmsCode(dockCodeAndPhoneQuery.getStartSiteID());
        if (ObjectHelper.isEmpty(startBaseSiteByDmsCode)) {
            return result.toFail("发货地ID有误");
        }
        BaseStaffSiteOrgDto endBaseSiteByDmsCode = baseMajorManager.getBaseSiteByDmsCode(dockCodeAndPhoneQuery.getEndSiteID());
        if (ObjectHelper.isEmpty(endBaseSiteByDmsCode)) {
            return result.toFail("目的地ID有误");
        }
        List<String> dockCodeList = workGridBusinessService.queryDockCodeByFlowDirection(dockCodeAndPhoneQuery.getFlowDirectionType(), startBaseSiteByDmsCode.getSiteCode(), endBaseSiteByDmsCode.getSiteCode());
        result.setData(dockCodeList);
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridBusinessJsfService.queryPhoneByDockCodeForTms", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP, JProEnum.FunctionError})
    public Result<List<WorkStationGrid>> queryPhoneByDockCodeForTms(DockCodeAndPhoneQuery dockCodeAndPhoneQuery) {
        Result<List<WorkStationGrid>> result = new Result<>();
        //根据传过来的siteId转换为网格的siteId
        BaseStaffSiteOrgDto startBaseSiteByDmsCode = baseMajorManager.getBaseSiteByDmsCode(dockCodeAndPhoneQuery.getStartSiteID());
        if (ObjectHelper.isEmpty(startBaseSiteByDmsCode)) {
            return result.toFail("发货地ID有误");
        }
        BaseStaffSiteOrgDto endBaseSiteByDmsCode = baseMajorManager.getBaseSiteByDmsCode(dockCodeAndPhoneQuery.getEndSiteID());
        if (ObjectHelper.isEmpty(endBaseSiteByDmsCode)) {
            return result.toFail("目的地ID有误");
        }
        if (ObjectHelper.isEmpty(dockCodeAndPhoneQuery.getDockCode())) {
            return result.toFail("月台号为空");
        }
        List<WorkStationGrid> workStationGridList = workGridBusinessService.queryPhoneByDockCodeForTms(dockCodeAndPhoneQuery.getFlowDirectionType(), startBaseSiteByDmsCode.getSiteCode(), endBaseSiteByDmsCode.getSiteCode(), dockCodeAndPhoneQuery.getDockCode());
        result.setData(workStationGridList);
        return result;
    }
}


