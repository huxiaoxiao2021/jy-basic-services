package com.jdl.basic.provider.core.service.site;

import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jd.ql.basic.domain.BaseSite;
import com.jd.ql.basic.domain.PsStoreInfo;
import com.jd.ql.basic.dto.BaseSiteSimpleDto;
import com.jd.ql.basic.dto.PageDto;
import com.jd.ql.basic.util.PageUtil;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.api.dto.site.AreaVO;
import com.jdl.basic.api.dto.site.BasicSiteVO;
import com.jdl.basic.api.dto.site.ProvinceAgencyVO;
import com.jdl.basic.api.dto.site.SiteQueryCondition;
import com.jdl.basic.api.enums.WorkSiteTypeEnum;
import com.jdl.basic.api.service.site.SiteQueryService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.enums.BasicAreaEnum;
import com.jdl.basic.provider.core.enums.BasicProvinceAgencyEnum;
import com.jdl.basic.provider.core.manager.basicSiteQueryWS.IBasicSiteQueryWSManager;
import com.jdl.basic.rpc.exception.JYBasicRpcException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("siteQueryService")
public class BaseSiteQueryServiceImpl implements SiteQueryService {



    @Autowired
    private IBasicSiteQueryWSManager basicSiteQueryWSManager;


    @Cache(key = "SiteQueryService.queryAllProvinceAgencyInfo", memoryEnable = true, memoryExpiredTime = 30 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 60 * 60 * 1000)
    @Override
    public Result<List<ProvinceAgencyVO>> queryAllProvinceAgencyInfo() {
        Result<List<ProvinceAgencyVO>> result = new Result<>();
        result.toSuccess();
        result.setData(Arrays.stream(BasicProvinceAgencyEnum.values()).map(item -> {
            ProvinceAgencyVO provinceAgencyVO = new ProvinceAgencyVO();
            provinceAgencyVO.setProvinceAgencyCode(item.getCode());
            provinceAgencyVO.setProvinceAgencyName(item.getName());
            return provinceAgencyVO;
        }).collect(Collectors.toList()));
        return result;
    }

    @Cache(key = "SiteQueryService.queryAllAreaInfo", memoryEnable = true, memoryExpiredTime = 30 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 60 * 60 * 1000)
    @Override
    public Result<List<AreaVO>> queryAllAreaInfo() {
        Result<List<AreaVO>> result = new Result<>();
        result.toSuccess();
        result.setData(Arrays.stream(BasicAreaEnum.values()).map(item -> {
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaCode(item.getCode());
            areaVO.setAreaName(item.getName());
            return areaVO;
        }).collect(Collectors.toList()));
        return result;
    }

    @Cache(key = "SiteQueryService.queryProvinceAgencyInfoByCode@args0", memoryEnable = true, memoryExpiredTime = 30 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 60 * 60 * 1000)
    @Override
    public Result<ProvinceAgencyVO> queryProvinceAgencyInfoByCode(String provinceAgencyCode) {
        Result<ProvinceAgencyVO> result = new Result<>();
        result.toSuccess();
        Optional<ProvinceAgencyVO> first = Arrays.stream(BasicProvinceAgencyEnum.values()).filter(item -> Objects.equals(item.getCode(), provinceAgencyCode)).map(item -> {
            ProvinceAgencyVO provinceAgencyVO = new ProvinceAgencyVO();
            provinceAgencyVO.setProvinceAgencyCode(item.getCode());
            provinceAgencyVO.setProvinceAgencyName(item.getName());
            return provinceAgencyVO;
        }).findFirst();
        if(first.isPresent()){
            result.setData(first.get());
        }else {
            result.toFail("未查询到省区信息!");
        }
        return result;
    }

    @Cache(key = "SiteQueryService.queryAreaVOInfoByCode@args0", memoryEnable = true, memoryExpiredTime = 30 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 60 * 60 * 1000)
    @Override
    public Result<AreaVO> queryAreaVOInfoByCode(String areaCode) {
        Result<AreaVO> result = new Result<>();
        result.toSuccess();
        Optional<AreaVO> first = Arrays.stream(BasicAreaEnum.values()).filter(item -> Objects.equals(item.getCode(), areaCode)).map(item -> {
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaCode(item.getCode());
            areaVO.setAreaName(item.getName());
            return areaVO;
        }).findFirst();
        if(first.isPresent()){
            result.setData(first.get());
        }else {
            result.toFail("未查询到枢纽信息!");
        }
        return result;
    }

