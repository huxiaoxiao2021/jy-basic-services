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
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.provider.config.cache.CacheService;
import com.jdl.basic.provider.config.ducc.DuccPropertyConfiguration;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.jdl.basic.common.contants.Constants.CONSTANT_SPECIAL_MARK_ASTERISK;

@Slf4j
@Service
public class WorkGridScheduleServiceImpl implements WorkGridScheduleService {

    // 凌晨12点
    private final String midNight = "00:00";

    @Autowired
    private WorkGridScheduleDao workGridScheduleDao;

    @Autowired
    CacheService jimdbCacheService;

    @Autowired
    private DuccPropertyConfiguration ducc;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");



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
        Boolean insertResult = workGridScheduleDao.batchInsert(request);
        if (!insertResult) {
            log.warn("WorkGridScheduleServiceImpl batchInsert 执行失败！");
            throw new RuntimeException("批量插入记录失败！");
        }
        List<String> workGridKeys = request.getWorkGridSchedules().stream().map(WorkGridSchedule::getWorkGridKey).distinct().collect(Collectors.toList());
        delWorkGridScheduleCache(workGridKeys);
        return result.setData(insertResult);
    }

    /**
     * 班次是否立即生效
     * @param updateWorkGridSchedule
     * @return
     */
    private boolean isImmediatelyValid(WorkGridSchedule updateWorkGridSchedule) {
        Date effectiveStartTime = DateUtils.addHours(DateHelper.parseDate(DateHelper.getDateOfHH_mm(new Date()), DateHelper.DATE_FORMAT_TIME_HH_mm), Constants.SCHEDULE_VALID_BEFORE_HOUR);
        Date scheduleStartTime = DateHelper.parseDate(updateWorkGridSchedule.getStartTime(), DateHelper.DATE_FORMAT_TIME_HH_mm);
        return effectiveStartTime.before(scheduleStartTime);
    }

    private void setValidTime(List<WorkGridSchedule> insertSchedules) {
        if (CollectionUtils.isEmpty(insertSchedules)) {
            return;
        }
        List<String> scheduleKeys = insertSchedules.stream().map(WorkGridSchedule::getScheduleKey).distinct().collect(Collectors.toList());
        BatchWorkGridScheduleQueryDto queryDto = new BatchWorkGridScheduleQueryDto();
        queryDto.setScheduleKeyList(scheduleKeys);
        // TODO 后续上线切换至request参数里获取 避免与center重复查询
        List<WorkGridSchedule> currentSchedules = workGridScheduleDao.listWorkGridScheduleByKeys(queryDto);
        Map<String, WorkGridSchedule> currentSchedulesMap = currentSchedules.stream().collect(Collectors.toMap(WorkGridSchedule::getScheduleKey, item -> item, (first, second) -> first));
        for (WorkGridSchedule insertSchedule : insertSchedules) {
            WorkGridSchedule workGridSchedule = currentSchedulesMap.get(insertSchedule.getScheduleKey());
            // 插入的班次是否立即生效
            Date now = new Date();
            boolean insertImmediatelyFlag = isImmediatelyValid(insertSchedule);
            // workGridSchedule == null说明班次是新增的
            if (workGridSchedule == null) {
                // 新插入的且立即生效
                if (insertImmediatelyFlag) {
                    insertSchedule.setValidTime(getScheduleDateTimeOfSpecifiedDate(now, insertSchedule.getStartTime(), 0));
                    // 新插入的且不立即生效
                } else {
                    insertSchedule.setValidTime(getScheduleDateTimeOfSpecifiedDate(now, insertSchedule.getStartTime(), 1));
                }
                // 不是新增的
            } else {
                // 班次正在生效中的
                if (isScheduleWorking(workGridSchedule)) {
                    insertSchedule.setValidTime(getScheduleDateTimeOfSpecifiedDate(now, insertSchedule.getStartTime(), 1));
                // 没有在生效的
                } else {
                    insertSchedule.setValidTime(getScheduleDateTimeOfSpecifiedDate(now, insertSchedule.getStartTime(), 0));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.batchUpdateWorkGridSchedule", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> batchUpdateWorkGridSchedule(WorkGridScheduleBatchUpdateRequest request) {
        if (checkUpdateWorkGridScheduleSwitchV2(request)){
            log.info("batchUpdateWorkGridSchedule 切流量：{}",JsonHelper.toJSONString(request));
            return batchUpdateWorkGridScheduleV2(request);
        }
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

        setValidTime(request.getAddWorkGridSchedule());

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

    private boolean checkUpdateWorkGridScheduleSwitchV2(WorkGridScheduleBatchUpdateRequest request) {
        log.info("checkUpdateWorkGridScheduleSwitchV2:{}",ducc.getUpdateWorkGridScheduleSwitch());
        if (CONSTANT_SPECIAL_MARK_ASTERISK.equals(ducc.getUpdateWorkGridScheduleSwitch())){
            return true;
        }
        try {
            List<String>  siteList  =Arrays.asList(ducc.getUpdateWorkGridScheduleSwitch().split(Constants.SEPARATOR_COMMA));
            if (CollectionUtils.isNotEmpty(siteList) && ObjectHelper.isNotEmpty(request.getSiteCode()) && siteList.contains(String.valueOf(request.getSiteCode()))){
                return true;
            }
        } catch (Exception e) {
            log.error("checkUpdateWorkGridScheduleSwitchV2 err request:{},",JsonHelper.toJSONString(request),e);
        }
        return false;
    }

    public Result<Boolean> batchUpdateWorkGridScheduleV2(WorkGridScheduleBatchUpdateRequest request) {
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

        /**
         * 计算生效和失效时间
         */
        try {
            processValidateTimeAndInvalidTime(request);
        } catch (Exception e) {
           log.error("processValidateTimeAndInvalidTime request:{} err",JsonHelper.toJSONString(request),e);
        }

        if (CollectionUtils.isNotEmpty(request.getDeleteWorkGridSchedule())) {
            log.info("班次变更-删除班次数据:{}", JsonHelper.toJSONString(request.getDeleteWorkGridSchedule()));
            WorkGridScheduleBatchRequest deleteRequest = buildScheduleBatchRequest(request, request.getDeleteWorkGridSchedule());
            Result<Boolean> deleteResult = batchDeleteByScheduleKeyV2(deleteRequest);
            if (deleteResult.isFail()) {
                log.warn("batchUpdateWorkGridSchedule 批量删除失败！" + deleteResult.getMessage());
            }
        }

        if (CollectionUtils.isNotEmpty(request.getAddWorkGridSchedule())) {
            log.info("班次变更-新增班次数据:{}", JsonHelper.toJSONString(request.getAddWorkGridSchedule()));
            WorkGridScheduleBatchRequest insertRequest = buildScheduleBatchRequest(request, request.getAddWorkGridSchedule());
            Result<Boolean> insertResult = batchInsert(insertRequest);
            if (insertResult.isFail()) {
                log.warn("batchUpdateWorkGridSchedule 批量插入失败！" + insertResult.getMessage());
                throw new RuntimeException(insertResult.getMessage());
            }
        }
        return result.setData(Boolean.TRUE);
    }

    private void processValidateTimeAndInvalidTime(WorkGridScheduleBatchUpdateRequest request) {
        boolean hasAddSchedules = CollectionUtils.isNotEmpty(request.getAddWorkGridSchedule());
        boolean hasDeleteSchedules = CollectionUtils.isNotEmpty(request.getDeleteWorkGridSchedule());

        if (!hasAddSchedules && !hasDeleteSchedules) {
            return;
        }

        if (hasDeleteSchedules) {
            processSchedules(request.getDeleteWorkGridSchedule(), request.getAddWorkGridSchedule(), true);
        }

        if (hasAddSchedules) {
            processSchedules(request.getAddWorkGridSchedule(), request.getDeleteWorkGridSchedule(), false);
        }
    }

    private void processSchedules(List<WorkGridSchedule> primarySchedules, List<WorkGridSchedule> secondarySchedules, boolean isDelete) {
        for (WorkGridSchedule primarySchedule : primarySchedules) {
            WorkGridSchedule secondarySchedule =  checkIfExitsIntersection(primarySchedule, secondarySchedules);

            if (ObjectHelper.isNotNull(secondarySchedule)) {
                if (isDelete) {
                    caculInvalidateTime(primarySchedule, secondarySchedule);
                } else {
                    caculValidateTime(primarySchedule, secondarySchedule);
                }
            } else {
                if (isDelete) {
                    caculInvalidateTime(primarySchedule);
                } else {
                    caculValidateTime(primarySchedule);
                }
            }
        }
    }



    /**
     * 计算新网格的生效时间--变更场景
     * @param insert 待插入的工作网格计划
     * @param delete 待删除的工作网格计划
     */
    private void caculValidateTime(WorkGridSchedule insert, WorkGridSchedule delete) {
        LocalDateTime now = LocalDateTime.now();
        LocalTime insertStartTime = LocalTime.parse(insert.getStartTime(), TIME_FORMATTER);
        LocalTime deleteStartTime = LocalTime.parse(delete.getStartTime(), TIME_FORMATTER);
        LocalTime deleteEndTime = LocalTime.parse(delete.getEndTime(), TIME_FORMATTER);
        LocalDateTime oneHourLater = now.plusHours(1);

        // 旧班次立即失效
        if (oneHourLater.toLocalTime().isBefore(deleteStartTime)) {
            //新班次立即生效
            if (oneHourLater.toLocalTime().isBefore(insertStartTime)){
                insert.setValidTime(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
            }else {
                LocalDateTime validTime = LocalDateTime.of(now.toLocalDate().plusDays(1), insertStartTime);
                insert.setValidTime(Date.from(validTime.atZone(ZoneId.systemDefault()).toInstant()));
            }
        } else {
            LocalDateTime validTime;
            if (deleteEndTime.isAfter(insertStartTime)) {
                if (deleteEndTime.isBefore(deleteStartTime)){
                    // 若有交叉 且跨夜（旧班次结束时间 在新班次开始时间之后） 新班次的生效时间是第三天班次的开始时间
                    validTime = LocalDateTime.of(now.toLocalDate().plusDays(2), insertStartTime);
                }else {
                    // 若有交叉 不跨夜（旧班次结束时间 在新班次开始时间之后） 新班次的生效时间是第二天班次的开始时间
                    validTime = LocalDateTime.of(now.toLocalDate().plusDays(1), insertStartTime);
                }
            } else {
                // 若无交叉 则新班次的生效时间是第二天班次的开始时间
                validTime = LocalDateTime.of(now.toLocalDate().plusDays(1), insertStartTime);
            }
            insert.setValidTime(Date.from(validTime.atZone(ZoneId.systemDefault()).toInstant()));
        }
    }
    /**
     * 计算新网格的生效时间--新增场景
     * @param insert 待插入的工作网格计划
     */
    private void caculValidateTime(WorkGridSchedule insert) {
        LocalDateTime now = LocalDateTime.now();
        LocalTime insertStartTime = LocalTime.parse(insert.getStartTime(), TIME_FORMATTER);
        LocalDateTime oneHourLater = now.plusHours(1);

        if (oneHourLater.toLocalTime().isBefore(insertStartTime)) {
            // 立即生效
            insert.setValidTime(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
        } else {
            // 第二天班次开始时间
            LocalDateTime validTime = LocalDateTime.of(now.toLocalDate().plusDays(1), insertStartTime);
            insert.setValidTime(Date.from(validTime.atZone(ZoneId.systemDefault()).toInstant()));
        }
    }

    public static void main(String[] args) {
        WorkGridSchedule insert =new WorkGridSchedule();
        insert.setStartTime("15:00");
        insert.setEndTime("18:00");
        //caculValidateTime(insert);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(format.format((insert.getValidTime())));

    }



    /**
     * 计算旧网格的失效时间-变更场景
     * @param delete 需要删除的工作网格计划
     * @param insert 需要插入的工作网格计划
     */
    private void caculInvalidateTime(WorkGridSchedule delete, WorkGridSchedule insert) {
        caculInvalidateTime(delete);
    }

    /**
     * 计算旧网格的失效时间-删除场景
     * @param delete 需要删除的工作网格计划
     */
    private void caculInvalidateTime(WorkGridSchedule delete) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourLater = now.plusHours(1);
        LocalTime startTime = LocalTime.parse(delete.getStartTime(), TIME_FORMATTER);
        LocalTime endTime = LocalTime.parse(delete.getEndTime(), TIME_FORMATTER);

        LocalDateTime invalidateTime;
        if (oneHourLater.toLocalTime().isBefore(startTime)) {
            // 班次的失效时间就是当前时间
            invalidateTime = now;
        } else {
            // 判断班次是否跨夜
            if (endTime.isBefore(startTime)) {
                // 跨夜，失效时间就是第二天的班次结束时间
                invalidateTime = LocalDateTime.of(now.toLocalDate().plusDays(1), endTime);
            } else {
                // 不跨夜，失效时间就是当天的班次结束时间
                invalidateTime = LocalDateTime.of(now.toLocalDate(), endTime);
            }
        }
        delete.setInvalidTime(Date.from(invalidateTime.atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * 校验是否存在交集
     * @param target
     * @param workGridScheduleList
     * @return
     */
    private static WorkGridSchedule checkIfExitsIntersection(WorkGridSchedule target ,List<WorkGridSchedule> workGridScheduleList) {
        if (CollectionUtils.isNotEmpty(workGridScheduleList)){
            for (WorkGridSchedule workGridSchedule : workGridScheduleList){
                if (workGridSchedule.getScheduleKey().equals(target.getScheduleKey())){
                    return workGridSchedule;
                }
            }
        }
        return null;
    }

    private static WorkGridSchedule checkDeleteScheduleIfExitsInsertScheduleList(WorkGridSchedule delete ,List<WorkGridSchedule> addWorkGridSchedule) {
        for (WorkGridSchedule insert : addWorkGridSchedule){
            if (delete.getScheduleKey().equals(insert.getScheduleKey())){
                return insert;
            }
        }
        return null;
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
        List<String> workGridKeys = request.getWorkGridSchedules().stream().map(WorkGridSchedule::getWorkGridKey).distinct().collect(Collectors.toList());
        delWorkGridScheduleCache(workGridKeys);
        return result.setData(deleteResult);
    }

    public Result<Boolean> batchDeleteByScheduleKeyV2(WorkGridScheduleBatchRequest request) {
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


        Boolean deleteResult = workGridScheduleDao.batchDeleteByScheduleKey(request);
        if (!deleteResult) {
            log.warn("WorkGridScheduleServiceImpl batchDeleteByScheduleKey 执行失败！");
        }
        List<String> workGridKeys = request.getWorkGridSchedules().stream().map(WorkGridSchedule::getWorkGridKey).distinct().collect(Collectors.toList());
        delWorkGridScheduleCache(workGridKeys);
        return result.setData(deleteResult);
    }

    /**
     * 设置被删除班次的失效时间
     * @param deleteSchedules 被删除的班次
     */
    private void setInvalidTime(List<WorkGridSchedule> deleteSchedules) {
        for (WorkGridSchedule deleteSchedule : deleteSchedules) {
            // 是否跨夜
            boolean crossDayFlag = isCrossDay(deleteSchedule);
            Date now = new Date();

            // 班次生效中
            if (isScheduleWorking(deleteSchedule)) {
                int addCount = deleteSchedule.getEndTime().equals(midNight) ? 1 : 0;
                Date invalidTime = getScheduleDateTimeOfSpecifiedDate(now, deleteSchedule.getEndTime(), addCount);
                // 跨夜场景
                if (crossDayFlag) {
                    // 后半夜 所以失效时间是当天
                    if (!now.after(invalidTime)) {
                        deleteSchedule.setInvalidTime(invalidTime);
                        // 前半夜  失效时间需要加1天
                    } else {
                        deleteSchedule.setInvalidTime(DateUtils.addDays(invalidTime, 1));
                    }
                    // 非跨夜场景
                } else {
                    deleteSchedule.setInvalidTime(invalidTime);
                }
                // 班次不是生效中
            } else {
                Date invalidTime = getScheduleDateTimeOfSpecifiedDate(now, deleteSchedule.getEndTime(), 0);
                // 跨夜场景
                if (crossDayFlag) {
                    deleteSchedule.setInvalidTime(invalidTime);
                } else {
                    if (!now.before(invalidTime)) {
                        deleteSchedule.setInvalidTime(invalidTime);
                    } else {
                        deleteSchedule.setInvalidTime(getScheduleDateTimeOfSpecifiedDate(now, deleteSchedule.getEndTime(), -1));
                    }
                }
            }
        }
    }

    /**
     * 插入时是否立即生效
     * 当前时间加1小时是否在班次开始时间之前，之前则立即生效(或者说该班次还未开始 true-未开始 false-已经开始)
     * @param updateWorkGridSchedule
     * @return
     */
    private boolean isScheduleNotBegin(WorkGridSchedule updateWorkGridSchedule) {
        // 转换成1970年开始的时间后再比较
        Date effectiveStartTime = DateUtils.addHours(DateHelper.parseDate(DateHelper.getDateOfHH_mm(new Date()), DateHelper.DATE_FORMAT_TIME_HH_mm), Constants.SCHEDULE_VALID_BEFORE_HOUR);
        Date scheduleStartTime = DateHelper.parseDate(updateWorkGridSchedule.getStartTime(), DateHelper.DATE_FORMAT_TIME_HH_mm);
        return effectiveStartTime.before(scheduleStartTime);
    }

    /**
     * 班次是否正在生效中
     * 跨天场景  当前时间在班次结束后n个小时内 或者  班次开始前n个小时内  班次正在生效
     * @param workGridSchedule
     * @return
     */
    private boolean isScheduleWorking(WorkGridSchedule workGridSchedule) {
        boolean crossDayFlag = isCrossDay(workGridSchedule);

        Date now = new Date();
        Date scheduleStartDateTime = getScheduleDateTimeOfSpecifiedDate(now, workGridSchedule.getStartTime(), 0);
        Date scheduleEndDateTime = getScheduleDateTimeOfSpecifiedDate(now, workGridSchedule.getEndTime(), 0);

        if (crossDayFlag) {
            // 跨天场景  当前时间在班次结束后n个小时内 或者  班次开始前n个小时内  班次正在生效
            return !now.after(DateUtils.addHours(scheduleEndDateTime, Constants.SCHEDULE_VALID_BEFORE_HOUR)) || !now.before(DateUtils.addHours(scheduleStartDateTime, -Constants.SCHEDULE_VALID_BEFORE_HOUR));
        } else {
            // 非跨天场景  当前时间在班次开始时间前n个小时 或者  在结束时间后n小时内  班次正在生效
            return !now.before(DateUtils.addHours(scheduleStartDateTime, -Constants.SCHEDULE_VALID_BEFORE_HOUR)) && !now.after(DateUtils.addHours(scheduleEndDateTime, Constants.SCHEDULE_VALID_BEFORE_HOUR));
        }
    }

    /**
     * 班次是否跨天
     * @param workGridSchedule
     * @return
     */
    private boolean isCrossDay(WorkGridSchedule workGridSchedule) {
        return workGridSchedule.getStartTime().compareTo(workGridSchedule.getEndTime()) > 0;
    }

    /**
     * 获取给定date的班次开始时间或者结束时间
     * 入参是开始时间  则返回的是开始时间的date对象
     * 入参是结束时间  则返回的是结束时间的date对象
     * @param date 指定的时间
     * @param scheduleTime 开始时间或者结束时间
     * @param addCount 需要增加(减少)的天数
     * @return
     */
    private Date getScheduleDateTimeOfSpecifiedDate(Date date, String scheduleTime, int addCount) {
        // 立即生效是当天
        // 不是立即生效是明天
        Date scheduleDateTime = DateUtils.truncate(DateUtils.addDays(date, addCount), Calendar.DATE);

        String hourStr = scheduleTime.split(Constants.COLON)[0];
        String minStr = scheduleTime.split(Constants.COLON)[1];
        int hour = Integer.parseInt(hourStr);
        int min = Integer.parseInt(minStr);

        return DateUtils.addMinutes(DateUtils.addHours(scheduleDateTime, hour), min);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridScheduleServiceImpl.listValidCutWorkGridScheduleByTime", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<ScheduleValidTimeDto>> listValidCutWorkGridScheduleByTime(ValidWorkGridScheduleRequest request) {
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
        if (request.getInvalidTime().getTime() - request.getValidTime().getTime() > 24 * 3600 * 1000L) {
            return result.toFail("查询时间不能大于一天！");
        }

        List<WorkGridSchedule> validList = Collections.emptyList();
        String cacheKey = String.format(CacheKeyConstants.CACHE_KEY_ALL_VALID_WORK_GRID_SCHEDULE_IGNORE_YN, request.getWorkGridKey());
        String json = jimdbCacheService.get(cacheKey);
        log.info("{} listValidWorkGridScheduleByTime 缓存key={}",request.getRequestId(), cacheKey);
        if (StringUtils.isNotEmpty(json)) {
            validList = JSON.parseObject(json, new TypeReference<List<WorkGridSchedule>>(){});
            log.info("{} listValidWorkGridScheduleByTime 网格key={} 缓存结果={}",request.getRequestId(), cacheKey, JsonHelper.toJSONString(validList));
        } else {
            Date now = new Date();
            List<WorkGridSchedule> gridAllSchedule = null;
            try {
                gridAllSchedule = workGridScheduleDao.listAllScheduleIgnoreYn(request);
            } catch (Exception e) {
               log.info("workGridScheduleDao.listAllScheduleIgnoreYn e:{}",e);
            }
            if (CollectionUtils.isNotEmpty(gridAllSchedule)){
                validList = gridAllSchedule.stream().filter(item -> {
                    if (item.getValidTime() == null || item.getInvalidTime() == null) {
                        return false;
                    }

                    // 开始过滤生效过的班次
                    // yn = 1的且当前时间在生效时间之后是有效的
                    if (item.getYn().intValue() == Constants.CONSTANT_NUMBER_ONE && !now.before(item.getValidTime())) {
                        return true;
                    }

                    // 以下yn = 0的
                    // 删除时间不在班次有效开始时间之前  证明该班次生效过
                    if (item.getYn().intValue() != Constants.CONSTANT_NUMBER_ONE &&!item.getUpdateTime().before(item.getValidTime())) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
                log.info("listValidWorkGridScheduleByTime 网格key={} mysql查询结果={}", cacheKey, JsonHelper.toJSONString(validList));

                jimdbCacheService.setEx(cacheKey, validList, 30L, TimeUnit.MINUTES);
            }
        }

        List<ScheduleValidTimeDto> retList = new ArrayList<>();
        if (CollectionUtils.isEmpty(validList)) {
            return result.setData(retList);
        }
        // 过滤生效过的班次并切割出对应查询时间范围内的时间
        for (WorkGridSchedule workGridSchedule : validList) {
            // 查询开始不在班次生效时间之前 且 不在失效时间之后
            boolean validFlag = !request.getValidTime().before(workGridSchedule.getValidTime()) && !request.getValidTime().after(workGridSchedule.getInvalidTime());
            boolean invalidFlag = !request.getInvalidTime().before(workGridSchedule.getValidTime()) && !request.getInvalidTime().after(workGridSchedule.getInvalidTime());
            String[] splits = workGridSchedule.getStartTime().split(Constants.COLON);
            int hour = Integer.parseInt(splits[0]);
            int minute = Integer.parseInt(splits[1]);
            Date dateTimeOfStartTime = DateUtils.addMinutes(DateUtils.addHours(DateUtils.truncate(request.getValidTime(), Calendar.DATE), hour), minute);
            // 查询开始时间在班次的时间范围内  或者  查询结束时间在班次的时间范围内
            // 且班次当天开始时间不在班次失效时间之前
            if (validFlag || invalidFlag){
                retList.addAll(getValidTimeDto(workGridSchedule, request));
            }

        }
        return result.setData(retList);
    }

    private List<ScheduleValidTimeDto> getValidTimeDto(WorkGridSchedule workGridSchedule, ValidWorkGridScheduleRequest request) {
        List<ScheduleValidTimeDto> retList = new ArrayList<>();

        boolean crossDayFlag = isCrossDay(workGridSchedule);
        // 非跨夜 一段时间
        if (!crossDayFlag) {
            Date date = DateUtils.truncate(request.getValidTime(), Calendar.DATE);
            Date scheduleStartTime = getScheduleDateTimeOfSpecifiedDate(date, workGridSchedule.getStartTime(), 0);
            Date scheduleEndTime = getScheduleDateTimeOfSpecifiedDate(date, workGridSchedule.getEndTime(), 0);
            ScheduleValidTimeDto validTimeDto = new ScheduleValidTimeDto();
            BeanUtils.copyProperties(workGridSchedule, validTimeDto);
            String validStartTime = request.getValidTime().before(scheduleStartTime) ? DateHelper.getDateOfHH_mm(scheduleStartTime) : DateHelper.getDateOfHH_mm(request.getValidTime());
            String validEndTime = request.getInvalidTime().after(scheduleEndTime) ? DateHelper.getDateOfHH_mm(scheduleEndTime) : DateHelper.getDateOfHH_mm(request.getInvalidTime());
            validTimeDto.setValidStartTime(validStartTime);
            validTimeDto.setValidEndTime(validEndTime);
            retList.add(validTimeDto);
        } else {    // 跨夜  可能有两段时间

            Date searchDateStart = DateUtils.truncate(request.getValidTime(), Calendar.DATE);
            Date searchDateEnd = DateUtils.truncate(request.getInvalidTime(), Calendar.DATE);
            // 查询当天跟开始生效时间一样  只有下半段
            if (searchDateStart.equals(DateUtils.truncate(workGridSchedule.getValidTime(), Calendar.DATE))) {
                ScheduleValidTimeDto validTimeDto = new ScheduleValidTimeDto();
                BeanUtils.copyProperties(workGridSchedule, validTimeDto);
                validTimeDto.setValidStartTime(workGridSchedule.getStartTime());
                validTimeDto.setValidEndTime(midNight);
                retList.add(validTimeDto);
                // 查询当天跟失效时间一样  只有上半段
            } else if (searchDateEnd.equals(DateUtils.truncate(workGridSchedule.getInvalidTime(), Calendar.DATE))) {
                ScheduleValidTimeDto validTimeDto = new ScheduleValidTimeDto();
                BeanUtils.copyProperties(workGridSchedule, validTimeDto);
                validTimeDto.setValidStartTime(midNight);
                validTimeDto.setValidEndTime(workGridSchedule.getEndTime());
                if (!midNight.equals(workGridSchedule.getEndTime())) {
                    retList.add(validTimeDto);
                }
                // 生效时间包含查询时间  有上半段和下半段
            } else {
                ScheduleValidTimeDto validTimeDto1 = new ScheduleValidTimeDto();
                BeanUtils.copyProperties(workGridSchedule, validTimeDto1);
                validTimeDto1.setValidStartTime(midNight);
                validTimeDto1.setValidEndTime(workGridSchedule.getEndTime());
                // 跨夜且结束时间不是00:00
                if (!midNight.equals(workGridSchedule.getEndTime())) {
                    retList.add(validTimeDto1);
                }

                ScheduleValidTimeDto validTimeDto2 = new ScheduleValidTimeDto();
                BeanUtils.copyProperties(workGridSchedule, validTimeDto2);
                validTimeDto2.setValidStartTime(workGridSchedule.getStartTime());
                validTimeDto2.setValidEndTime(midNight);

                retList.add(validTimeDto2);
            }
        }

        return retList;
    }

    /**
     * 删除缓存
     * @param workGridKeys 网格key
     */
    private void delWorkGridScheduleCache(List<String> workGridKeys) {
        if (CollectionUtils.isEmpty(workGridKeys)) {
            return;
        }
        List<String> cacheKeys = new ArrayList<>();
        for (String workGridKey : workGridKeys) {
            String cacheKey = String.format(CacheKeyConstants.CACHE_KEY_ALL_VALID_WORK_GRID_SCHEDULE_IGNORE_YN, workGridKey);
            cacheKeys.add(cacheKey);
        }
        jimdbCacheService.del(cacheKeys.toArray(cacheKeys.toArray(new String[0])));
    }
}
