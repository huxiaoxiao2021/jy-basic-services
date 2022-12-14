package com.jdl.basic.provider.core.service.workStation.impl;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.workStation.SiteWaveSchedule;
import com.jdl.basic.api.domain.workStation.SiteWaveScheduleQuery;
import com.jdl.basic.api.domain.workStation.SiteWaveScheduleVo;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.enums.ScheduleEnum;
import com.jdl.basic.common.enums.WaveTypeEnum;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.workStation.SiteWaveScheduleDao;
import com.jdl.basic.provider.core.service.workStation.SiteWaveScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service("siteWaveScheduleService")
public class SiteWaveScheduleServiceImpl implements SiteWaveScheduleService {

    @Autowired
    private SiteWaveScheduleDao siteWaveScheduleDao;

    @Override
    @Transactional
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteWaveScheduleServiceImpl.importDatas", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> importDatas(List<SiteWaveScheduleVo> dataList) {
        Result<Boolean> result = Result.success();
        if(CollectionUtils.isEmpty(dataList)){
            result.toFail("导入数据为空！");
            return result;
        }

        for (SiteWaveScheduleVo vo : dataList){
            List<SiteWaveSchedule> importDatas = new ArrayList<>();
            Result<Boolean> checkResult = this.checkAndConvertDto(vo, importDatas);
            if (checkResult.isFail()){
                return result.toFail(checkResult.getMessage());
            }


            for (SiteWaveSchedule insertData : importDatas){
                if (!Objects.equals(siteWaveScheduleDao.insert(insertData), Constants.YN_YES)){
                    log.info("数据插入数据库失败！入参{}", JsonHelper.toJSONString(insertData));
                    return result.toFail("导入失败！");
                }
            }
        }
        return result;
    }

    private Result<Boolean> checkAndConvertDto(SiteWaveScheduleVo vo, List<SiteWaveSchedule> importDatas){
        Result<Boolean> result = Result.success();
        if(CollectionUtils.isEmpty(vo.getDayShift()) && CollectionUtils.isEmpty(vo.getMidShift()) && CollectionUtils.isEmpty(vo.getNightShift())){
            return result.toFail("没有出勤时间！");
        }
        try {
            genSiteWaveSchedule(vo, importDatas);
        }catch (Exception e){
            log.info("时间格式不对！入参{}", JsonHelper.toJSONString(vo));
            return result.toFail("场地ID为【" + vo.getSiteCode() + "】的班次时间格式不对！");
        }

        long minStartTime = Long.MAX_VALUE;
        long maxEndTime = Long.MIN_VALUE;
        for(SiteWaveSchedule schedule : importDatas){
            if (schedule.getStartTime() != null && schedule.getEndTime() != null){
                minStartTime = Math.min(schedule.getStartTime().getTime(), minStartTime);
                maxEndTime = Math.max(schedule.getEndTime().getTime(), maxEndTime);
            }
        }
        //限制在24小时内
        if(maxEndTime - minStartTime > 24 * 3600 * 1000){
            return result.toFail("场地ID为【" + vo.getSiteCode() + "】的班次时间超过24小时！");
        }

        for(SiteWaveSchedule insertData : importDatas){
            SiteWaveSchedule oldData = siteWaveScheduleDao.queryOldDataByBusinessKey(insertData);
            if (oldData != null){
                if(siteWaveScheduleDao.deleteOldDataByBusinessKey(oldData) < Constants.YN_YES){
                    log.info("删除旧数据失败！入参{}", JsonHelper.toJSONString(oldData));
                    return result.toFail("更新数据失败!");
                }
                insertData.setCreateUser(oldData.getCreateUser());
                insertData.setCreateUserName(oldData.getCreateUserName());
                insertData.setCreateTime(oldData.getCreateTime());
                insertData.setUpdateUser(vo.getUpdateUser());
                insertData.setUpdateUserName(vo.getUpdateUserName());
                insertData.setUpdateTime(new Date());
                insertData.setVersionNum(oldData.getVersionNum() + 1);
            }else {
                insertData.setCreateUser(vo.getCreateUser());
                insertData.setCreateUserName(vo.getCreateUserName());
                insertData.setCreateTime(new Date());
                insertData.setUpdateUser(vo.getUpdateUser());
                insertData.setUpdateUserName(vo.getUpdateUserName());
                insertData.setUpdateTime(new Date());
            }

        }
        return result;
    }

    private void getCommonValues(Map<String, String> map, SiteWaveScheduleVo vo, WaveTypeEnum waveTypeEnum, List<SiteWaveSchedule> list) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        for(ScheduleEnum typeEnum : ScheduleEnum.values()){
            SiteWaveSchedule newData = new SiteWaveSchedule();
            if(StringUtils.isNotEmpty(map.get(typeEnum.getName()))){
                String[] timeString = map.get(typeEnum.getName()).split("-");
                Date startTime = sdf.parse(timeString[0]);
                Date endTime = sdf.parse(timeString[1]);
                //时间段跨两天，如22:00-01:00，为当天22:00到次日凌晨01:00
                if (startTime.getTime() > endTime.getTime()){
                    endTime = DateUtils.addDays(endTime, 1);
                }
                newData.setStartTime(startTime);
                newData.setEndTime(endTime);
            }

            newData.setOrgCode(vo.getOrgCode());
            newData.setOrgName(vo.getOrgName());
            newData.setSiteCode(vo.getSiteCode());
            newData.setSiteName(vo.getSiteName());
            newData.setBusinessLineCode(vo.getBusinessLineCode());
            newData.setBusinessLineName(vo.getBusinessLineName());
            newData.setWaveCode(waveTypeEnum.getCode());
            newData.setWavePeriod(typeEnum.getCode());
            list.add(newData);
        }
    }

    private void genSiteWaveSchedule(SiteWaveScheduleVo vo, List<SiteWaveSchedule> importDatas) throws Exception {
        //白班
        Map<String, String> dayShift = vo.getDayShift();
        getCommonValues(dayShift, vo, WaveTypeEnum.DAY, importDatas);
        //中班
        Map<String, String> midShift = vo.getMidShift();
        getCommonValues(midShift, vo, WaveTypeEnum.MIDDLE, importDatas);
        //夜班
        Map<String, String> nightShift = vo.getNightShift();
        getCommonValues(nightShift, vo, WaveTypeEnum.NIGHT, importDatas);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteWaveScheduleServiceImpl.queryPageList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<PageDto<SiteWaveScheduleVo>> queryPageList(SiteWaveScheduleQuery query) {
        Result<PageDto<SiteWaveScheduleVo>> result = Result.success();
        Result<Boolean> checkResult = checkAndFillQueryParam(query);
        if(!checkResult.isSuccess()){
            return Result.fail(checkResult.getMessage());
        }
        PageDto<SiteWaveScheduleVo> pageData = new PageDto<>(query.getPageNumber(), query.getPageSize());
        //由于插入数据是列转行，每条数据逻辑上的业务主键包括orgCode,siteCode
        //分页查询需要对业务主键进行group by才能正确分页，返回多个结果
        List<Long> totalList = siteWaveScheduleDao.queryTotalCount(query);
        Long totalCount = totalList.stream().count();
        if(totalCount != null && totalCount > 0){
            List<SiteWaveScheduleVo> returnList = new ArrayList<>();
            List<SiteWaveSchedule> pageList = siteWaveScheduleDao.queryPageList(query);
            for (SiteWaveSchedule schedule : pageList){
                //行转列，多行数据拼装成一行给前台展示
                SiteWaveScheduleVo returnVo = convertSiteWaveScheduleVo(schedule);
                returnList.add(returnVo);
            }
            pageData.setResult(returnList);
            pageData.setTotalRow(totalCount.intValue());
        }else {
            pageData.setResult(new ArrayList<SiteWaveScheduleVo>());
            pageData.setTotalRow(0);
        }

        result.setData(pageData);
        return result;
    }

    private Result<Boolean> checkAndFillQueryParam(SiteWaveScheduleQuery query){
        Result<Boolean> result = Result.success();
        if(query.getPageSize() == null
                || query.getPageSize() <= 0) {
            query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
        }
        query.setLimit(query.getPageSize());
        if (query.getPageNumber() == 0){
            query.setPageNumber(1);
        }
        query.setOffset((query.getPageNumber() - 1) * query.getPageSize());

        return result;
    }

    /**
     * 多行数据拼接成一行
     * @param schedule
     * @return
     */
    private SiteWaveScheduleVo convertSiteWaveScheduleVo(SiteWaveSchedule schedule){
        SiteWaveScheduleVo returnVo = new SiteWaveScheduleVo();
        List<SiteWaveSchedule> detailList = siteWaveScheduleDao.queryPageDetail(schedule);
        if(detailList == null || detailList.size() != 9){
            log.error("数据查询出错！入参{}", JsonHelper.toJSONString(schedule));
            throw new RuntimeException("数据出错！");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        returnVo.setOrgCode(detailList.get(0).getOrgCode());
        returnVo.setOrgName(detailList.get(0).getOrgName());
        returnVo.setSiteCode(detailList.get(0).getSiteCode());
        returnVo.setSiteName(detailList.get(0).getSiteName());
        returnVo.setBusinessLineCode(detailList.get(0).getBusinessLineCode());
        returnVo.setBusinessLineName(detailList.get(0).getBusinessLineName());
        returnVo.setUpdateUser(detailList.get(0).getUpdateUser());
        returnVo.setUpdateUserName(detailList.get(0).getUpdateUserName());
        returnVo.setUpdateTime(detailList.get(0).getUpdateTime());

        Map<String, String> dayShift = new HashMap<>();
        Map<String, String> midShift = new HashMap<>();
        Map<String, String> nightShift = new HashMap<>();
        detailList.forEach(item -> {
            if (WaveTypeEnum.DAY.getCode().equals(item.getWaveCode())){
                if(item.getStartTime() != null && item.getEndTime() != null) {
                    String timeString = sdf.format(item.getStartTime());
                    timeString += "-";
                    timeString += sdf.format(item.getEndTime());
                    dayShift.put(ScheduleEnum.getNameByCode(item.getWavePeriod()), timeString);
                }else {
                    dayShift.put(ScheduleEnum.getNameByCode(item.getWavePeriod()), "");
                }
            }
            if (WaveTypeEnum.MIDDLE.getCode().equals(item.getWaveCode())){
                if(item.getStartTime() != null && item.getEndTime() != null) {
                    String timeString = sdf.format(item.getStartTime());
                    timeString += "-";
                    timeString += sdf.format(item.getEndTime());
                    midShift.put(ScheduleEnum.getNameByCode(item.getWavePeriod()), timeString);
                }else {
                    midShift.put(ScheduleEnum.getNameByCode(item.getWavePeriod()), "");
                }
            }
            if (WaveTypeEnum.NIGHT.getCode().equals(item.getWaveCode())){
                if(item.getStartTime() != null && item.getEndTime() != null) {
                    String timeString = sdf.format(item.getStartTime());
                    timeString += "-";
                    timeString += sdf.format(item.getEndTime());
                    nightShift.put(ScheduleEnum.getNameByCode(item.getWavePeriod()), timeString);
                }else {
                    nightShift.put(ScheduleEnum.getNameByCode(item.getWavePeriod()), "");
                }
            }
        });
        returnVo.setDayShift(dayShift);
        returnVo.setMidShift(midShift);
        returnVo.setNightShift(nightShift);
        return returnVo;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteWaveScheduleServiceImpl.queryTotalCount", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Long> queryTotalCount(SiteWaveScheduleQuery query) {
        Result<Long> result = Result.success();
        List<Long> totalList = siteWaveScheduleDao.queryTotalCount(query);
        return result.setData(totalList.stream().count());
    }

}