    @Override
    public Result<Pager<BasicSiteVO>> queryJySiteByConditionFromBasicSite(Pager<SiteQueryCondition> siteQueryPager) {
        checkSiteQueryPager(siteQueryPager);
        Result<Pager<BasicSiteVO>> result = assemblePageResult(siteQueryPager);
        if(checkIsQueryJySite(siteQueryPager.getSearchVo().getSubTypes())){
            PageDto<Object> pageDto = new PageDto<>();
            pageDto.setCurPage(siteQueryPager.getPageNo());
            pageDto.setPageSize(siteQueryPager.getPageSize());
            PageDto<List<BaseSiteSimpleDto>> pageSiteResult = basicSiteQueryWSManager.querySiteByCondition(convertSiteQuery(siteQueryPager.getSearchVo()), pageDto);
            if(pageSiteResult != null && CollectionUtils.isNotEmpty(pageSiteResult.getData())){
                result.getData().setTotal((long) pageSiteResult.getTotalRow());
                result.getData().getData().addAll(convertBasicSiteList(pageSiteResult.getData()));
            }
        }
        return result;
    }

    private List<BasicSiteVO> convertBasicSiteList(List<BaseSiteSimpleDto> data) {
        return data.stream().map(baseSiteSimpleDto -> {
            BasicSiteVO basicSiteVO =new BasicSiteVO();
            basicSiteVO.setSiteCode(baseSiteSimpleDto.getSiteCode());
            basicSiteVO.setSiteName(baseSiteSimpleDto.getSiteName());
            //basicSiteVO.setProvinceAgencyCode(baseSiteSimpleDto.getProvinceAgencyCode());
            //basicSiteVO.setProvinceAgencyName(baseSiteSimpleDto.getProvinceAgencyName());
            return basicSiteVO;
        }).collect(Collectors.toList());
    }

    private Result<Pager<BasicSiteVO>> assemblePageResult(Pager<SiteQueryCondition> siteQueryPager) {
        Result result =new Result();
        Pager<BasicSiteVO> pager =new Pager();
        pager.setPageNo(siteQueryPager.getPageNo());
        pager.setPageSize(siteQueryPager.getPageSize());
        List<BasicSiteVO> basicSiteVOList =new ArrayList<>();
        pager.setData(basicSiteVOList);
        result.setData(pager);
        return result;
    }

    private BaseSite convertSiteQuery(SiteQueryCondition siteQueryCondition) {
        BaseSite siteQuery = new BaseSite();
        //siteQuery.setSubTypeList(siteQueryCondition.getSubTypes());
        return siteQuery;
    }

    private boolean checkIsQueryJySite(List<Integer> subTypes) {
        if (CollectionUtils.isNotEmpty(subTypes)){
            for (Integer subType: subTypes){
                if (null ==WorkSiteTypeEnum.getWorkingSiteTypeBySubType(subType)){
                    return false;
                }
            }
        }
        return true;
    }

    private void checkSiteQueryPager(Pager<SiteQueryCondition> siteQueryPager) {
        if (ObjectHelper.isEmpty(siteQueryPager.getPageNo())){
            throw  new JYBasicRpcException("参数异常：缺失分页页码！");
        }
        if (ObjectHelper.isEmpty(siteQueryPager.getPageSize())){
            throw  new JYBasicRpcException("参数异常：缺失分页数量！");
        }
        if (ObjectHelper.isEmpty(siteQueryPager.getSearchVo())){
            throw  new JYBasicRpcException("参数异常：缺失查询条件！");
        }
        if (CollectionUtils.isEmpty(siteQueryPager.getSearchVo().getSubTypes())){
            throw  new JYBasicRpcException("参数异常：缺失子站点类型！");
        }
        if (siteQueryPager.getPageSize()> Constants.DEFAULT_PAGE_SIZE_QUERY_USER){
            throw  new JYBasicRpcException("参数异常：查询数量过大！");
        }
    }
}
