package com.jdl.basic.provider.core.service.site;

import com.google.common.collect.Lists;
import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jd.ql.basic.domain.BaseOrganStruct;
import com.jd.ql.basic.domain.BaseSite;
import com.jd.ql.basic.dto.BaseSiteSimpleDto;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.dto.PageDto;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.api.dto.site.AreaVO;
import com.jdl.basic.api.dto.site.BasicSiteVO;
import com.jdl.basic.api.dto.site.ProvinceAgencyVO;
import com.jdl.basic.api.dto.site.SiteQueryCondition;
import com.jdl.basic.api.service.site.SiteQueryService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.NumberHelper;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.config.ducc.DuccPropertyConfiguration;
import com.jdl.basic.provider.core.dao.basic.BasicSiteEsDao;
import com.jdl.basic.provider.core.enums.BasicAreaEnum;
import com.jdl.basic.provider.core.enums.BasicProvinceAgencyEnum;
import com.jdl.basic.provider.core.enums.SiteOperateStateEnum;
import com.jdl.basic.provider.core.manager.BasicOrganStructWSManager;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
    private DuccPropertyConfiguration duccPropertyConfiguration;


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
            //总公司下包含枢纽
            if(item.equals(BasicProvinceAgencyEnum.WULIUZONGBU)){
                provinceAgencyVO.setHasAreaFlag(Boolean.TRUE);
            }
            return provinceAgencyVO;
        }).collect(Collectors.toList()));
        return result;
    }

    @Cache(key = "SiteQueryService.queryAllAreaInfo", memoryEnable = true, memoryExpiredTime = 30 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 60 * 60 * 1000)
    @Override
    public Result<List<AreaVO>> queryAllAreaInfo(String provinceAgencyCode) {
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

    /**
     * 根据条件查询站点
     * @param siteQueryCondition
     * @param limit
     * @return
     */
    @Override
    public Result<List<BasicSiteVO>> querySiteByConditionFromBasicSite(SiteQueryCondition siteQueryCondition, Integer limit) {
        if(duccPropertyConfiguration.getSiteQueryDowngradeSwitch()){
            return querySiteFromES(siteQueryCondition, limit);
        }
        return querySiteFromExternal(siteQueryCondition, limit);
    }

    private Result<List<BasicSiteVO>> querySiteFromExternal(SiteQueryCondition siteQueryCondition, Integer limit) {
        Result<List<BasicSiteVO>> baseEntity = new Result<List<BasicSiteVO>>();
        baseEntity.setData(Lists.newArrayList());
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.api.service.site.SiteQueryService.querySiteFromBasic",
                false, true);
        try {
            // 先查站点数据
            PageDto<Object> pageDto = new PageDto<>();
            pageDto.setCurPage(Constants.CONSTANT_NUMBER_ONE);
            pageDto.setPageSize(limit > MAX_LIMIT ? MAX_LIMIT : limit);
            PageDto<List<BaseSiteSimpleDto>> pageSiteResult = basicSiteQueryWSManager.querySiteByCondition(convertOutSiteQuery(siteQueryCondition), pageDto);
            if(pageSiteResult != null && CollectionUtils.isNotEmpty(pageSiteResult.getData())){
                baseEntity.getData().addAll(pageSiteResult.getData().stream().map(this::convertOwnBasicSite).collect(Collectors.toList()));
            }
            // 在查库房数据 todo
            
            
            
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
        siteQuery.setProvinceAgencyCode(siteQueryCondition.getProvinceAgencyCode());
        siteQuery.setAreaCode(siteQueryCondition.getAreaCode());
        siteQuery.setSiteCode(siteQueryCondition.getSiteCode());
        siteQuery.setSiteName(siteQueryCondition.getSiteName());
        siteQuery.setSiteNamePym(siteQueryCondition.getSiteNamePym());
//        siteQuery.setSiteType();
//        siteQuery.setSubType(siteQueryCondition.getSubTypes());
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
        // 所属分拣id
        if(CollectionUtils.isNotEmpty(siteQueryCondition.getDmsIds())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.DMS_ID, siteQueryCondition.getDmsIds()));
        }
        // 站点类型
        if(CollectionUtils.isNotEmpty(siteQueryCondition.getSiteTypes())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.SITE_TYPE, siteQueryCondition.getSiteTypes()));
        }
        // 站点子类型
        if(CollectionUtils.isNotEmpty(siteQueryCondition.getSubTypes())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.SUB_TYPE, siteQueryCondition.getSubTypes()));
        }
        // 区域id
        if(CollectionUtils.isNotEmpty(siteQueryCondition.getOrgIds())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.ORG_ID, siteQueryCondition.getOrgIds()));
        }
        // 省区编码
        if(StringUtils.isNotEmpty(siteQueryCondition.getProvinceAgencyCode())){
            boolQueryBuilder.filter(QueryBuilders.termQuery(BasicSiteEsDto.PROVINCE_AGENCY_CODE, siteQueryCondition.getProvinceAgencyCode()));
        }
        // 枢纽编码
        if(StringUtils.isNotEmpty(siteQueryCondition.getAreaCode())){
            boolQueryBuilder.filter(QueryBuilders.termQuery(BasicSiteEsDto.AREA_CODE, siteQueryCondition.getAreaCode()));
        }
        // 省id
        if(CollectionUtils.isNotEmpty(siteQueryCondition.getProvinceIds())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.PROVINCE_ID, siteQueryCondition.getProvinceIds()));
        }
        // 市id
        if(CollectionUtils.isNotEmpty(siteQueryCondition.getCityIds())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.CITY_ID, siteQueryCondition.getCityIds()));
        }
        // 县id
        if(CollectionUtils.isNotEmpty(siteQueryCondition.getCountryIds())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.COUNTRY_ID, siteQueryCondition.getCountryIds()));
        }
        // 乡id
        if(CollectionUtils.isNotEmpty(siteQueryCondition.getCountrySideIds())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.COUNTRYSIDE_ID, siteQueryCondition.getCountrySideIds()));
        }
        // 片区id
        if(CollectionUtils.isNotEmpty(siteQueryCondition.getAreaCodes())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.AREA_CODE, siteQueryCondition.getAreaCodes()));
        }
        // 分区id
        if(CollectionUtils.isNotEmpty(siteQueryCondition.getPartitionCodes())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.PARTITION_CODE, siteQueryCondition.getPartitionCodes()));
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
        Result<Pager<BasicSiteVO>> result = new Result<Pager<BasicSiteVO>>();
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.api.service.site.SiteQueryService.querySitePageByConditionFromBasicSite", false, true);
        try {
            final Result<Void> checkResult = this.checkParam4querySitePageByConditionFromBasicSite(siteQueryPager);
            if (!checkResult.isSuccess()) {
                result.setCode(checkResult.getCode());
                result.setMessage(checkResult.getMessage());
                return result;
            }
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

    @Override
    public Result<Pager<BasicSiteVO>> getFJSiteByProvinceAgencyCode(Pager<SiteQueryCondition> siteQueryPager) {
        SiteQueryCondition siteQueryCondition =siteQueryPager.getSearchVo();
        checkParams4QueryFjSites(siteQueryCondition);
        List<BaseStaffSiteOrgDto> baseStaffSiteOrgDtoList = basicSiteQueryWSManager.getBaseSiteByProvinceAgencyCodeSubTypePage(siteQueryCondition.getProvinceAgencyCode(),siteQueryCondition.getSubTypes(),siteQueryPager.getPageNo());
        if (CollectionUtils.isNotEmpty(baseStaffSiteOrgDtoList)){
            List<BasicSiteVO> basicSiteVOList =baseStaffSiteOrgDtoList
                .stream()
                .map(baseStaffSiteOrgDto ->
                {
                    BasicSiteVO basicSiteVO =assembleBasicSiteVO(baseStaffSiteOrgDto);
                    return basicSiteVO;
                }).collect(Collectors.toList());

            Pager pager =new Pager();
            pager.setPageNo(siteQueryPager.getPageNo());
            pager.setPageSize(siteQueryPager.getPageSize());
            pager.setData(basicSiteVOList);
            return Result.success(pager);
        }
        return Result.success();
    }

    private BasicSiteVO assembleBasicSiteVO(BaseStaffSiteOrgDto baseStaffSiteOrgDto) {
        return null;
    }

    private void checkParams4QueryFjSites(SiteQueryCondition siteQueryCondition) {
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
            siteQueryPager.setPageSize(20);
        }
        return result;
    }
}
