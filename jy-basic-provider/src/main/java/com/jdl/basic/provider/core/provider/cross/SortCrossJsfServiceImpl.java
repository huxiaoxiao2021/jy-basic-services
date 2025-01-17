package com.jdl.basic.provider.core.provider.cross;

import com.jd.ql.basic.dto.BaseSiteInfoDto;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.cross.*;
import com.jdl.basic.api.service.cross.SortCrossJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.enums.SortCrossEnableEnum;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.manager.IBasicSiteQueryWSManager;
import com.jdl.basic.provider.core.service.cross.SortCrossService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author liwenji
 * @date 2022-11-09 10:46
 */
@Component
@Slf4j
public class SortCrossJsfServiceImpl implements SortCrossJsfService {
    
    @Autowired
    private SortCrossService sortCrossService;
    
    @Autowired
    private IBasicSiteQueryWSManager basicSiteQueryWSManager;
    
    
    @Value("${site.siteType}")
    protected Integer siteType;

    @Value("${site.subType}")
    protected Integer subType;

    @Value("${site.thirdType}")
    protected Integer thirdType;
    
    private static final Integer QUERY_LIMIT = 500;
    
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossJsfServiceImpl.queryPage", mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<PageDto<SortCrossDetail>> queryPage(SortCrossQuery query) {
        log.info("开始分页查询滑道笼车配置，查询条件为：{}",query);
        Result<PageDto<SortCrossDetail>> result = new Result<>();
        result.toFail("分页查询滑道笼车配置失败");
        PageDto<SortCrossDetail> queryPage = sortCrossService.queryPage(query);
        if (queryPage != null) {
            result.setData(queryPage);
            result.toSuccess();
        }
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossJsfServiceImpl.updateEnableByIds", mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Boolean> updateEnableByIds(SortCrossUpdateRequest request) {
        log.info("开始更新滑道笼车配置的状态: {}", JsonHelper.toJSONString(request));
        Result<Boolean> result = new Result<>();
        result.toFail("批量更新滑道笼车配置状态失败");
        if (sortCrossService.updateEnableByIds(request)) {
            return result.toSuccess("更新成功");
        }
        return result;
    }

    
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossJsfServiceImpl.initSortCross", mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Boolean> initSortCross(Integer siteCode) {
        if (siteCode == null ) {
            return Result.success(Boolean.FALSE);
        }
        while(true) {
            // 查询没有初始化的数据
            List<SortCrossDetail> sortCrossNotInit = sortCrossService.queryNotInit(siteCode);
            if (CollectionUtils.isEmpty(sortCrossNotInit)){
                break;
            }
            for (SortCrossDetail sortCrossDetail : sortCrossNotInit) {
                try{
                    if (StringUtils.isEmpty(sortCrossDetail.getSiteCode())){
                        sortCrossDetail.setSiteType(-1);
                        sortCrossDetail.setEnableFlag(SortCrossEnableEnum.DISABLE.getCode());
                    } else {
                        if (!initSiteType(sortCrossDetail)){
                            log.info("id:{}初始化数据失败",sortCrossDetail.getId());
                            return Result.fail("初始化数据失败");
                        }
                    }
                }catch (Exception e){
                    log.info("id:{}初始化数据失败:{}",sortCrossDetail.getId(),e);
                    return Result.fail("初始化数据失败");
                }
            }
        }
        return Result.success(Boolean.TRUE);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossJsfServiceImpl.queryCrossDataByDmsCode", mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<CrossDataJsfResp> queryCrossDataByDmsCode(CrossPageQuery query) {
        log.info("开始分页查询场地滑道信息: {}", JsonHelper.toJSONString(query));
        Result<CrossDataJsfResp> result = new Result<>();
        result.toFail("场地滑道分页查询失败");
        if (query.getDmsId()==null){
            return result.toFail("未获取到场地信息");
        }
        if (query.getPageNumber() < 1 || query.getLimit() < 1) {
            return result.toFail("分页参数不合法");
        }
        try{
            result.setData(sortCrossService.queryCrossDataByDmsCode(query));
            return result.toSuccess();
        }catch (Exception e) {
            log.info("场地:{}滑道分页查询失败 {}",query.getDmsId(),e);
        }
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossJsfServiceImpl.queryTableTrolleyListByCrossCode", mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<TableTrolleyJsfResp> queryTableTrolleyListByCrossCode(TableTrolleyQuery query) {
        log.info("开始根据滑道分页查询笼车信息: {}", JsonHelper.toJSONString(query));
        Result<TableTrolleyJsfResp> result = new Result<>();
        result.toFail("根据滑道分页查询笼车信息失败");
        if (query.getDmsId() == null) {
            return result.toFail("未获取到场地信息");
        }
        if (query.getCrossCode() == null) {
            return result.toFail("未获取到滑道信息");
        }
        if (query.getPageNumber() < 1 || query.getLimit() < 1) {
            return result.toFail("分页参数不合法");
        }
        try {
            result.setData(sortCrossService.queryTableTrolleyListByCrossCode(query));
            return result.toSuccess();
        } catch (Exception e) {
            log.info("场地:{}滑道：{} 分页查询失败 {}", query.getDmsId(), query.getCrossCode(), e);
        }
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossJsfServiceImpl.queryTableTrolleyListByDmsId", mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<TableTrolleyJsfResp> queryTableTrolleyListByDmsId(TableTrolleyQuery query) {
        log.info("开始根据场地分页查询笼车信息: {}", JsonHelper.toJSONString(query));
        Result<TableTrolleyJsfResp> result = new Result<>();
        result.toFail("根据场地分页查询笼车信息失败");
        if (query.getDmsId() == null) {
            return result.toFail("未获取到场地信息");
        }
        if (query.getPageNumber() < 1 || query.getLimit() < 1) {
            return result.toFail("分页参数不合法");
        }
        try {
            result.setData(sortCrossService.queryTableTrolleyListByCrossCode(query));
            return result.toSuccess();
        } catch (Exception e) {
            log.error("场地:{}分页查询失败 {}", query.getDmsId(), e);
        }
        return result;    
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossJsfServiceImpl.queryCTTByStartEndSiteCode", mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<TableTrolleyJsfResp> queryCTTByStartEndSiteCode(TableTrolleyQuery query) {
        log.info("开始根据始发：{}和目的地：{} 获取滑道笼车信息", query.getDmsId(),query.getSiteCode());
        Result<TableTrolleyJsfResp> result = new Result<>();
        result.toFail("根据始发和目的地查询流向信息失败");
        if (query.getDmsId() == null || query.getSiteCode() == null) {
            result.toFail("查询参数错误");
        }
        try{
            result.setData(sortCrossService.queryTableTrolley(query));
            return result.toSuccess();
        }catch (Exception e){
            log.error("根据始发和目的地获取滑道笼车信息失败: {}", JsonHelper.toJSONString(query), e);
        }
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossJsfServiceImpl.queryCTTByCTTCode", mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<TableTrolleyJsfResp> queryCTTByCTTCode(TableTrolleyQuery query) {
        log.info("开始根据滑道笼车号获取流向信息：{}", JsonHelper.toJSONString(query));
        Result<TableTrolleyJsfResp> result = new Result<>();
        result.toFail("根据滑道笼车号获取流向信息失败");
        if (query.getCrossCode() == null || query.getTabletrolleyCode() == null) {
            result.toFail("查询参数错误");
        }
        try{
            result.setData(sortCrossService.queryTableTrolley(query));
            return result.toSuccess();
        }catch (Exception e){
            log.error("根据滑道笼车号获取流向信息失败: {}", JsonHelper.toJSONString(query), e);
        }
        return result;    }
    
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossJsfServiceImpl.initSiteType", mState = {JProEnum.TP, JProEnum.FunctionError})
    public Boolean initSiteType(SortCrossDetail sortCrossDetail) {
        BaseSiteInfoDto baseSiteInfoDto = basicSiteQueryWSManager.getBaseSiteInfoBySiteId(Integer.valueOf(sortCrossDetail.getSiteCode()));
        if (baseSiteInfoDto != null) {
            sortCrossDetail.setSiteType(baseSiteInfoDto.getSiteType());
            sortCrossDetail.setSubType(baseSiteInfoDto.getSubType());
            sortCrossDetail.setThirdType(baseSiteInfoDto.getThirdType());
            sortCrossDetail.setBelongCode(baseSiteInfoDto.getBelongCode());
            sortCrossDetail.setBelongName(baseSiteInfoDto.getBelongName());
            if (baseSiteInfoDto.getBelongCode() != null) {
                sortCrossDetail.setEnableFlag(SortCrossEnableEnum.DISABLE.getCode());
            }else if (baseSiteInfoDto.getSiteType() != null && Objects.equals(baseSiteInfoDto.getSiteType(), siteType)
                    && baseSiteInfoDto.getSubType() != null && Objects.equals(baseSiteInfoDto.getSubType(), subType) 
                    && baseSiteInfoDto.getThirdType() != null && Objects.equals(baseSiteInfoDto.getThirdType(), thirdType)) {
                sortCrossDetail.setEnableFlag(SortCrossEnableEnum.ENABLE.getCode());
            }else {
                sortCrossDetail.setEnableFlag(SortCrossEnableEnum.DISABLE.getCode());
            }
        } else {
            sortCrossDetail.setSiteType(-1);
            sortCrossDetail.setEnableFlag(SortCrossEnableEnum.DISABLE.getCode());
        }
        if (sortCrossService.initSiteTypeById(sortCrossDetail) < 1) {
            log.info("id:{}初始化数据失败", sortCrossDetail.getId());
            return false;
        }
        return true;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossJsfServiceImpl.queryCrossCodeTableTrolleyBySiteFlow", mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<TableTrolleyJsfResp> queryCrossCodeTableTrolleyBySiteFlow(TableTrolleyQuery request) {
        Result<TableTrolleyJsfResp> result = new Result<>();
        result.toSuccess();
        if(Objects.isNull(request)) {
            result.toFail("参数为空");
            return result;
        }
        if(Objects.isNull(request.getDmsId())) {
            result.toFail("参数DmsId缺失");
            return result;
        }
        if(Objects.isNull(request.getSiteCode())) {
            result.toFail("参数SiteCode缺失");
            return result;
        }
        try{
            result.setData(sortCrossService.queryCrossCodeTableTrolleyBySiteFlow(request));
            return result;
        }catch (Exception e) {
            log.error("根据场地流向获取滑道笼车号服务失败，参数={}，errMsg={}", JsonHelper.toJSONString(request), e.getMessage(), e);
            result.toError("根据场地流向获取滑道笼车号服务失败");
            return result;
        }
    }


    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossJsfServiceImpl.queryCrossCodeTableTrolleyBySiteFlowList", mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<TableTrolleyJsfResp> queryCrossCodeTableTrolleyBySiteFlowList(TableTrolleyQuery request) {
        Result<TableTrolleyJsfResp> result = new Result<>();
        result.toSuccess();
        if(Objects.isNull(request)) {
            result.toFail("参数为空");
            return result;
        }
        if(Objects.isNull(request.getDmsId())) {
            result.toFail("参数DmsId缺失");
            return result;
        }
        if(CollectionUtils.isEmpty(request.getSiteCodeList())) {
            result.toFail("参数SiteCodeList缺失");
            return result;
        }
        try{
            result.setData(sortCrossService.queryCrossCodeTableTrolleyBySiteFlowList(request));
            return result;
        }catch (Exception e) {
            log.error("根据场地流向批量获取滑道笼车号服务失败，参数={}，errMsg={}", JsonHelper.toJSONString(request), e.getMessage(), e);
            result.toError("根据场地流向批量获取滑道笼车号服务失败");
            return result;
        }
    }



    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossJsfServiceImpl.querySiteFlowByCrossCodeTableTrolley", mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<TableTrolleyJsfResp> querySiteFlowByCrossCodeTableTrolley(TableTrolleyQuery request) {
        Result<TableTrolleyJsfResp> result = new Result<>();
        result.toSuccess();
        if(Objects.isNull(request)) {
            result.toFail("参数为空");
            return result;
        }
        if(Objects.isNull(request.getCrossCode())) {
            result.toFail("参数CrossCode缺失");
            return result;
        }
        if(Objects.isNull(request.getTabletrolleyCode())) {
            result.toFail("参数TabletrolleyCode缺失");
            return result;
        }
        if(Objects.isNull(request.getDmsId())) {
            result.toFail("参数DmsId缺失");
            return result;
        }
        try{
            result.setData(sortCrossService.querySiteFlowByCrossCodeTableTrolley(request));
            return result;
        }catch (Exception e) {
            log.error("根据滑道笼车号查询场地流向服务失败，参数={}，errMsg={}", JsonHelper.toJSONString(request), e.getMessage(), e);
            result.toError("根据滑道笼车号查询场地流向服务失败");
            return result;
        }
    }

}
