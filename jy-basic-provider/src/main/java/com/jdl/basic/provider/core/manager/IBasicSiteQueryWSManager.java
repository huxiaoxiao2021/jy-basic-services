package com.jdl.basic.provider.core.manager;

import com.jd.ql.basic.dto.BaseSiteInfoDto;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import java.util.List;

/**
 * @author liwenji
 * @description TODO
 * @date 2022-11-14 20:07
 */
public interface IBasicSiteQueryWSManager {

    /**
     * 根据网点ID（或分拣中心ID）查询取详细信息
     * @param siteCode
     * @return
     */
    BaseSiteInfoDto getBaseSiteInfoBySiteId(Integer siteCode);


    /**
     * 根据省区编码和站点子类型subType分页获取站点
     * @param provinceAgencyCode
     * @param subTypes
     * @param pageIndex
     * @return
     */
    List<BaseStaffSiteOrgDto> getBaseSiteByProvinceAgencyCodeSubTypePage(String provinceAgencyCode,
        List<Integer> subTypes, Integer pageIndex);


}
