package com.jdl.basic.provider.core.service.site;

import com.jdl.basic.provider.dto.BasicPsStoreInfo;
import com.jdl.basic.provider.dto.BasicSiteChangeMQ;

public interface SiteService {

    /**
     * 初始化所有站点
     */
    void syncBasicSite();

    /**
     * 初始化所有仓
     */
    void syncAllWmsNew();

    /**
     * 更新仓信息
     * @param storeInfo
     */
    void updateWmsDto(BasicPsStoreInfo storeInfo);

    /**
     * 更新站点
     * @param basicSiteChangeMQ
     */
    void updateBasicSite(BasicSiteChangeMQ basicSiteChangeMQ);
    
}
