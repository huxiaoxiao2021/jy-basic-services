package com.jdl.basic.provider.core.service.site;

import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jdl.basic.api.dto.site.AreaVO;
import com.jdl.basic.api.dto.site.ProvinceAgencyVO;
import com.jdl.basic.api.service.site.SiteQueryService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.enums.BasicAreaEnum;
import com.jdl.basic.provider.core.enums.BasicProvinceAgencyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("siteQueryService")
public class BaseSiteQueryServiceImpl implements SiteQueryService {





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
}
