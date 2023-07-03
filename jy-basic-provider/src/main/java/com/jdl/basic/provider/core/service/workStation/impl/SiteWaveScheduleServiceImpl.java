package com.jdl.basic.provider.core.service.workStation.impl;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.workStation.SiteWaveSchedule;
import com.jdl.basic.api.domain.workStation.SiteWaveScheduleQuery;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.workStation.SiteWaveScheduleDao;
import com.jdl.basic.provider.core.service.workStation.SiteWaveScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("siteWaveScheduleService")
public class SiteWaveScheduleServiceImpl implements SiteWaveScheduleService {

    @Autowired
    private SiteWaveScheduleDao siteWaveScheduleDao;

    @Override
    @Transactional
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteWaveScheduleServiceImpl.importDatas", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> importDatas(List<SiteWaveSchedule> insertDataList) {
        Result<Boolean> result = Result.success();
        if(CollectionUtils.isEmpty(insertDataList)){
            result.toFail("导入数据为空！");
            return result;
        }

        //经过列转行后无法直接通过id增删改查
        //区域orgCode、场地siteCode确定唯一场地班次时间
        SiteWaveSchedule oldData = siteWaveScheduleDao.queryOldDataByBusinessKey(insertDataList.get(0));
        if (oldData != null) {
            log.info("删除旧数据入参{}", oldData);
            //先删除旧数据后插入新数据
            siteWaveScheduleDao.deleteOldDataByBusinessKey(oldData);
            insertDataList.forEach((item) -> item.setVersionNum(oldData.getVersionNum() + 1));
        }else {
            insertDataList.forEach((item) -> item.setVersionNum(1));
        }
        siteWaveScheduleDao.batchInsert(insertDataList.stream().peek(item -> {
            if(item.getProvinceAgencyCode() == null){
                item.setProvinceAgencyCode(Constants.EMPTY_FILL);
            }
            if(item.getProvinceAgencyName() == null){
                item.setProvinceAgencyName(Constants.EMPTY_FILL);
            }
            if(item.getAreaHubCode() == null){
                item.setAreaHubCode(Constants.EMPTY_FILL);
            }
            if(item.getAreaHubName() == null){
                item.setAreaHubName(Constants.EMPTY_FILL);
            }
        }).collect(Collectors.toList()));
        return result;
    }


    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteWaveScheduleServiceImpl.queryPageList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<SiteWaveSchedule>> queryPageList(SiteWaveScheduleQuery query) {
        Result<List<SiteWaveSchedule>> result = Result.success();
        Result<Boolean> checkResult = checkAndFillQueryParam(query);
        if(!checkResult.isSuccess()){
            return Result.fail(checkResult.getMessage());
        }
        List<SiteWaveSchedule> pageList = siteWaveScheduleDao.queryPageList(query);
        result.setData(pageList);
        return result;
    }

    @Override
    public Result<List<SiteWaveSchedule>> queryPageDetail(SiteWaveSchedule schedule) {
        Result<List<SiteWaveSchedule>> result = Result.success();
        List<SiteWaveSchedule> detailList = siteWaveScheduleDao.queryPageDetail(schedule);
        log.info("{}", JsonHelper.toJSONString(detailList));
        if (CollectionUtils.isEmpty(detailList)){
            return result.toFail("查询数据为空！");
        }
        result.setData(detailList);
        return result;
    }


    private Result<Boolean> checkAndFillQueryParam(SiteWaveScheduleQuery query){
        Result<Boolean> result = Result.success();
        if(query.getPageSize() == null || query.getPageSize() <= 0) {
            query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
        }
        query.setLimit(query.getPageSize());
        if (query.getPageNumber() == 0){
            query.setPageNumber(1);
        }
        query.setOffset((query.getPageNumber() - 1) * query.getPageSize());

        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteWaveScheduleServiceImpl.queryTotalCount", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Long> queryTotalCount(SiteWaveScheduleQuery query) {
        Result<Long> result = Result.success();
        List<Long> totalList = siteWaveScheduleDao.queryTotalCount(query);
        return result.setData(totalList.stream().count());
    }

}
