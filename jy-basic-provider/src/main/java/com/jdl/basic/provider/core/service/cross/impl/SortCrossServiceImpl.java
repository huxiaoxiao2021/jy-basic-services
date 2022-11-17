package com.jdl.basic.provider.core.service.cross.impl;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.cross.*;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.provider.core.dao.cross.SortCrossDetailDao;
import com.jdl.basic.provider.core.service.cross.SortCrossService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liwenji
 * @date 2022-11-09 10:48
 */

@Service
@Slf4j
public class SortCrossServiceImpl implements SortCrossService {
    
    @Autowired
    private SortCrossDetailDao crossDetailDao;
    
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
                || request.getEnable() == null
                || request.getEnable()< 0
                || request.getEnable()> 1) {
            return Boolean.FALSE;
        }
        return crossDetailDao.updateEnableByIds(request) > 0;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossServiceImpl.queryNotInit", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public List<SortCrossDetail> queryNotInit(Integer queryLimit) {
        return crossDetailDao.queryNotInit(queryLimit);
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
            int totalPage = (int) (count% query.getLimit() == 0 ? count/query.getLimit() : count/query.getLimit()+1);
            tableTrolley.setTotalPage(totalPage);
        }
        tableTrolley.setTableTrolleyDtoJsfList(tableTrolleyList);
        return tableTrolley;    
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SortCrossServiceImpl.queryTableTrolleyListByDmsId", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public TableTrolleyJsfResp queryTableTrolleyListByDmsId(TableTrolleyQuery query) {
        TableTrolleyJsfResp tableTrolley = new TableTrolleyJsfResp();
        List<TableTrolleyJsfDto> tableTrolleyList = new ArrayList<>();
        Long count = crossDetailDao.queryTableTrolleyCount(query);
        query.setOffset((query.getPageNumber() - 1) * query.getLimit());
        if (count > 0 ) {
            tableTrolleyList = crossDetailDao.queryTableTrolleyList(query);
            int totalPage = (int) (count% query.getLimit() == 0 ? count/query.getLimit() : count/query.getLimit()+1);
            tableTrolley.setTotalPage(totalPage);
        }
        tableTrolley.setTableTrolleyDtoJsfList(tableTrolleyList);
        return tableTrolley;
    }

}
