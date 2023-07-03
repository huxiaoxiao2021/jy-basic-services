package com.jdl.basic.provider.core.manager;

import com.jd.ql.basic.domain.BaseSite;
import com.jd.ql.basic.dto.BaseSiteInfoDto;
import com.jd.ql.basic.dto.BaseSiteSimpleDto;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.dto.PageDto;
import com.jd.ql.basic.ws.BasicSiteQueryWS;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.ObjectHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PageDto<List<BaseSiteSimpleDto>> querySiteByCondition(BaseSite site, PageDto pageDto) {
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.manager.IBasicSiteQueryWSManager.querySiteByCondition",
                Constants.UMP_APP_NAME, false, true);
        try{
            if(pageDto == null || pageDto.getCurPage() <= Constants.NUMBER_ZERO || pageDto.getPageSize() <= Constants.NUMBER_ZERO){
                pageDto = new PageDto<>();
                pageDto.setCurPage(Constants.CONSTANT_NUMBER_ONE);
                pageDto.setPageSize(Constants.CONSTANT_NUMBER_TEN);
            }
            return basicSiteQueryWS.querySiteByCondition(site, pageDto);
        }catch (Exception e){
            log.error("分页查询站点异常!", e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return null;
    }


}
