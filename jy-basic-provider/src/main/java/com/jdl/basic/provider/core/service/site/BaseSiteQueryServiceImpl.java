package com.jdl.basic.provider.core.service.site;

import com.google.common.collect.Lists;
import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jd.ql.basic.domain.BaseSite;
import com.jd.ql.basic.domain.PsStoreInfo;
import com.jd.ql.basic.dto.BaseSiteSimpleDto;
import com.jd.ql.basic.dto.PageDto;
import com.jd.ql.basic.util.PageUtil;
import com.jd.ump.profiler.proxy.Profiler;
import com.jd.ql.basic.domain.BaseOrganStruct;
import com.jd.ql.basic.domain.BaseSite;
import com.jd.ql.basic.domain.PsStoreInfo;
import com.jd.ql.basic.dto.BaseSiteSimpleDto;
import com.jd.ql.basic.dto.PageDto;
import com.jd.ql.basic.dto.PsStoreInfoRequest;
import com.jd.ql.basic.enums.DmsStoreTypeEnum;
import com.jd.ql.basic.util.PageUtil;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.api.dto.site.AreaVO;
import com.jdl.basic.api.dto.site.BasicSiteVO;
import com.jdl.basic.api.dto.site.ProvinceAgencyVO;
import com.jdl.basic.api.dto.site.SiteQueryCondition;
import com.jdl.basic.api.dto.site.SiteQueryCondition;
import com.jdl.basic.api.enums.WorkSiteTypeEnum;
import com.jdl.basic.api.service.site.SiteQueryService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.NumberHelper;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.basic.BasicSiteEsDao;
import com.jdl.basic.provider.core.enums.BasicAreaEnum;
import com.jdl.basic.provider.core.enums.BasicProvinceAgencyEnum;
import com.jdl.basic.provider.core.enums.SiteOperateStateEnum;
import com.jdl.basic.provider.core.manager.BasicOrganStructWSManager;
import com.jdl.basic.provider.core.manager.BasicWareHouseWSManager;
import com.jdl.basic.provider.core.manager.IBasicSiteQueryWSManager;
import com.jdl.basic.provider.core.po.BasicSiteEsDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import com.jdl.basic.provider.core.manager.basicSiteQueryWS.IBasicSiteQueryWSManager;
import com.jdl.basic.rpc.exception.JYBasicRpcException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("siteQueryService")
public class BaseSiteQueryServiceImpl implements SiteQueryService {

    private static final Logger logger = LoggerFactory.getLogger(BaseSiteQueryServiceImpl.class);

    // 截取最长长度
    private static final int subRetainLength = 20;
    // 最大limit
    private static final int MAX_LIMIT = 100;

    @Autowired
    private BasicSiteEsDao basicSiteEsDao;

    @Autowired
    private IBasicSiteQueryWSManager basicSiteQueryWSManager;

    @Autowired
    private BasicOrganStructWSManager basicOrganStructWSManager;

    @Autowired
    private BasicWareHouseWSManager basicWareHouseWSManager;

    @Override
    public Result<List<ProvinceAgencyVO>> queryAllProvinceAgencyInfo() {
        return queryAllProvinceAgencyInfo4Cache();
    }

