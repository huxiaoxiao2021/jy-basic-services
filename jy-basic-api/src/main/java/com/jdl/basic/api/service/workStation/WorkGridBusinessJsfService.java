package com.jdl.basic.api.service.workStation;


import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.workStation.DockCodeAndPhoneQuery;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;

import java.util.List;

/**
 * 场地网格业务表--JsfService接口
 *
 * @author ext.lishaotan5
 * @date 2023年08月09日 16:46:56
 *
 */
public interface WorkGridBusinessJsfService {


    Result<List<String>> queryDockCodeByFlowDirection(DockCodeAndPhoneQuery dockCodeAndPhoneQuery);

    Result<List<WorkStationGrid>> queryPhoneByDockCodeForTms(DockCodeAndPhoneQuery dockCodeAndPhoneQuery);
}
