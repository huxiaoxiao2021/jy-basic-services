package com.jdl.basic.provider.core.manager;

import com.jd.ql.basic.dto.BaseSiteInfoDto;
import com.jd.ql.basic.ws.BasicSiteQueryWS;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.common.contants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liwenji
 * @date 2022-11-14 20:08
 */
@Slf4j
@Service
public class IBasicSiteQueryWSManagerImpl implements IBasicSiteQueryWSManager {
    
    @Autowired
    private BasicSiteQueryWS basicSiteQueryWS;
    
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".IBasicSiteQueryWSManagerImpl.getBaseSiteInfoBySiteId", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public BaseSiteInfoDto getBaseSiteInfoBySiteId(Integer siteCode) {
        try {
            return basicSiteQueryWS.getBaseSiteInfoBySiteId(siteCode);
        }catch (Exception e){
            log.error("根据网点ID：{}查询网点信息异常!", siteCode);
        }
        return null;
    }
}
