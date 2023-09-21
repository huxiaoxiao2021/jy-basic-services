package com.jdl.basic.provider.core.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.bd.dms.automatic.sdk.common.dto.BaseDmsAutoJsfResponse;
import com.jd.bd.dms.automatic.sdk.modules.device.DeviceConfigInfoJsfService;
import com.jd.bd.dms.automatic.sdk.modules.device.dto.DeviceGridDto;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.api.domain.workStation.WorkGridDeviceVo;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.Result;

/**
 * 
 * @author wuyoude
 *
 */
@Service("deviceConfigInfoJsfServiceManager")
public class DeviceConfigInfoJsfServiceManagerImpl implements DeviceConfigInfoJsfServiceManager{

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeviceConfigInfoJsfService deviceConfigInfoJsfService;

    @Override
    public Result<List<WorkGridDeviceVo>> findDeviceGridByBusinessKey(String gridKey,List<String> gridKeyList) {
        CallerInfo callerInfo = Profiler.registerInfo("basic.client.automatic.deviceConfigInfoJsfService.findDeviceGridByBusinessKey",
                Constants.UMP_APP_NAME, false, true);
        Result<List<WorkGridDeviceVo>> result = Result.success();
        List<WorkGridDeviceVo> dataList = new ArrayList<>();
        result.setData(dataList);
        try {
        	BaseDmsAutoJsfResponse<List<DeviceGridDto>> remoteResult = deviceConfigInfoJsfService.findDeviceGridByBusinessKey(gridKey,gridKeyList);
        	if(remoteResult != null && CollectionUtils.isEmpty(remoteResult.getData())) {
        		for(DeviceGridDto dto:remoteResult.getData()) {
        			dataList.add(toWorkGridDeviceVo(dto));
        		}
        	}
        }catch (Exception e){
            log.error("根据设备编码:{},{}查询设备详情异常!", gridKey,gridKeyList, e);
            Profiler.functionError(callerInfo);
        }finally {
            Profiler.registerInfoEnd(callerInfo);
        }
        return result;
    }

	private WorkGridDeviceVo toWorkGridDeviceVo(DeviceGridDto dto) {
		WorkGridDeviceVo vo = new WorkGridDeviceVo();
		vo.setChuteCode(dto.getChuteCode());
		vo.setMachineCode(dto.getMachineCode());
		vo.setSupplyNo(dto.getSupplyNo());
		return vo;
	}
}
    
