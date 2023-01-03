package com.jdl.basic.provider.core.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.SiteAttendPlan;
import com.jdl.basic.api.domain.workStation.SiteAttendPlanQuery;
import com.jdl.basic.api.service.workStation.SiteAttendPlanJsfService;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.config.lock.LockService;
import com.jdl.basic.provider.core.service.workStation.SiteAttendPlanService;
import com.jdl.basic.provider.hander.ResultHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SiteAttendPlanJsfServiceImpl implements SiteAttendPlanJsfService {

    @Autowired
    @Qualifier("siteAttendPlanService")
    private SiteAttendPlanService siteAttendPlanService;

    @Autowired
    @Qualifier("jimdbRemoteLockService")
    private LockService lockService;


    @Override
    public Result<Boolean> importDatas(List<SiteAttendPlan> dataList) {
        final Result<Boolean> result = Result.success();
        lockService.tryLock(CacheKeyConstants.CACHE_KEY_SITE_ATTEND_PLAN_EDIT, DateHelper.FIVE_MINUTES_MILLI, new ResultHandler() {
            @Override
            public void success() {
                Result<Boolean> apiResult = siteAttendPlanService.importDatas(dataList);
                if(!apiResult.isSuccess()) {
                    result.setCode(apiResult.getCode());
                    result.setMessage(apiResult.getMessage());
                    result.setData(apiResult.getData());
                }
            }
            @Override
            public void fail() {
                result.toFail("其他用户正在修改出勤计划，请稍后操作！");
            }
            @Override
            public void error(Exception e) {
                log.error(e.getMessage(), e);
                result.toFail("操作异常，请稍后重试！");
            }
        });
        return result;
    }

    @Override
    public Result<List<SiteAttendPlan>> queryPageList(SiteAttendPlanQuery query) {
        if(log.isInfoEnabled()){
            log.info("场地维度出勤计划 queryPageList 入参-{}", JSON.toJSONString(query));
        }
        return siteAttendPlanService.queryPageList(query);
    }

    @Override
    public Result<List<SiteAttendPlan>> queryPageDetail(SiteAttendPlan plan){
        if(log.isInfoEnabled()){
            log.info("场地维度出勤计划 queryPageDetail 入参-{}", JSON.toJSONString(plan));
        }
        return siteAttendPlanService.queryPageDetail(plan);
    }

    @Override
    public Result<Boolean> confirmRecords(List<SiteAttendPlan> dataList) {
        if(log.isInfoEnabled()){
            log.info("场地维度出勤计划 confirmRecords 入参-{}", JSON.toJSONString(dataList));
        }
        final Result<Boolean> result = Result.success();
        lockService.tryLock(CacheKeyConstants.CACHE_KEY_SITE_ATTEND_PLAN_EDIT, DateHelper.FIVE_MINUTES_MILLI, new ResultHandler() {
            @Override
            public void success() {
                Result<Boolean> apiResult = siteAttendPlanService.confirmRecords(dataList);
                if(!apiResult.isSuccess()) {
                    result.setCode(apiResult.getCode());
                    result.setMessage(apiResult.getMessage());
                    result.setData(apiResult.getData());
                }
            }
            @Override
            public void fail() {
                result.toFail("其他用户正在修改出勤计划，请稍后操作！");
            }
            @Override
            public void error(Exception e) {
                log.error(e.getMessage(), e);
                result.toFail("操作异常，请稍后重试！");
            }
        });
        return result;
    }

    @Override
    public Result<Long> queryTotalCount(SiteAttendPlanQuery query) {
        if(log.isInfoEnabled()){
            log.info("场地维度出勤计划 queryTotalCount 入参-{}", JSON.toJSONString(query));
        }
        return siteAttendPlanService.queryTotalCount(query);
    }
}
