package com.jdl.basic.provider.core.service.cross.impl;

import com.jd.ql.basic.domain.SortCross;
import com.jdl.basic.api.domain.cross.SortCrossDetail;
import com.jdl.basic.api.domain.cross.SortCrossQuery;
import com.jdl.basic.api.domain.cross.SortCrossUpdateRequest;
import com.jdl.basic.api.domain.sendHandover.SendTripartiteHandoverDetail;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.cross.SortCrossDetailDao;
import com.jdl.basic.provider.core.service.cross.SortCrossService;
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
public class SortCrossServiceImpl implements SortCrossService {
    
    @Autowired
    private SortCrossDetailDao crossDetailDao;
    
    @Override
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
    public List<SortCrossDetail> queryNotInit(Integer queryLimit) {
        return crossDetailDao.queryNotInit(queryLimit);
    }

    @Override
    public int initSiteTypeById(SortCrossDetail sortCrossDetail) {
        return crossDetailDao.updateById(sortCrossDetail);
    }

}
