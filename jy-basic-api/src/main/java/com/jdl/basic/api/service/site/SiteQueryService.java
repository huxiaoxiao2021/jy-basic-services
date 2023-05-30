package com.jdl.basic.api.service.site;

import com.jdl.basic.api.dto.site.BasicSiteVO;
import com.jdl.basic.api.dto.site.ProvinceAgencyVO;
import com.jdl.basic.api.dto.site.SiteQueryCondition;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface SiteQueryService {

    /**
     * 查询所有省区
     * 
     * @return
     */
    Result<List<ProvinceAgencyVO>> queryAllProvinceAgencyInfo();
    
    /**
     * 根据条件查询站点
     * @param siteQueryCondition
     * @param limit
     * @return
     */
    Result<List<BasicSiteVO>> querySiteByConditionFromBasicSite(SiteQueryCondition siteQueryCondition, Integer limit);

    /**
     * 分页查询
     * @param siteQueryPager 分页查询参数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-10-12 14:06:56 周三
     */
    Result<Pager<BasicSiteVO>> querySitePageByConditionFromBasicSite(Pager<SiteQueryCondition> siteQueryPager);
}
