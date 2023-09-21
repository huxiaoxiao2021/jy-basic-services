package com.jdl.basic.provider.core.manager;

import java.util.List;

import com.jdl.basic.api.domain.workStation.WorkGridDeviceVo;
import com.jdl.basic.common.utils.Result;

public interface DeviceConfigInfoJsfServiceManager {

    /**
     * 根据设备编码查询再用设备详情
     *
     * @param machineCode
     * @return
     */
	Result<List<WorkGridDeviceVo>> findDeviceGridByBusinessKey(String gridKey,List<String> gridKeyList);

}
