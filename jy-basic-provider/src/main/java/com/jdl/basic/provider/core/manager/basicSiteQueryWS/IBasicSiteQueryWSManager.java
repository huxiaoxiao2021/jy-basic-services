package com.jdl.basic.provider.core.manager.basicSiteQueryWS;

import com.jd.ql.basic.domain.BaseSite;
import com.jd.ql.basic.dto.BaseSiteInfoDto;
import com.jd.ql.basic.dto.BaseSiteSimpleDto;
import com.jd.ql.basic.dto.PageDto;
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

    PageDto<List<BaseSiteSimpleDto>> querySiteByCondition(BaseSite site, PageDto pageDto);
}
