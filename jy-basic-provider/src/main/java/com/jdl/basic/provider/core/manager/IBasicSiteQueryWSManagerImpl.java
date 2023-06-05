package com.jdl.basic.provider.core.manager;

import com.jd.ql.basic.dto.BaseSiteInfoDto;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.dto.PageDto;
import com.jd.ql.basic.ws.BasicSiteQueryWS;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
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

    @Override
    public List<BaseStaffSiteOrgDto> getBaseSiteByProvinceAgencyCodeSubTypePage(
        String provinceAgencyCode, List<Integer> subTypes, Integer pageIndex) {
        try {
            PageDto<List<BaseStaffSiteOrgDto>> resp = basicSiteQueryWS.getBaseSiteByProvinceAgencyCodeSubTypePage(provinceAgencyCode,subTypes,pageIndex);
            if (ObjectHelper.isNotNull(resp)){
              if (ObjectHelper.isNotNull(resp.getData())){
                return resp.getData();
              }
              log.error("getBaseSiteByProvinceAgencyCodeSubTypePage 未查询到数据：{}",resp.getMessage());
            }
        } catch (Exception e) {
            log.error("getBaseSiteByProvinceAgencyCodeSubTypePage 查询异常：{},{}", provinceAgencyCode,subTypes);
        }
        return null;
    }
}
