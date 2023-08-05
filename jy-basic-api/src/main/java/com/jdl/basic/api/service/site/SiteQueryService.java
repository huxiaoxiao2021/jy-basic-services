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
     * 查询所有枢纽
     *
     * @return
     */
    Result<List<AreaVO>> queryAllAreaInfo();

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
     * 查询拣运场地
     * @param siteQueryPager
     * @return
     */
    Result<Pager<BasicSiteVO>> queryJySiteByConditionFromBasicSite(Pager<SiteQueryCondition> siteQueryPager);
}
