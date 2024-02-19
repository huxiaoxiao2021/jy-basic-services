package com.jdl.basic.provider.core.service.schedule.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.schedule.*;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.provider.config.cache.CacheService;
import com.jdl.basic.provider.core.dao.schedule.WorkGridScheduleDao;
import com.jdl.basic.provider.core.service.schedule.WorkGridScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WorkGridScheduleServiceImpl implements WorkGridScheduleService {

    @Autowired
    private WorkGridScheduleDao workGridScheduleDao;

    @Autowired
    CacheService jimdbCacheService;


    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.batchQueryByWorkGridKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<WorkGridSchedule>> batchQueryByWorkGridKey(WorkGridScheduleBatchRequest request) {
        Result<List<WorkGridSchedule>> result = Result.success();
        if (CollectionUtils.isEmpty(request.getWorkGridSchedules())) {
            return result.toFail("网格主键不能为空！");
        }
        return result.setData(workGridScheduleDao.batchQueryByWorkGridKey(request));
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.batchDeleteByWorkGridKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchDeleteByWorkGridKey(WorkGridScheduleBatchRequest request) {
        Result<Boolean> result = Result.success();
        if (CollectionUtils.isEmpty(request.getWorkGridSchedules())) {
            return result.toFail("网格主键不能为空！");
        }
        if (StringUtils.isEmpty(request.getUpdateUserErp())) {
            return result.toFail("修改人erp不能为空！");
        }
        if (request.getUpdateTime() == null) {
            return result.toFail("修改时间不能为空！");
        }
        Boolean deleteResult = workGridScheduleDao.batchDeleteByWorkGridKey(request);
        if (!deleteResult) {
            log.warn("WorkGridScheduleServiceImpl batchDeleteByWorkGridKey 执行失败！");
        }
        return result.setData(deleteResult);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.batchInsert", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchInsert(WorkGridScheduleBatchRequest request) {
        Result<Boolean> result = Result.success();
        if (CollectionUtils.isEmpty(request.getWorkGridSchedules())) {
            return result.toFail("班次时间插入记录不能为空！");
        }
        setValidTime(request.getWorkGridSchedules());
        Boolean insertResult = workGridScheduleDao.batchInsert(request);
        if (!insertResult) {
            log.warn("WorkGridScheduleServiceImpl batchInsert 执行失败！");
            throw new RuntimeException("批量插入记录失败！");
        }
        return result.setData(insertResult);
    }

    private void setValidTime(List<WorkGridSchedule> insertSchedules) {
        for (WorkGridSchedule insertSchedule : insertSchedules) {
            // 是否立即生效
            boolean immediatelyFlag = isImmediatelyValid(insertSchedule);
            if (immediatelyFlag) {
                //设置有效开始时间
                insertSchedule.setValidTime(getValidOrInvalidTime(new Date(), insertSchedule.getStartTime(), true));
            } else {
                insertSchedule.setValidTime(getValidOrInvalidTime(new Date(), insertSchedule.getEndTime(), false));
            }
        }
    }

    @Override
    @Transactional
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.batchUpdateWorkGridSchedule", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchUpdateWorkGridSchedule(WorkGridScheduleBatchUpdateRequest request) {
        Result<Boolean> result = Result.success();

        if (StringUtils.isEmpty(request.getUpdateUserErp())) {
            return result.toFail("updateUserErp不能为空！");
        }
        if (StringUtils.isEmpty(request.getUpdateUserName())) {
            return result.toFail("updateUserName不能为空！");
        }
        if (request.getUpdateTime() == null) {
            return result.toFail("修改时间不能为空！");
        }

        if (CollectionUtils.isNotEmpty(request.getDeleteWorkGridSchedule())) {
            WorkGridScheduleBatchRequest deleteRequest = buildScheduleBatchRequest(request, request.getDeleteWorkGridSchedule());
            Result<Boolean> deleteResult = batchDeleteByScheduleKey(deleteRequest);
            if (deleteResult.isFail()) {
                log.warn("batchUpdateWorkGridSchedule 批量删除失败！" + deleteResult.getMessage());
            }
        }

        if (CollectionUtils.isNotEmpty(request.getAddWorkGridSchedule())) {
            WorkGridScheduleBatchRequest insertRequest = buildScheduleBatchRequest(request, request.getAddWorkGridSchedule());
            Result<Boolean> insertResult = batchInsert(insertRequest);
            if (insertResult.isFail()) {
                log.warn("batchUpdateWorkGridSchedule 批量插入失败！" + insertResult.getMessage());
                throw new RuntimeException(insertResult.getMessage());
            }
        }
        return result.setData(Boolean.TRUE);
    }

    private WorkGridScheduleBatchRequest buildScheduleBatchRequest(WorkGridScheduleBatchUpdateRequest inputRequest, List<WorkGridSchedule> workGridSchedules) {
        WorkGridScheduleBatchRequest outputRequest = new WorkGridScheduleBatchRequest();
        outputRequest.setWorkGridSchedules(workGridSchedules);
        outputRequest.setUpdateUserName(inputRequest.getUpdateUserName());
        outputRequest.setUpdateUserErp(inputRequest.getUpdateUserErp());
        outputRequest.setUpdateTime(inputRequest.getUpdateTime());
        return outputRequest;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.querySiteScheduleByCondition", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<WorkGridSchedule>> querySiteScheduleByCondition(WorkGridScheduleRequest request) {
        Result<List<WorkGridSchedule>> result = Result.success();
        if (request.getSiteCode() == null) {
            return result.toFail("场地编码不能为空！");
        }
        if (request.getSourceType() == null) {
            return result.toFail("查询维度不能为空！");
        }
        return result.setData(workGridScheduleDao.querySiteScheduleByCondition(request));
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.queryWorkGridScheduleByKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public WorkGridSchedule queryWorkGridScheduleByKey(WorkGridScheduleRequest request) {
        return workGridScheduleDao.queryWorkGridScheduleByKey(request);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.listWorkGridScheduleByKeys", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public List<WorkGridSchedule> listWorkGridScheduleByKeys(BatchWorkGridScheduleQueryDto dto) {
        return workGridScheduleDao.listWorkGridScheduleByKeys(dto);
    }

    @Override
    public WorkGridSchedule queryWorkGridScheduleByName(WorkGridScheduleRequest request) {
        return workGridScheduleDao.queryWorkGridScheduleByName(request);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.cleanWorkGridScheduleOldTime", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> cleanWorkGridScheduleOldTime(BatchCleanOldTimeRequest request) {
        Result<Boolean> result = Result.success();
        if (CollectionUtils.isEmpty(request.getWorkGridKeys())) {
            return result.toFail("网格主键不能为空！");
        }
        if (request.getOldStartTime() == null || request.getOldEndTime() == null) {
            return result.toFail("oldStartTime和oldEndTime不允许为null");
        }
        return result.setData(workGridScheduleDao.cleanWorkGridScheduleOldTime(request));
    }


    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.queryTodayDeletedSiteSchedule", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<WorkGridSchedule>> queryTodayDeletedSiteSchedule(WorkGridScheduleRequest request) {
        Result<List<WorkGridSchedule>> result = Result.success();
        if (request.getSiteCode() == null) {
            return result.toFail("场地编码不能为空！");
        }
        if (request.getSourceType() == null) {
            return result.toFail("查询维度不能为空！");
        }
        if (request.getUpdateTime() == null) {
            return result.toFail("更新时间不能为空");
        }
        List<WorkGridSchedule> deletedWorkGridSchedule = workGridScheduleDao.queryTodayDeletedSiteSchedule(request);
        return result.setData(deletedWorkGridSchedule);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.batchDeleteByScheduleKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchDeleteByScheduleKey(WorkGridScheduleBatchRequest request) {
        Result<Boolean> result = Result.success();
        if (CollectionUtils.isEmpty(request.getWorkGridSchedules())) {
            return result.toFail("班次key不能为空！");
        }
        if (StringUtils.isEmpty(request.getUpdateUserErp())) {
            return result.toFail("修改人erp不能为空！");
        }
        if (request.getUpdateTime() == null) {
            return result.toFail("修改时间不能为空！");
        }

        setInvalidTime(request.getWorkGridSchedules());

        Boolean deleteResult = workGridScheduleDao.batchDeleteByScheduleKey(request);
        if (!deleteResult) {
            log.warn("WorkGridScheduleServiceImpl batchDeleteByScheduleKey 执行失败！");
        }
        return result.setData(deleteResult);
    }

    /**
     * 设置被删除班次的失效时间
     * @param deleteSchedules 被删除的班次
     */
    private void setInvalidTime(List<WorkGridSchedule> deleteSchedules) {
        for (WorkGridSchedule deleteSchedule : deleteSchedules) {
            // 是否立即生效
            boolean immediatelyFlag = isImmediatelyValid(deleteSchedule);
            // 是否跨夜
            boolean crossDayFlag = deleteSchedule.getStartTime().compareTo(deleteSchedule.getEndTime()) > 0;
            //失效那天时间
            Date date = crossDayFlag ? DateUtils.addDays(new Date(), 1) : new Date();
            if (immediatelyFlag) {
                deleteSchedule.setInvalidTime(getValidOrInvalidTime(date, deleteSchedule.getEndTime(), true));
            } else {
                deleteSchedule.setInvalidTime(getValidOrInvalidTime(date, deleteSchedule.getEndTime(), false));
            }
        }
    }

    /**
     * 是否立即生效
     * 当前时间加1小时是否在班次开始时间之前，之前则立即生效
     * @param updateWorkGridSchedule
     * @return
     */
    private boolean isImmediatelyValid(WorkGridSchedule updateWorkGridSchedule) {
        Date effectiveStartTime = DateUtils.addHours(DateHelper.parseDate(DateHelper.getDateOfHH_mm(new Date()), DateHelper.DATE_FORMAT_TIME_HH_mm), 1);
        Date scheduleStartTime = DateHelper.parseDate(updateWorkGridSchedule.getStartTime(), DateHelper.DATE_FORMAT_TIME_HH_mm);
        return effectiveStartTime.before(scheduleStartTime);
    }

    /**
     * 获取班次的有效或失效时间（计算方式一样）
     * 入参是删除班次  则返回的是失效时间
     * 入参是添加班次  则返回的是生效时间
     * @param date 生效or失效当天
     * @param scheduleTime 排班时间（开始时间或者结束时间）
     * @param validImmediatelyFlag 是否立即生效
     * @return
     */
    private Date getValidOrInvalidTime(Date date, String scheduleTime, boolean validImmediatelyFlag) {
        // 立即生效是当天
        // 不是立即生效是明天
        Date validDay = validImmediatelyFlag ? DateUtils.truncate(date, Calendar.DATE) : DateUtils.truncate(DateUtils.addDays(date, 1), Calendar.DATE);

        String hourStr = scheduleTime.split(Constants.COLON)[0];
        String minStr = scheduleTime.split(Constants.COLON)[1];
        int hour = Integer.parseInt(hourStr);
        int min = Integer.parseInt(minStr);

        return DateUtils.addMinutes(DateUtils.addHours(validDay, hour), min);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.listValidWorkGridScheduleByTime", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<ScheduleValidTimeDto>> listValidWorkGridScheduleByTime(ValidWorkGridScheduleRequest request) {
        Result<List<ScheduleValidTimeDto>> result = new Result<>();
        if (request.getWorkGridKey() == null) {
            return result.toFail("网格key不能为空！");
        }
        if (request.getValidTime() == null) {
            return result.toFail("有效时间不能为空！");
        }
        if (request.getInvalidTime() == null) {
            return result.toFail("失效时间不能为空！");
        }
        if (request.getValidTime().after(request.getInvalidTime())) {
            return result.toFail("有效时间不能小于失效时间！");
        }

        List<ScheduleValidTimeDto> validList;
        String cacheKey = String.format(CacheKeyConstants.CACHE_KEY_VALID_WORK_GRID_SCHEDULE, request.getWorkGridKey(),
                DateHelper.getDateOfyyMMddHHmmss(request.getValidTime()), DateHelper.getDateOfyyMMddHHmmss(request.getInvalidTime()));
        String json = jimdbCacheService.get(cacheKey);
        log.info("listValidWorkGridScheduleByTime 缓存key={}", cacheKey);
        if (StringUtils.isNotEmpty(json)) {
            validList = JSON.parseObject(json, new TypeReference<List<ScheduleValidTimeDto>>(){});
        } else {
            validList = new ArrayList<>();
            List<WorkGridSchedule> gridAllSchedule = workGridScheduleDao.listAllScheduleIgnoreYn(request);
            // 过滤生效过的班次并切割出对应查询时间范围内的时间
            for (WorkGridSchedule workGridSchedule : gridAllSchedule) {
                // yn = 1的直接是有效的
                if (workGridSchedule.getYn().intValue() == Constants.CONSTANT_NUMBER_ONE) {
                    ScheduleValidTimeDto validTimeDto = getValidTimeDto(workGridSchedule, request);
                    validList.add(validTimeDto);
                    continue;
                }

                // 以下yn = 0的
                // 生效失效时间为空的不用管
                if (workGridSchedule.getValidTime() == null || workGridSchedule.getInvalidTime() == null) {
                    continue;
                }

                // 删除时间不在班次有效开始时间之前  证明该班次生效过
                if (!workGridSchedule.getUpdateTime().before(workGridSchedule.getValidTime())) {
                    ScheduleValidTimeDto validTimeDto = getValidTimeDto(workGridSchedule, request);
                    validList.add(validTimeDto);
                }
            }


            jimdbCacheService.setEx(cacheKey, validList, 30L, TimeUnit.MINUTES);
        }
        return result.setData(validList);
    }

    private ScheduleValidTimeDto getValidTimeDto(WorkGridSchedule workGridSchedule, ValidWorkGridScheduleRequest request) {
        ScheduleValidTimeDto validTimeDto = new ScheduleValidTimeDto();
        BeanUtils.copyProperties(workGridSchedule, validTimeDto);
        if (workGridSchedule.getValidTime().getTime() <= request.getValidTime().getTime()) {
            validTimeDto.setValidStartTime(DateHelper.getDateOfHH_mm(request.getValidTime()));
        } else {
            validTimeDto.setValidStartTime(workGridSchedule.getStartTime());
        }

        if (workGridSchedule.getInvalidTime().getTime() >= request.getInvalidTime().getTime()) {
            validTimeDto.setValidEndTime(DateHelper.getDateOfHH_mm(request.getInvalidTime()));
        } else {
            validTimeDto.setValidEndTime(workGridSchedule.getEndTime());
        }

        return validTimeDto;
    }
}