    @Cache(key = "SiteQueryService.queryAllProvinceAgencyInfo4Cache", memoryEnable = true, memoryExpiredTime = 30 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 60 * 60 * 1000)
    public Result<List<ProvinceAgencyVO>> queryAllProvinceAgencyInfo4Cache() {
        Result<List<ProvinceAgencyVO>> result = new Result<>();
        result.toSuccess();
        result.setData(Arrays.stream(BasicProvinceAgencyEnum.values()).map(item -> {
            ProvinceAgencyVO provinceAgencyVO = new ProvinceAgencyVO();
            provinceAgencyVO.setProvinceAgencyCode(item.getCode());
            provinceAgencyVO.setProvinceAgencyName(item.getName());
            //总公司下包含枢纽
            if(item.equals(BasicProvinceAgencyEnum.WULIUZONGBU)){
                provinceAgencyVO.setHasAreaFlag(Boolean.TRUE);
            }
            return provinceAgencyVO;
        }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public Result<List<AreaVO>> queryAllAreaInfo(String provinceAgencyCode) {
        return queryAllAreaInfo4Cache(provinceAgencyCode);
    }

    @Cache(key = "SiteQueryService.queryAllAreaInfo4Cache@args0", memoryEnable = true, memoryExpiredTime = 30 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 60 * 60 * 1000)
    public Result<List<AreaVO>> queryAllAreaInfo4Cache(String provinceAgencyCode) {
        Result<List<AreaVO>> result = new Result<>();
        result.toSuccess();
        result.setData(Lists.newArrayList());
        if(BasicProvinceAgencyEnum.WULIUZONGBU.getCode().equals(provinceAgencyCode)){
            //目前仅支持总部省区下返回枢纽
            List<BaseOrganStruct> list = basicOrganStructWSManager.getBaseOrganStructByParentCode(provinceAgencyCode);
            if(CollectionUtils.isNotEmpty(list)){
                result.getData().addAll(list.stream().map(item -> {
                    AreaVO areaVO = new AreaVO();
                    areaVO.setAreaCode(item.getOrganCode());
                    areaVO.setAreaName(item.getOrganName());
                    return areaVO;
                }).collect(Collectors.toList()));
            }
        }
        return result;
    }

    @Override
    public Result<ProvinceAgencyVO> queryProvinceAgencyInfoByCode(String provinceAgencyCode) {
        return queryProvinceAgencyInfoByCode4Cache(provinceAgencyCode);
    }

    @Cache(key = "SiteQueryService.queryProvinceAgencyInfoByCode4Cache@args0", memoryEnable = true, memoryExpiredTime = 30 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 60 * 60 * 1000)
    public Result<ProvinceAgencyVO> queryProvinceAgencyInfoByCode4Cache(String provinceAgencyCode) {
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

    @Override
    public Result<AreaVO> queryAreaVOInfoByCode(String areaCode) {
        return queryAreaVOInfoByCode4Cache(areaCode);
    }

    @Cache(key = "SiteQueryService.queryAreaVOInfoByCode4Cache@args0", memoryEnable = true, memoryExpiredTime = 30 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 60 * 60 * 1000)
    public Result<AreaVO> queryAreaVOInfoByCode4Cache(String areaCode) {
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

    /**
     * 根据条件查询站点
     * @param siteQueryCondition
     * @param limit
     * @return
     */
    @Override
    public Result<List<BasicSiteVO>> querySiteByConditionFromBasicSite(SiteQueryCondition siteQueryCondition, Integer limit) {
        Result<List<BasicSiteVO>> baseEntity = new Result<List<BasicSiteVO>>();
        baseEntity.setData(Lists.newArrayList());
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.api.service.site.SiteQueryService.querySiteFromBasic",
                false, true);
        try {
            limit = limit > MAX_LIMIT ? MAX_LIMIT : limit;
            // 先查站点数据
            if(checkIsQuerySite(siteQueryCondition.getSiteTypes())){
                PageDto<Object> pageDto = new PageDto<>();
                pageDto.setCurPage(Constants.CONSTANT_NUMBER_ONE);
                pageDto.setPageSize(limit);
                PageDto<List<BaseSiteSimpleDto>> pageSiteResult = basicSiteQueryWSManager.querySiteByCondition(convertOutSiteQuery(siteQueryCondition), pageDto);
                if(pageSiteResult != null && CollectionUtils.isNotEmpty(pageSiteResult.getData())){
                    baseEntity.getData().addAll(convert2OwnBasicSiteList(pageSiteResult.getData()));
                }
            }
            // 在查库房数据
            if(checkIsQueryWare(siteQueryCondition.getSiteTypes())){
                PageUtil pageUtil = new PageUtil();
                pageUtil.setCurPage(Constants.CONSTANT_NUMBER_ONE);
                pageUtil.setPageSize(limit);
                PageDto<List<PsStoreInfo>> pageWareResult = basicWareHouseWSManager.query(convertOutWareQuery(siteQueryCondition), pageUtil);
                if(pageWareResult != null && CollectionUtils.isNotEmpty(pageWareResult.getData())){
                    baseEntity.getData().addAll(wareConvert2OwnBasicSiteList(pageWareResult.getData()));
                }
            }
        }catch (Exception e){
            logger.error("根据条件{}查询站点异常!", JsonHelper.toJSONString(siteQueryCondition), e);
            baseEntity.toFail("服务异常!");
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return baseEntity;
    }

    private BaseSite convertOutSiteQuery(SiteQueryCondition siteQueryCondition) {
        BaseSite siteQuery = new BaseSite();
        siteQuery.setOrgId(siteQueryCondition.getOrgId());
        siteQuery.setProvinceAgencyCode(siteQueryCondition.getProvinceAgencyCode());
        siteQuery.setAreaCode(siteQueryCondition.getAreaCode());
        siteQuery.setProvinceId(siteQueryCondition.getProvinceId());
        siteQuery.setCityId(siteQueryCondition.getCityId());
        siteQuery.setCountryId(siteQueryCondition.getCountryId());
        siteQuery.setSiteCode(siteQueryCondition.getSiteCode());
        siteQuery.setSiteName(siteQueryCondition.getSiteName());
        siteQuery.setSiteNamePym(siteQueryCondition.getSiteNamePym());
        siteQuery.setDmsCode(siteQueryCondition.getDmsCode());
        siteQuery.setSiteTypeList(siteQueryCondition.getSiteTypes());
        siteQuery.setSubTypeList(siteQueryCondition.getSubTypes());
        if(StringUtils.isNotEmpty(siteQueryCondition.getSearchStr())){
            if(NumberHelper.isNumber(siteQueryCondition.getSearchStr())){
                // 数字，则根据站点id查询
                siteQuery.setSiteCode(Integer.valueOf(siteQueryCondition.getSearchStr()));
            }else {
                // 非数字则根据站点名称模糊查询
                siteQuery.setSiteName(siteQueryCondition.getSearchStr());
            }
        }
        return siteQuery;
    }

    private BasicSiteVO convertOwnBasicSite(BaseSiteSimpleDto item) {
        BasicSiteVO basicSiteVO = new BasicSiteVO();
        basicSiteVO.setOrgId(item.getOrgId());
        basicSiteVO.setProvinceAgencyCode(item.getProvinceAgencyCode());
        basicSiteVO.setProvinceAgencyName(item.getProvinceAgencyName());
        basicSiteVO.setAreaCode(item.getAreaCode());
        // 获取枢纽名称
        Result<List<AreaVO>> areaResult = queryAllAreaInfo(BasicProvinceAgencyEnum.WULIUZONGBU.getCode());
        if(areaResult != null && CollectionUtils.isNotEmpty(areaResult.getData())){
            List<String> areaCodeList = areaResult.getData()
                    .stream()
                    .map(AreaVO::getAreaCode)
                    .filter(areaCode -> Objects.equals(item.getAreaCode(), areaCode))
                    .collect(Collectors.toList());
            basicSiteVO.setAreaName(CollectionUtils.isNotEmpty(areaCodeList) ? areaCodeList.get(0) : null);
        }
        basicSiteVO.setProvinceId(item.getProvinceId());
        basicSiteVO.setProvinceName(item.getProvinceName());
        basicSiteVO.setCityId(item.getCityId());
        basicSiteVO.setSiteCode(item.getSiteCode());
        basicSiteVO.setDmsSiteCode(item.getDmsCode());
        basicSiteVO.setSiteName(item.getSiteName());
        basicSiteVO.setSiteType(item.getSiteType());
        basicSiteVO.setSubType(item.getSubType());
        return basicSiteVO;
    }

    private BasicSiteVO wareConvertOwnBasicSite(PsStoreInfo item) {
        BasicSiteVO basicSiteVO = new BasicSiteVO();
        basicSiteVO.setOrgId(item.getOrgId());
        basicSiteVO.setOrgName(item.getOrgName());
        basicSiteVO.setProvinceAgencyCode(item.getProvinceAgencyCode());
        basicSiteVO.setProvinceAgencyName(item.getProvinceAgencyName());
        basicSiteVO.setProvinceId(item.getProvinceId());
        basicSiteVO.setProvinceName(item.getProvinceName());
        basicSiteVO.setCityId(item.getCityId());
        basicSiteVO.setSiteCode(item.getDmsSiteId());
        basicSiteVO.setDmsSiteCode(item.getDmsCode());
        basicSiteVO.setSiteName(item.getDmsStoreName());
        return basicSiteVO;
    }

    private Result<List<BasicSiteVO>> querySiteFromES(SiteQueryCondition siteQueryCondition, Integer limit) {
        Result<List<BasicSiteVO>> baseEntity = new Result<List<BasicSiteVO>>();
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.api.service.site.SiteQueryService.querySiteFromES",
                false, true);
        try {
            // 构建es查询条件
            BoolQueryBuilder boolQueryBuilder = createQueryCondition(siteQueryCondition);
            List<BasicSiteEsDto> streamlinedBasicSiteEsDtoList = basicSiteEsDao.searchListByQueryParam(boolQueryBuilder,
                    Constants.CONSTANT_NUMBER_ZERO, limit, BasicSiteEsDto.class);
            List<BasicSiteVO> basicSiteVOS =new ArrayList<BasicSiteVO>();
            if(CollectionUtils.isNotEmpty(streamlinedBasicSiteEsDtoList)){
                streamlinedBasicSiteEsDtoList.forEach(streamlinedBasicSiteEsDto -> {
                    BasicSiteVO streamLinedBasicSiteVO = new BasicSiteVO();
                    BeanUtils.copyProperties(streamlinedBasicSiteEsDto, streamLinedBasicSiteVO);
                    basicSiteVOS.add(streamLinedBasicSiteVO);
                });
            }
            baseEntity.setData(basicSiteVOS);
        }catch (Exception e){
            logger.error("根据条件{}查询站点异常!", JsonHelper.toJSONString(siteQueryCondition), e);
            baseEntity.toFail("服务异常!");
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return baseEntity;
    }

    /**
     * 构建es查询条件
     * @param siteQueryCondition 查询条件
     * @return
     */
    private BoolQueryBuilder createQueryCondition(SiteQueryCondition siteQueryCondition) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(BasicSiteEsDto.YN, Constants.CONSTANT_NUMBER_ONE));
        // 站点id
        if(siteQueryCondition.getSiteCode() != null){
            boolQueryBuilder.filter(QueryBuilders.termQuery(BasicSiteEsDto.SITE_CODE, siteQueryCondition.getSiteCode()));
        }
        // 站点编码
        if(StringUtils.isNotEmpty(siteQueryCondition.getDmsCode())){
            WildcardQueryBuilder dmsSiteCodeWildcardQueryBuilder = QueryBuilders.wildcardQuery(BasicSiteEsDto.DMS_SITE_CODE,
                    Constants.CONSTANT_SPECIAL_MARK_ASTERISK + subOverLength(siteQueryCondition.getDmsCode()) + Constants.CONSTANT_SPECIAL_MARK_ASTERISK);
            boolQueryBuilder.filter(dmsSiteCodeWildcardQueryBuilder);
        }
        // 站点名称
        if(StringUtils.isNotEmpty(siteQueryCondition.getSiteName())){
            WildcardQueryBuilder siteNameWildcardQueryBuilder = QueryBuilders.wildcardQuery(BasicSiteEsDto.SITE_NAME,
                    Constants.CONSTANT_SPECIAL_MARK_ASTERISK + subOverLength(siteQueryCondition.getSiteName()) + Constants.CONSTANT_SPECIAL_MARK_ASTERISK);
            boolQueryBuilder.filter(siteNameWildcardQueryBuilder);
        }
        // 站点名称拼音码模糊查询
        if(StringUtils.isNotEmpty(siteQueryCondition.getSiteNamePym())){
            WildcardQueryBuilder siteNameWildcardQueryBuilder = QueryBuilders.wildcardQuery(BasicSiteEsDto.SITE_NAME_PYM,
                    Constants.CONSTANT_SPECIAL_MARK_ASTERISK + subOverLength(siteQueryCondition.getSiteNamePym()) + Constants.CONSTANT_SPECIAL_MARK_ASTERISK);
            boolQueryBuilder.filter(siteNameWildcardQueryBuilder);
        }
        // 站点类型
        if(CollectionUtils.isNotEmpty(siteQueryCondition.getSiteTypes())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.SITE_TYPE, siteQueryCondition.getSiteTypes()));
        }
        // 站点子类型
        if(CollectionUtils.isNotEmpty(siteQueryCondition.getSubTypes())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.SUB_TYPE, siteQueryCondition.getSubTypes()));
        }
        // 省区编码
        if(StringUtils.isNotEmpty(siteQueryCondition.getProvinceAgencyCode())){
            boolQueryBuilder.filter(QueryBuilders.termQuery(BasicSiteEsDto.PROVINCE_AGENCY_CODE, siteQueryCondition.getProvinceAgencyCode()));
        }
        // 枢纽编码
        if(StringUtils.isNotEmpty(siteQueryCondition.getAreaCode())){
            boolQueryBuilder.filter(QueryBuilders.termQuery(BasicSiteEsDto.AREA_CODE, siteQueryCondition.getAreaCode()));
        }
        // 省ID
        if(siteQueryCondition.getProvinceId() != null){
            boolQueryBuilder.filter(QueryBuilders.termQuery(BasicSiteEsDto.PROVINCE_ID, siteQueryCondition.getProvinceId()));
        }
        // 市ID
        if(siteQueryCondition.getCityId() != null){
            boolQueryBuilder.filter(QueryBuilders.termQuery(BasicSiteEsDto.CITY_ID, siteQueryCondition.getCityId()));
        }
        // 县ID
        if(siteQueryCondition.getCountryId() != null){
            boolQueryBuilder.filter(QueryBuilders.termQuery(BasicSiteEsDto.COUNTRY_ID, siteQueryCondition.getCountryId()));
        }
        // 模糊地址
        if(StringUtils.isNotEmpty(siteQueryCondition.getAddress())){
            WildcardQueryBuilder addressWildcardQueryBuilder = QueryBuilders.wildcardQuery(BasicSiteEsDto.ADDRESS,
                    Constants.CONSTANT_SPECIAL_MARK_ASTERISK + subOverLength(siteQueryCondition.getAddress()) + Constants.CONSTANT_SPECIAL_MARK_ASTERISK);
            boolQueryBuilder.filter(addressWildcardQueryBuilder);
        }
        // 站点运营状态：线上、线下、不存在、无运营状态
        BoolQueryBuilder shouldBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.termsQuery(BasicSiteEsDto.OPERATE_STATE, effectiveOperateState()))
                .should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(BasicSiteEsDto.OPERATE_STATE)));
        boolQueryBuilder.filter(shouldBuilder);

        // 关键字搜索
        if(StringUtils.isNotBlank(siteQueryCondition.getSearchStr())){
            BoolQueryBuilder searchShouldBuilder = QueryBuilders.boolQuery()
                    .should(QueryBuilders.wildcardQuery(BasicSiteEsDto.SITE_NAME,
                            Constants.CONSTANT_SPECIAL_MARK_ASTERISK + subOverLength(siteQueryCondition.getSearchStr()) + Constants.CONSTANT_SPECIAL_MARK_ASTERISK))
                    .should(QueryBuilders.wildcardQuery(BasicSiteEsDto.SITE_NAME_PYM,
                            Constants.CONSTANT_SPECIAL_MARK_ASTERISK + subOverLength(siteQueryCondition.getSearchStr()) + Constants.CONSTANT_SPECIAL_MARK_ASTERISK));
            if(NumberUtils.isNumber(siteQueryCondition.getSearchStr())){
                final Long siteCodeQuery = NumberUtils.createLong(siteQueryCondition.getSearchStr());
                if(siteCodeQuery >= Integer.MIN_VALUE && siteCodeQuery <= Integer.MAX_VALUE){
                    searchShouldBuilder.should(QueryBuilders.termQuery(BasicSiteEsDto.SITE_CODE, siteCodeQuery));
                }
            }
            boolQueryBuilder.filter(searchShouldBuilder);
        }
        return boolQueryBuilder;
    }

    private SortBuilder createSortBuilder() {
        SortBuilder sortBuilder = SortBuilders.fieldSort(BasicSiteEsDto.SITE_CODE).order(SortOrder.ASC);
        return sortBuilder;
    }


    /**
     * 截取超长字段
     *
     * @param overLengthColumn
     */
    private String subOverLength(String overLengthColumn) {
        if(StringUtils.isEmpty(overLengthColumn)){
            return overLengthColumn;
        }
        return overLengthColumn.length() > subRetainLength ? overLengthColumn.substring(Constants.CONSTANT_NUMBER_ZERO, subRetainLength) : overLengthColumn;
    }


    /**
     * 获取满足条件的运营状态
     *  <p>
     *     站点：线上运营：1、线下运营：2、部分历史站点无运营状态：null
     *     仓：无运营状态：-1
     *  </p>
     * @return
     */
    private List<Integer> effectiveOperateState() {
        return Arrays.asList(SiteOperateStateEnum.SITE_OPERATE_STATE_ONLINE.getCode(),
                SiteOperateStateEnum.SITE_OPERATE_STATE_OFFLINE.getCode(),
                SiteOperateStateEnum.SITE_OPERATE_STATE_NOT_EXIST.getCode());
    }

    /**
     * 分页查询
     *
     * @param siteQueryPager 分页查询参数
     * @return 分页数据
     * @author fanggang7
     * @time 2022-10-12 14:06:56 周三
     */
    @Override
    public Result<Pager<BasicSiteVO>> querySitePageByConditionFromBasicSite(Pager<SiteQueryCondition> siteQueryPager) {
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.api.service.site.SiteQueryService.queryPageSiteFromExternal",
                false, true);
        Result<Pager<BasicSiteVO>> result = new Result<Pager<BasicSiteVO>>();
        final Result<Void> checkResult = this.checkParam4querySitePageByConditionFromBasicSite(siteQueryPager);
        if (!checkResult.isSuccess()) {
            result.setCode(checkResult.getCode());
            result.setMessage(checkResult.getMessage());
            return result;
        }
        Result<Pager<BasicSiteVO>> baseEntity = initPageResult(siteQueryPager);
        try {
            // 先查站点数据
            if(checkIsQuerySite(siteQueryPager.getSearchVo().getSiteTypes())){
                PageDto<Object> pageDto = new PageDto<>();
                pageDto.setCurPage(siteQueryPager.getPageNo());
                pageDto.setPageSize(siteQueryPager.getPageSize());
                PageDto<List<BaseSiteSimpleDto>> pageSiteResult = basicSiteQueryWSManager.querySiteByCondition(convertOutSiteQuery(siteQueryPager.getSearchVo()), pageDto);
                if(pageSiteResult != null && CollectionUtils.isNotEmpty(pageSiteResult.getData())){
                    baseEntity.getData().setTotal((long) pageSiteResult.getTotalRow());
                    baseEntity.getData().getData().addAll(convert2OwnBasicSiteList(pageSiteResult.getData()));
                }
            }
            // 在查库房数据
            if(checkIsQueryWare(siteQueryPager.getSearchVo().getSiteTypes())){
                PageUtil pageUtil = new PageUtil();
                pageUtil.setCurPage(siteQueryPager.getPageNo());
                pageUtil.setPageSize(siteQueryPager.getPageSize());
                PageDto<List<PsStoreInfo>> pageWareResult = basicWareHouseWSManager.query(convertOutWareQuery(siteQueryPager.getSearchVo()), pageUtil);
                if(pageWareResult != null && CollectionUtils.isNotEmpty(pageWareResult.getData())){
                    baseEntity.getData().setTotal(baseEntity.getData().getTotal() + pageWareResult.getTotalRow());
                    baseEntity.getData().getData().addAll(wareConvert2OwnBasicSiteList(pageWareResult.getData()));
                }
            }
        }catch (Exception e){
            logger.error("根据条件{}分页查询站点异常!", JsonHelper.toJSONString(siteQueryPager), e);
            baseEntity.toFail("服务异常!");
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return baseEntity;
    }

    /**
     * 根据站点类型判断是否查询站点数据（只要有非库房类型则需要查询）
     *
     * @param siteTypes
     * @return
     */
    private boolean checkIsQuerySite(List<Integer> siteTypes) {
        if(CollectionUtils.isNotEmpty(siteTypes)){
            // 所有库房类型
            List<Integer> wmsTypes = Arrays.stream(DmsStoreTypeEnum.values())
                    .map(item -> Integer.parseInt(item.getDmsStoreType()))
                    .collect(Collectors.toList());
            return siteTypes.stream().anyMatch(item -> !wmsTypes.contains(item));
        }
        return true;
    }

    /**
     * 根据站点类型判断是否查询仓数据（只要有库房类型则需要查询）
     *
     * @param siteTypes
     * @return
     */
    private boolean checkIsQueryWare(List<Integer> siteTypes) {
        if(CollectionUtils.isNotEmpty(siteTypes)){
            // 所有库房类型
            List<Integer> wmsTypes = Arrays.stream(DmsStoreTypeEnum.values())
                    .map(item -> Integer.parseInt(item.getDmsStoreType()))
                    .collect(Collectors.toList());
            return siteTypes.stream().anyMatch(wmsTypes::contains);
        }
        return true;
    }

    private PsStoreInfoRequest convertOutWareQuery(SiteQueryCondition siteQueryCondition) {
        PsStoreInfoRequest psStoreInfoRequest = new PsStoreInfoRequest();
        psStoreInfoRequest.setDmsStoreName(siteQueryCondition.getSiteName());
        psStoreInfoRequest.setDmsSiteId(siteQueryCondition.getSiteCode());
        psStoreInfoRequest.setDmsCode(siteQueryCondition.getDmsCode());
        psStoreInfoRequest.setOrgId(siteQueryCondition.getOrgId());
        psStoreInfoRequest.setSiteNamePym(siteQueryCondition.getSiteNamePym());
        psStoreInfoRequest.setProvinceId(siteQueryCondition.getProvinceId());
        psStoreInfoRequest.setCityId(siteQueryCondition.getCityId());
        psStoreInfoRequest.setCountyId(siteQueryCondition.getCountryId());
        psStoreInfoRequest.setProvinceAgencyCode(siteQueryCondition.getProvinceAgencyCode());
        return psStoreInfoRequest;
    }

    private Result<Pager<BasicSiteVO>> initPageResult(Pager<SiteQueryCondition> siteQueryPager) {
        Result<Pager<BasicSiteVO>> baseEntity = new Result<Pager<BasicSiteVO>>();
        baseEntity.setData(new Pager<>());
        baseEntity.getData().setPageNo(siteQueryPager.getPageNo());
        baseEntity.getData().setPageSize(siteQueryPager.getPageSize());
        baseEntity.getData().setTotal(Constants.NUMBER_ZERO.longValue());
        baseEntity.getData().setData(Lists.newArrayList());
        return baseEntity;
    }

    private List<BasicSiteVO> convert2OwnBasicSiteList(List<BaseSiteSimpleDto> list) {
        return list.stream()
                // 转换为分拣内部使用数据
                .map(this::convertOwnBasicSite)
                .collect(Collectors.toList());
    }

    private List<BasicSiteVO> wareConvert2OwnBasicSiteList(List<PsStoreInfo> list) {
        return list.stream()
                // 转换为分拣内部使用数据
                .map(this::wareConvertOwnBasicSite).collect(Collectors.toList());
    }

    private Result<Pager<BasicSiteVO>> queryPageSiteFromES(Pager<SiteQueryCondition> siteQueryPager) {
        Result<Pager<BasicSiteVO>> result = new Result<Pager<BasicSiteVO>>();
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.api.service.site.SiteQueryService.queryPageSiteFromES", false, true);
        try {
            // 构建es查询条件
            BoolQueryBuilder boolQueryBuilder = createQueryCondition(siteQueryPager.getSearchVo());
            int from = siteQueryPager.getPageSize() * (siteQueryPager.getPageNo() - 1);
            final ImmutablePair<Long, List<BasicSiteEsDto>> queryPageResult = basicSiteEsDao.selectByPage(boolQueryBuilder, from, siteQueryPager.getPageSize(), createSortBuilder(), BasicSiteEsDto.class);
            List<BasicSiteVO> basicSiteVOS = new ArrayList<BasicSiteVO>();
            final List<BasicSiteEsDto> streamlinedBasicSiteEsDtoList = queryPageResult.getRight();
            if (CollectionUtils.isNotEmpty(streamlinedBasicSiteEsDtoList)) {
                streamlinedBasicSiteEsDtoList.forEach(streamlinedBasicSiteEsDto -> {
                    BasicSiteVO streamLinedBasicSiteVO = new BasicSiteVO();
                    BeanUtils.copyProperties(streamlinedBasicSiteEsDto, streamLinedBasicSiteVO);
                    basicSiteVOS.add(streamLinedBasicSiteVO);
                });
            }
            Pager<BasicSiteVO> basicSitePagerData = new Pager<>(siteQueryPager.getPageNo(), siteQueryPager.getPageSize(), queryPageResult.getLeft());
            basicSitePagerData.setData(basicSiteVOS);
            result.toSuccess();
            result.setData(basicSitePagerData);
        } catch (Exception e) {
            logger.error("根据条件{}查询站点异常!", JsonHelper.toJSONString(siteQueryPager), e);
            result.toFail("服务异常!");
            Profiler.functionError(info);
        } finally {
            Profiler.registerInfoEnd(info);
        }
        return result;
    }
    private Result<Void> checkParam4querySitePageByConditionFromBasicSite(Pager<SiteQueryCondition> siteQueryPager){
        Result<Void> result = new Result<>();
        if (siteQueryPager == null) {
            result.toFail("参数错误，参数不能为空");
            return result;
        }
        if (siteQueryPager.getPageNo() == null) {
            logger.warn("checkParam4querySitePageByConditionFromBasicSite pageNo为空 {}", JsonHelper.toJSONString(siteQueryPager));
            siteQueryPager.setPageNo(1);
        }
        if (siteQueryPager.getPageSize() == null) {
            logger.warn("checkParam4querySitePageByConditionFromBasicSite pageSize为空 {}", JsonHelper.toJSONString(siteQueryPager));
            siteQueryPager.setPageSize(Constants.CONSTANT_NUMBER_TEN);
        }
        if(siteQueryPager.getSearchVo() == null){
            logger.warn("checkParam4querySitePageByConditionFromBasicSite searchVo为空 {}", JsonHelper.toJSONString(siteQueryPager));
            siteQueryPager.setSearchVo(new SiteQueryCondition());
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
            basicSiteVO.setProvinceAgencyCode(baseSiteSimpleDto.getProvinceAgencyCode());
            basicSiteVO.setProvinceAgencyName(baseSiteSimpleDto.getProvinceAgencyName());
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
        siteQuery.setSubTypeList(siteQueryCondition.getSubTypes());
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
