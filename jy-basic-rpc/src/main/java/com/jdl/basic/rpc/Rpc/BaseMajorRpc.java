package com.jdl.basic.rpc.Rpc;

import com.jd.ldop.basic.api.BasicTraderAPI;
import com.jd.ldop.basic.dto.BasicTraderInfoDTO;
import com.jd.ldop.basic.dto.ResponseDTO;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.ws.BasicPrimaryWS;
import com.jdl.basic.common.contants.BaseContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;



/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/27 17:16
 * @Description: 基础资料信息
 */
@Service("baseMajorRpc")
public class BaseMajorRpc {


    @Autowired
    @Qualifier("basicPrimaryWS")
    private BasicPrimaryWS basicPrimaryWS;

    @Autowired
    @Qualifier("basicTraderAPI")
    private BasicTraderAPI basicTraderAPI;

    public BaseStaffSiteOrgDto getBaseSiteBySiteId(Integer paramInteger) {
        try{
            BaseStaffSiteOrgDto dtoStaff = basicPrimaryWS.getBaseSiteBySiteId(paramInteger);
            ResponseDTO<BasicTraderInfoDTO> responseDTO = null;
            if (dtoStaff != null)
                return dtoStaff;
            else
                dtoStaff = basicPrimaryWS.getBaseStoreByDmsSiteId(paramInteger);
            if (dtoStaff != null)
                return dtoStaff;
            else
                responseDTO = basicTraderAPI.getBasicTraderById(paramInteger);

            if (responseDTO != null && responseDTO.getResult() != null)
                dtoStaff = getBaseStaffSiteOrgDtoFromTrader(responseDTO.getResult());
            return dtoStaff;
        }catch (Exception e){
            throw e;
        }
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
