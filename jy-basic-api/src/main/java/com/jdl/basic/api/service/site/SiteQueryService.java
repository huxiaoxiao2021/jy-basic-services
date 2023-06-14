package com.jdl.basic.api.service.site;

import com.jdl.basic.api.dto.site.AreaVO;
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
     * 查询省区下所有枢纽
     *
     * @return
     */
    Result<List<AreaVO>> queryAllAreaInfo(String provinceAgencyCode);

    /**
     * 根据省区编码查询省区信息
     *
     * @return
     */
    Result<ProvinceAgencyVO> queryProvinceAgencyInfoByCode(String provinceAgencyCode);

    /**
     * 根据枢纽编码查询枢纽信息
     *
     * @return
     */
    Result<AreaVO> queryAreaVOInfoByCode(String areaCode);

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

    /**
     * 根据省区编码查询省区下的分拣中心场地
     * @param siteQueryPager
     * @return
     */
    Result<Pager<BasicSiteVO>> getFJSiteByProvinceAgencyCode(Pager<SiteQueryCondition> siteQueryPager);

}
