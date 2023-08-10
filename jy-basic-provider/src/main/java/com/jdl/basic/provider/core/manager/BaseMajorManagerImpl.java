package com.jdl.basic.provider.core.manager;

import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jd.ldop.basic.api.BasicTraderAPI;
import com.jd.ldop.basic.dto.BasicTraderInfoDTO;
import com.jd.ldop.basic.dto.ResponseDTO;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.dto.BaseStoreInfoDto;
import com.jd.ql.basic.dto.PageDto;
import com.jd.ql.basic.ws.BasicPrimaryWS;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.common.contants.BaseContants;
import com.jdl.basic.common.contants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    @Qualifier("basicTraderAPI")
    private BasicTraderAPI basicTraderAPI;

    @Cache(key = "baseMajorManagerImpl.getBaseSiteBySiteId@args0", memoryEnable = true, memoryExpiredTime = 5 * 60 * 1000,
            redisEnable = true, redisExpiredTime = 10 * 60 * 1000)
    @Override
    public BaseStaffSiteOrgDto getBaseSiteBySiteId(Integer siteCode) {
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.manager.BaseMajorManagerImpl.getBaseSiteBySiteId",
                Constants.UMP_APP_NAME, false, true);
        try{
            return basicPrimaryWS.getBaseSiteBySiteId(siteCode);
        }catch (Exception e){
            log.error("根据站点ID：{}查询场地信息异常!", siteCode, e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return null;
    }

    @Override
    public PageDto<List<Integer>> getBaseSiteCodeNoYnByPage(Integer subType, Integer pageIndex) {
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.manager.BaseMajorManagerImpl.getBaseSiteCodeNoYnByPage",
                Constants.UMP_APP_NAME, false, true);
        try{
            return basicPrimaryWS.getBaseSiteCodeNoYnByPage(subType, pageIndex);
        }catch (Exception e){
            log.error("分页查询站点异常!", e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return null;
    }

    @Override
    public PageDto<List<BaseStoreInfoDto>> getBaseStoreInfoByPage(Integer pageIndex) {
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.provider.core.manager.BaseMajorManagerImpl.getBaseStoreInfoByPage",
                Constants.UMP_APP_NAME, false, true);
        try{
            return basicPrimaryWS.getBaseStoreInfoByPage(pageIndex);
        }catch (Exception e){
            log.error("分页查询站点异常!", e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return null;
    }

    @Override
    @Cache(key = "baseMajorManagerImpl.getBaseStaffByErp@args0", memoryEnable = true, memoryExpiredTime = 1 * 60 * 1000, redisEnable = false)
    public BaseStaffSiteOrgDto getBaseStaffByErp(String erp) {
        try {
            return basicPrimaryWS.getBaseStaffByErp(erp);
        } catch (Exception e) {
            log.error("invoke getBaseStaffByErp error:{}",erp,e);
        }
        return null;
    }

    /**
     * 7位编码
     */
    @Override
    @Cache(key = "baseMajorManagerImpl.getBaseSiteByDmsCode@args0", memoryEnable = true, memoryExpiredTime = 5 * 60 * 1000,
            redisEnable = true, redisExpiredTime = 10 * 60 * 1000)
    @JProfiler(jKey = "DMS.BASE.BaseMajorManagerImpl.getBaseSiteByDmsCode", mState = {JProEnum.TP, JProEnum.FunctionError})
    public BaseStaffSiteOrgDto getBaseSiteByDmsCode(String siteCode) {
        BaseStaffSiteOrgDto dtoStaff = basicPrimaryWS.getBaseSiteByDmsCode(siteCode);
        BasicTraderInfoDTO dtoTrade = null;
        ResponseDTO<BasicTraderInfoDTO> responseDTO = null;
        if (dtoStaff != null)
            return dtoStaff;
        else
            dtoStaff = basicPrimaryWS.getBaseStoreByDmsCode(siteCode);

        if (dtoStaff != null)
            return dtoStaff;
        else
            responseDTO = basicTraderAPI.getBaseTraderByCode(siteCode);

        if (responseDTO != null && responseDTO.getResult() != null)
            dtoStaff = getBaseStaffSiteOrgDtoFromTrader(responseDTO.getResult());
        return dtoStaff;
    }

    public BaseStaffSiteOrgDto getBaseStaffSiteOrgDtoFromTrader(
            BasicTraderInfoDTO trader) {
        BaseStaffSiteOrgDto baseStaffSiteOrgDto = new BaseStaffSiteOrgDto();
        baseStaffSiteOrgDto.setDmsSiteCode(trader.getTraderCode());
        baseStaffSiteOrgDto.setSiteCode(trader.getId());
        baseStaffSiteOrgDto.setSiteName(trader.getTraderName());
        baseStaffSiteOrgDto.setSiteType(BaseContants.BASIC_B_TRADER_SITE_TYPE);
        baseStaffSiteOrgDto.setOrgId(BaseContants.BASIC_B_TRADER_ORG);
        baseStaffSiteOrgDto.setOrgName(BaseContants.BASIC_B_TRADER_ORG_NAME);
        baseStaffSiteOrgDto.setTraderTypeEbs(trader.getTraderTypeEbs());
        baseStaffSiteOrgDto.setAccountingOrg(trader.getAccountingOrg());
        baseStaffSiteOrgDto.setAirTransport(trader.getAirTransport());
        baseStaffSiteOrgDto.setSitePhone(trader.getTelephone());
        baseStaffSiteOrgDto.setPhone(trader.getContactMobile());
        return baseStaffSiteOrgDto;
    }
}
