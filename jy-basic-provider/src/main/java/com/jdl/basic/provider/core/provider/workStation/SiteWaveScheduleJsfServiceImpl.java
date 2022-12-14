package com.jdl.basic.provider.core.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.SiteWaveScheduleQuery;
import com.jdl.basic.api.domain.workStation.SiteWaveScheduleVo;
import com.jdl.basic.api.service.workStation.SiteWaveScheduleJsfService;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.config.lock.LockService;
import com.jdl.basic.provider.core.service.workStation.SiteWaveScheduleService;
import com.jdl.basic.provider.hander.ResultHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SiteWaveScheduleJsfServiceImpl implements SiteWaveScheduleJsfService {

    @Autowired
    @Qualifier("jimdbRemoteLockService")
    private LockService lockService;

    @Autowired
    @Qualifier("siteWaveScheduleService")
    private SiteWaveScheduleService siteWaveScheduleService;

    @Override
    public Result<Boolean> importDatas(List<SiteWaveScheduleVo> dataList) {
        final Result<Boolean> result = Result.success();
        lockService.tryLock(CacheKeyConstants.CACHE_KEY_SITE_WAVE_SCHEDULE_EDIT, DateHelper.FIVE_MINUTES_MILLI, new ResultHandler() {
            @Override
            public void success() {
                Result<Boolean> apiResult = siteWaveScheduleService.importDatas(dataList);
                if(!apiResult.isSuccess()) {
                    result.setCode(apiResult.getCode());
                    result.setMessage(apiResult.getMessage());
                    result.setData(apiResult.getData());
                }
            }
            @Override
            public void fail() {
                result.toFail("其他用户正在修改场地班次时间，请稍后操作！");
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
    public Result<PageDto<SiteWaveScheduleVo>> queryPageList(SiteWaveScheduleQuery query) {
        if(log.isInfoEnabled()){
            log.info("场地班次时间 queryPageList 入参-{}", JSON.toJSONString(query));
        }
        return siteWaveScheduleService.queryPageList(query);
    }

    @Override
    public Result<Long> queryTotalCount(SiteWaveScheduleQuery query) {
        if(log.isInfoEnabled()){
            log.info("场地班次时间 queryTotalCount 入参-{}", JSON.toJSONString(query));
        }
        return siteWaveScheduleService.queryTotalCount(query);
    }
}
