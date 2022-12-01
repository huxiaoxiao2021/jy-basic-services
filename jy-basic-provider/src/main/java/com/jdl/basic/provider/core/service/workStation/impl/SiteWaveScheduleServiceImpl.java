package com.jdl.basic.provider.core.service.workStation.impl;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.workStation.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

        Integer rowNum = 1;
        for (SiteWaveScheduleVo vo : dataList){
            String rowKey = "第" + rowNum + "行：";
            List<SiteWaveSchedule> importDatas = new ArrayList<>();
            Result<Boolean> checkResult = this.checkAndConvertDto(vo, importDatas);
            if (checkResult.isFail()){
                return result.toFail(rowKey + checkResult.getMessage());
            }

            log.info("before {}", importDatas);

            for (SiteWaveSchedule insertData : importDatas){
                log.info("insert {}", insertData);
                if (!Objects.equals(siteWaveScheduleDao.insert(insertData), Constants.YN_YES)){
                    log.info("数据插入数据库失败！入参{}", JsonHelper.toJSONString(insertData));
                    return result.toFail(rowKey + "导入失败！");
                }
            }
        }
        return result;
    }

    private Result<Boolean> checkAndConvertDto(SiteWaveScheduleVo vo, List<SiteWaveSchedule> importDatas){
        Result<Boolean> result = Result.success();
        if(CollectionUtils.isEmpty(vo.getDayShift()) && CollectionUtils.isEmpty(vo.getMidShift()) && CollectionUtils.isEmpty(vo.getNightShift())){
            return result.toFail("时间格式不对或没有出勤时间！");
        }
        try {
            genSiteWaveSchedule(vo, importDatas);
        }catch (Exception e){
            log.info("时间格式不对！入参{}", JsonHelper.toJSONString(vo));
            return result.toFail("时间格式不对！");
        }

        List<SiteWaveSchedule> copyList = importDatas.stream().filter(item -> item.getStartTime() != null).collect(Collectors.toList());
        //升序
        Collections.sort(copyList,
                (op1, op2) -> (int) (op1.getStartTime().getTime() - op2.getStartTime().getTime()));
        //比较各时间段是否重叠
        long minTime = copyList.get(0).getEndTime().getTime();
        for (int i = 1; i < copyList.size(); i++){
            long sub = copyList.get(i).getStartTime().getTime() - minTime;
            if (sub < 0){
                log.info("时间重叠!, 入参{}", JsonHelper.toJSONString(vo));
                return result.toFail("时间重叠!");
            }
            minTime = copyList.get(i).getStartTime().getTime();
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
                insertData.setVersionNum(oldData.getVersionNum() + 1);
            }else {
                insertData.setCreateUser(vo.getCreateUser());
                insertData.setCreateUserName(vo.getCreateUserName());
                insertData.setCreateTime(new Date());
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
                newData.setStartTime(startTime);
                newData.setEndTime(endTime);
            }

            newData.setOrgCode(vo.getOrgCode());
            newData.setOrgName(vo.getOrgName());
            newData.setSiteCode(vo.getSiteCode());
            newData.setSiteName(vo.getSiteName());
            newData.setWaveCode(waveTypeEnum.getCode());
            newData.setWavePeriod(typeEnum.getCode());
            newData.setUpdateUser(vo.getUpdateUser());
            newData.setUpdateUserName(vo.getUpdateUserName());
            newData.setUpdateTime(new Date());
            list.add(newData);
        }
    }

    private void genSiteWaveSchedule(SiteWaveScheduleVo vo, List<SiteWaveSchedule> importDatas) throws Exception {
        Map<String, String> dayShift = vo.getDayShift();
        getCommonValues(dayShift, vo, WaveTypeEnum.DAY, importDatas);
        Map<String, String> midShift = vo.getMidShift();
        getCommonValues(midShift, vo, WaveTypeEnum.MIDDLE, importDatas);
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
        List<Long> totalList = siteWaveScheduleDao.queryTotalCount(query);
        Long totalCount = totalList.stream().count();
        if(totalCount != null && totalCount > 0){
            List<SiteWaveScheduleVo> returnList = new ArrayList<>();
            List<SiteWaveSchedule> pageList = siteWaveScheduleDao.queryPageList(query);
            for (SiteWaveSchedule schedule : pageList){
                //行转列
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
        query.setOffset(0);
        query.setLimit(query.getPageSize());
        if(query.getPageNumber() > 0) {
            query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
        }

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
        log.info("dayShift{}", dayShift);
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
