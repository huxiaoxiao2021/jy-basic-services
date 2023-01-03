package com.jdl.basic.provider.core.service.workStation.impl;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.workStation.SiteAttendPlan;
import com.jdl.basic.api.domain.workStation.SiteAttendPlanQuery;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.workStation.SiteAttendPlanDao;
import com.jdl.basic.provider.core.service.workStation.SiteAttendPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service("siteAttendPlanService")
public class SiteAttendPlanServiceImpl implements SiteAttendPlanService {

    @Autowired
    private SiteAttendPlanDao siteAttendPlanDao;

    @Override
    @Transactional
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteAttendPlanServiceImpl.importDatas", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> importDatas(List<SiteAttendPlan> dataList) {
        Result<Boolean> result = Result.success();
        if(CollectionUtils.isEmpty(dataList)){
            result.toFail("导入数据为空！");
            return result;
        }

        //经过列转行后无法直接通过id增删改查
        //计划出勤时间siteAttendPlanTime、区域编码orgCode、场地编码siteCode确定唯一出勤计划
        SiteAttendPlan oldData = siteAttendPlanDao.queryOldDataByBusinessKey(dataList.get(0));
        if(oldData != null){
            log.info("删除旧数据入参{}", oldData);
            //先删除旧数据后插入新数据，逻辑上删除一条出勤计划，列转行后数据库删除15条
            siteAttendPlanDao.deleteOldDataByBusinessKey(oldData);
            dataList.forEach((item) -> item.setVersionNum(oldData.getVersionNum() + 1));
        }else {
            dataList.forEach((item) -> item.setVersionNum(1));
        }
        siteAttendPlanDao.batchInsert(dataList);
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteAttendPlanServiceImpl.queryPageList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<SiteAttendPlan>> queryPageList(SiteAttendPlanQuery query) {
        Result<List<SiteAttendPlan>> result = Result.success();
        Result<Boolean> checkResult = checkAndFillQueryParam(query);
        if(!checkResult.isSuccess()){
            return Result.fail(checkResult.getMessage());
        }
        List<SiteAttendPlan> pageList = siteAttendPlanDao.queryPageList(query);
        result.setData(pageList);
        return result;
    }

    @Override
    public Result<List<SiteAttendPlan>> queryPageDetail(SiteAttendPlan plan) {
        Result<List<SiteAttendPlan>> result = Result.success();
        List<SiteAttendPlan> detailList = siteAttendPlanDao.queryPageDetail(plan);
        if (CollectionUtils.isEmpty(detailList)){
            return result.toFail("查询数据为空！");
        }
        result.setData(detailList);
        return result;
    }

    private Result<Boolean> checkAndFillQueryParam(SiteAttendPlanQuery query){
        Result<Boolean> result = Result.success();
        if(query.getPageSize() == null
                || query.getPageSize() <= 0) {
            query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
        }
        query.setLimit(query.getPageSize());
        if (query.getPageNumber() <= 0){
            query.setPageNumber(1);
        }
        query.setOffset((query.getPageNumber() - 1) * query.getPageSize());

        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteAttendPlanServiceImpl.confirmRecords", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> confirmRecords(List<SiteAttendPlan> dataList) {
        Result<Boolean> result= Result.success();
        for(SiteAttendPlan plan : dataList){
            if(plan.getPlanAttendTime() == null){
                result.toFail("计划出勤日期不能为空！");
                return result;
            }
            if(plan.getOrgCode() == null){
                result.toFail("区域不能为空！");
                return result;
            }
            if(plan.getSiteCode() == null){
                result.toFail("场地ID不能为空！");
                return result;
            }
            //逻辑上确认1条出勤计划，列转行后数据库更新15条
            siteAttendPlanDao.confirmOneRecord(plan);
        }
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteAttendPlanServiceImpl.queryTotalCount", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Long> queryTotalCount(SiteAttendPlanQuery query) {
        List<Long> totalListCount = siteAttendPlanDao.queryTotalCount(query);
        Long totalCount = totalListCount.stream().count();
        Result<Long> result = Result.success();
        return result.setData(totalCount);
    }

}
