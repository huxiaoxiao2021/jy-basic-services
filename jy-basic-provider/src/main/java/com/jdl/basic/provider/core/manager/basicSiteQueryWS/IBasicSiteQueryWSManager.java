package com.jdl.basic.provider.core.manager.basicSiteQueryWS;

import com.jd.ql.basic.dto.BaseSiteInfoDto;

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
}
