package com.jdl.basic.provider.core.service.work.impl;

import com.jdl.basic.api.domain.work.WorkGridCandidate;
import com.jdl.basic.api.domain.work.WorkGridCandidateQuery;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.dao.work.WorkGridCandidateDao;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.work.WorkGridCandidateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("workGridCandidateService")
public class WorkGridCandidateServiceImpl implements WorkGridCandidateService {


    @Autowired
    @Qualifier("workGridCandidateDao")
    private WorkGridCandidateDao workGridCandidateDao;
    @Autowired
    @Qualifier("baseMajorManager")
    private BaseMajorManager baseMajorManager;
    @Override
    public Result<PageDto<WorkGridCandidate>> queryPageList(WorkGridCandidateQuery query) {
        Result<PageDto<WorkGridCandidate>> result = Result.success();
        Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
        if(!checkResult.isSuccess()){
            return Result.fail(checkResult.getMessage());
        }
        List<WorkGridCandidate> voDataList = new ArrayList<WorkGridCandidate>();
        PageDto<WorkGridCandidate> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
        Long totalCount = workGridCandidateDao.queryCount(query);
        if(totalCount != null && totalCount > 0){
            List<WorkGridCandidate> dataList = workGridCandidateDao.queryList(query);
            for (WorkGridCandidate tmp : dataList) {
                voDataList.add(tmp);
            }
            pageDto.setTotalRow(totalCount.intValue());
        }else{
            pageDto.setTotalRow(0);
        }
        pageDto.setResult(voDataList);
        result.setData(pageDto);
        return result;
    }

    @Override
    public Result<Boolean> insert(WorkGridCandidate insertData) {
        Result<Boolean> result = Result.success();
        Result<Boolean> checkResult = this.checkParamForInsert(insertData);
        if(!checkResult.isSuccess()){
            return Result.fail(checkResult.getMessage());
        }
        insertData.setCreateTime(new Date());
        insertData.setYn(1);
        workGridCandidateDao.insert(insertData);
        return result;
    }

    @Override
    public Result<Boolean> deleteBySiteCode(Integer siteCode) {
        Result<Boolean> result = Result.success();
        workGridCandidateDao.deleteBySiteCode(siteCode);
        return result;
    }

    @Override
    public List<WorkGridCandidate> queryCandidateList(Integer siteCode) {
        return workGridCandidateDao.queryCandidateList(siteCode);
    }

    private Result<Boolean> checkParamForInsert(WorkGridCandidate insertData) {
        Result<Boolean> result = Result.success();
        if (insertData.getSiteCode()==null){
            return Result.fail("场地ID不存在，请检查");
        }
        if (insertData.getSiteType()==null){
            return Result.fail("场地类型不存在，请检查");
        }
        if (StringUtils.isEmpty(insertData.getErp())){
            return Result.fail("ERP账号不存在，请检查");
        }
        return result;
    }

    /**
     * 查询参数校验
     * @param query
     * @return
     */
    public Result<Boolean> checkParamForQueryPageList(WorkGridCandidateQuery query){
        Result<Boolean> result = Result.success();
        if(query.getPageSize() == null || query.getPageSize() <= 0) {
            query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
        };
        query.setOffset(0);
        query.setLimit(query.getPageSize());
        if(query.getPageSize() == null || query.getPageNumber() > 0) {
            query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
        };
        return result;
    }
}
