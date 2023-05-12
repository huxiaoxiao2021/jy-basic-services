package com.jdl.basic.provider.core.service.cross.impl;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.cross.*;
import com.jdl.basic.api.service.cross.SortCrossJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.BeanUtils;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.provider.common.Jimdb.CacheService;
import com.jdl.basic.provider.core.dao.cross.SortCrossDetailDao;
import com.jdl.basic.provider.core.service.cross.SortCrossService;
import com.jdl.basic.provider.dto.SortCrossModifyDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jdl.basic.common.contants.CacheKeyConstants.CACHE_KEY_INSERT_OR_UPDATE_SORT_CROSS;

/**
 * @author liwenji
 * @date 2022-11-09 10:48
 */

@Service
@Slf4j
public class SortCrossServiceImpl implements SortCrossService {
    
    @Autowired
    private SortCrossDetailDao crossDetailDao;
    
    @Autowired
    private SortCrossJsfService sortCrossService;

    @Resource
    @Qualifier("JimdbCacheService")
    private CacheService cacheService;
    
    private static final String INSERT_EVENT = "INSERT";
    
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossServiceImpl.queryPage", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public PageDto<SortCrossDetail> queryPage(SortCrossQuery query) {
        if(query.getPageNumber() < Constants.YN_YES || query.getPageSize() <= Constants.YN_NO){
            return null;
        }
        PageDto<SortCrossDetail> pageDto = new PageDto<>();
        query.setLimit(query.getPageSize());
        query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
        long count = crossDetailDao.queryCount(query);
        if(count > 0){
            pageDto.setTotalRow((int) count);
            pageDto.setResult(crossDetailDao.queryPage(query));
        }else {
            pageDto.setResult(new ArrayList<>());
            pageDto.setTotalRow(0);
        }
        return pageDto;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossServiceImpl.updateEnableByIds", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public boolean updateEnableByIds(SortCrossUpdateRequest request) {
        if (request == null 
                || CollectionUtils.isEmpty(request.getIds()) 
                || request.getEnableFlag() == null
                || request.getEnableFlag()< 0
                || request.getEnableFlag()> 1) {
            return Boolean.FALSE;
        }
        return crossDetailDao.updateEnableByIds(request) > 0;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossServiceImpl.queryNotInit", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public List<SortCrossDetail> queryNotInit(Integer dmsId) {
        return crossDetailDao.queryNotInit(dmsId);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossServiceImpl.initSiteTypeById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public int initSiteTypeById(SortCrossDetail sortCrossDetail) {
        return crossDetailDao.updateById(sortCrossDetail);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossServiceImpl.queryCrossDataByDmsCode", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public CrossDataJsfResp queryCrossDataByDmsCode(CrossPageQuery query) {
        CrossDataJsfResp crossData = new CrossDataJsfResp();
        List<String> crossList = new ArrayList<>();
        Long count = crossDetailDao.queryCrossDataCount(query);
        query.setOffset((query.getPageNumber() - 1) * query.getLimit());
        if (count > 0 ) {
            crossList = crossDetailDao.queryCrossDataByDmsCode(query);
            int totalPage = (int) (count% query.getLimit() == 0 ? count/query.getLimit() : count/query.getLimit()+1);
            crossData.setTotalPage(totalPage);
        }
        crossData.setCrossCodeList(crossList);
        return crossData;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossServiceImpl.queryTableTrolleyListByCrossCode", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public TableTrolleyJsfResp queryTableTrolleyListByCrossCode(TableTrolleyQuery query) {
        TableTrolleyJsfResp tableTrolley = new TableTrolleyJsfResp();
        List<TableTrolleyJsfDto> tableTrolleyList = new ArrayList<>();
        Long count = crossDetailDao.queryTableTrolleyCount(query);
        query.setOffset((query.getPageNumber() - 1) * query.getLimit());
        if (count > 0 ) {
            tableTrolleyList = crossDetailDao.queryTableTrolleyList(query);
            log.info("获取滑道笼车信息：{}",tableTrolleyList.toString());
            int totalPage = (int) (count% query.getLimit() == 0 ? count/query.getLimit() : count/query.getLimit()+1);
            tableTrolley.setTotalPage(totalPage);
        }
        tableTrolley.setTableTrolleyDtoJsfList(tableTrolleyList);
        return tableTrolley;    
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossServiceImpl.queryTableTrolley", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public TableTrolleyJsfResp queryTableTrolley(TableTrolleyQuery query) {
        TableTrolleyJsfResp tableTrolley = new TableTrolleyJsfResp();
        List<TableTrolleyJsfDto> tableTrolleyList = crossDetailDao.queryTableTrolley(query);
        tableTrolley.setTableTrolleyDtoJsfList(tableTrolleyList);
        return tableTrolley;
    }
    @Override
    public boolean syncSortCross(SortCrossModifyDto sortCrossModifyDto) {
        if (sortCrossModifyDto == null) {
            return false;
        }
        if (CollectionUtils.isEmpty(sortCrossModifyDto.getAfterChangeOfColumns())) {
            return false;
        }
        SortCrossDetail sortCrossDetail = BeanUtils.copyByList(sortCrossModifyDto.getAfterChangeOfColumns(), SortCrossDetail.class);
        if(sortCrossDetail == null) {
            return false;
        }
        
        if (sortCrossDetail.getId() == null) {
            log.error("新增或更新滑道笼车数据没有主键ID：{}",JsonHelper.toJSONString(sortCrossModifyDto));
            return true;
        }
        String key = String.format(CACHE_KEY_INSERT_OR_UPDATE_SORT_CROSS,sortCrossDetail.getId());

        try{
            boolean getLock = cacheService.setNx(key, 1 + "", 60, TimeUnit.SECONDS);

            if (!getLock) {
                log.info("新增或更新滑道笼车数据获取锁失败{}",key);
                return false;
            }

            SortCrossDetail detail = crossDetailDao.selectByPrimaryKey(sortCrossDetail.getId());

            if (detail != null ) {
                
                // 并发消费insert和update场景：先消费update消息再消费insert消息时，忽略insert消息
                if (sortCrossModifyDto.getEventType().equals(INSERT_EVENT)) {
                    return true;
                }
                
                if ( crossDetailDao.updateByPrimaryKeySelective(sortCrossDetail) < 0 ) {
                    return false;
                }
            }else {
                sortCrossDetail.setSiteType(0);
                if (crossDetailDao.insert(sortCrossDetail) < 0) {
                    return false;
                }
                if (!sortCrossService.initSiteType(sortCrossDetail)) {
                    return false;
                }
            }
        }catch (Exception e){
            log.error("新增或更新滑道笼车数据异常：{}", JsonHelper.toJSONString(sortCrossModifyDto),e);
        }finally {
            cacheService.del(key);
        }
        return true;
    }

    @Override
    public TableTrolleyJsfResp queryCrossCodeTableTrolleyBySiteFlow(TableTrolleyQuery tableTrolleyQuery) {
        TableTrolleyJsfResp tableTrolley = new TableTrolleyJsfResp();
        List<TableTrolleyJsfDto> tableTrolleyList = crossDetailDao.queryCrossCodeTableTrolleyBySiteFlow(tableTrolleyQuery);
        tableTrolley.setTableTrolleyDtoJsfList(tableTrolleyList);
        return tableTrolley;
    }
}
