package com.jdl.basic.provider.core.manager;

import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jd.ldop.basic.dto.BasicTraderInfoDTO;
import com.jd.ldop.basic.dto.ResponseDTO;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.ws.BasicPrimaryWS;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.common.contants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基础资料包装类
 *
 * @author hujiping
 * @date 2022/10/8 11:17 AM
 */
@Slf4j
@Service("baseMajorManager")
public class BaseMajorManagerImpl implements BaseMajorManager {

    @Autowired
    private BasicPrimaryWS basicPrimaryWS;

    @Cache(key = "baseMajorManagerImpl.getBaseSiteBySiteId@args0", memoryEnable = true, memoryExpiredTime = 5 * 60 * 1000,
            redisEnable = true, redisExpiredTime = 10 * 60 * 1000)
    @Override
    public BaseStaffSiteOrgDto getBaseSiteBySiteId(Integer siteCode) {
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.manager.BaseMajorManagerImpl.getBaseSiteBySiteId",
                Constants.UMP_APP_NAME, false, true);
        try{
            return basicPrimaryWS.getBaseSiteBySiteId(siteCode);
        }catch (Exception e){
            log.error("根据站点ID：{}查询场地信息异常!", siteCode);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return null;
    }
}
