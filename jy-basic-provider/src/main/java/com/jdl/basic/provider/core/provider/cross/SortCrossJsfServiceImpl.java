package com.jdl.basic.provider.core.provider.cross;

import com.jd.ql.basic.dto.BaseSiteInfoDto;
import com.jdl.basic.api.domain.cross.*;
import com.jdl.basic.api.service.cross.SortCrossJsfService;
import com.jdl.basic.common.enums.SortCrossEnableEnum;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.manager.basicSiteQueryWS.IBasicSiteQueryWSManager;
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
    public Result<Boolean> initSortCross() {
        
        while(true) {
            // 查询没有初始化的数据
            List<SortCrossDetail> sortCrossNotInit = sortCrossService.queryNotInit(QUERY_LIMIT);
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

}
