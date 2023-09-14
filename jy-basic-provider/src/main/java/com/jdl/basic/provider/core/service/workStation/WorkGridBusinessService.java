package com.jdl.basic.provider.core.service.workStation;


import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;

import java.util.List;

/**
 * 场地网格业务表--Service接口
 *
 * @author ext.lishaotan5
 * @date 2023年08月09日 16:46:56
 *
 */
public interface WorkGridBusinessService {

    List<String> queryDockCodeByFlowDirection(Integer flowDirectionType,Integer startSiteID,Integer endSiteID);

    List<WorkStationGrid> queryPhoneByDockCodeForTms(Integer flowDirectionType,Integer startSiteID,Integer endSiteID,String dockCode);
}
