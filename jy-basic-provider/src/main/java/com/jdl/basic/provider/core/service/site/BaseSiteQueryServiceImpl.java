package com.jdl.basic.provider.core.service.site;

import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.api.dto.site.AreaVO;
import com.jdl.basic.api.dto.site.BasicSiteVO;
import com.jdl.basic.api.dto.site.ProvinceAgencyVO;
import com.jdl.basic.api.dto.site.SiteQueryCondition;
import com.jdl.basic.api.service.site.SiteQueryService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.basic.BasicSiteEsDao;
import com.jdl.basic.provider.core.enums.BasicAreaEnum;
import com.jdl.basic.provider.core.enums.BasicProvinceAgencyEnum;
import com.jdl.basic.provider.core.enums.SiteOperateStateEnum;
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
    
    @Autowired
    private BasicSiteEsDao basicSiteEsDao;

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

    @Cache(key = "SiteQueryService.queryProvinceAgencyInfoByCode", memoryEnable = true, memoryExpiredTime = 30 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 60 * 60 * 1000)
    @Override
    public Result<ProvinceAgencyVO> queryProvinceAgencyInfoByCode(String provinceAgencyCode) {
        Result<ProvinceAgencyVO> result = new Result<>();
        result.toFail("未查询到省区信息!");
        Optional<ProvinceAgencyVO> first = Arrays.stream(BasicProvinceAgencyEnum.values()).filter(item -> Objects.equals(item.getCode(), provinceAgencyCode)).map(item -> {
            ProvinceAgencyVO provinceAgencyVO = new ProvinceAgencyVO();
            provinceAgencyVO.setProvinceAgencyCode(item.getCode());
            provinceAgencyVO.setProvinceAgencyName(item.getName());
            return provinceAgencyVO;
        }).findFirst();
        first.ifPresent(result::setData);
        return result;
    }

    @Cache(key = "SiteQueryService.queryAreaVOInfoByCode", memoryEnable = true, memoryExpiredTime = 30 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 60 * 60 * 1000)
    @Override
    public Result<AreaVO> queryAreaVOInfoByCode(String areaCode) {
        Result<AreaVO> result = new Result<>();
        result.toFail("未查询到枢纽信息!");
        Optional<AreaVO> first = Arrays.stream(BasicAreaEnum.values()).filter(item -> Objects.equals(item.getCode(), areaCode)).map(item -> {
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaCode(item.getCode());
            areaVO.setAreaName(item.getName());
            return areaVO;
        }).findFirst();
        first.ifPresent(result::setData);
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
        CallerInfo info = Profiler.registerInfo("com.jdl.basic.api.service.site.SiteQueryService.querySiteByConditionFromBasicSite",
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
        if(CollectionUtils.isNotEmpty(siteQueryCondition.getSiteCodes())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.SITE_CODE, siteQueryCondition.getSiteCodes()));
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
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.PROVINCE_AGENCY_CODE, siteQueryCondition.getProvinceAgencyCode()));
        }
        // 枢纽编码
        if(StringUtils.isNotEmpty(siteQueryCondition.getAreaCode())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery(BasicSiteEsDto.AREA_CODE, siteQueryCondition.getAreaCode()));
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
