package com.jdl.basic.provider.core.service.workStation.impl;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.enums.WaveTypeEnum;
import com.jdl.basic.common.enums.WorkerTypeEnum;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.workStation.SiteAttendPlanDao;
import com.jdl.basic.provider.core.service.workStation.SiteAttendPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service("siteAttendPlanService")
public class SiteAttendPlanServiceImpl implements SiteAttendPlanService {

    @Autowired
    private SiteAttendPlanDao siteAttendPlanDao;

    @Override
    @Transactional
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteAttendPlanServiceImpl.importDatas", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> importDatas(List<SiteAttendPlanVo> dataList) {
        log.info("导入入参{}", JsonHelper.toJSONString(dataList));
        Result<Boolean> result = Result.success();
        if(CollectionUtils.isEmpty(dataList)){
            result.toFail("导入数据为空！");
            return result;
        }

        Result<Boolean> checkResult = this.checkBeforeImport(dataList);
        if (checkResult.isFail()){
            return result.toFail(checkResult.getMessage());
        }

        Integer rowNum = 1;
        for(SiteAttendPlanVo siteAttendPlanVo : dataList){
            String rowKey = "第" + rowNum + "行:";
            ArrayList<SiteAttendPlan> importDataList = new ArrayList<>();
            checkResult = convertDto(siteAttendPlanVo, importDataList);
            if(checkResult.isFail()){
                result.toFail(rowKey + checkResult.getMessage());
                return result;
            }

            if(importDataList.size() < 15){
                result.toFail(rowKey + "请严格按照导入模板填写数据！");
                return result;
            }
            for(SiteAttendPlan plan : importDataList){
                if(plan.getVersionNum() != null && plan.getVersionNum() > 0){
                    log.info("删除旧数据{}", plan);
                    if(!Objects.equals(siteAttendPlanDao.deleteOldDataByBusinessKey(plan), Constants.YN_YES)){
                        log.error("删除失败,BusinessKey:【attendPlanTime:{}, orgCode:{}, siteCode:{}】", plan.getPlanAttendTime(), plan.getOrgCode(), plan.getSiteCode());
                        throw  new RuntimeException("删除旧数据失败！");
                    }
                }
                if(!Objects.equals(siteAttendPlanDao.insert(plan), Constants.YN_YES)){
                    log.error("新增数据失败,入参{}", JsonHelper.toJSONString(plan));
                    throw  new RuntimeException("新增数据失败！");
                }
            }
            rowNum++;
        }
        return result;
    }

    private Result<Boolean> checkBeforeImport(List<SiteAttendPlanVo> voList){
        Result<Boolean> result = Result.success();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Set<Date> uniqueDateSet = new HashSet<>();
        Integer rowNum = 1;
        for (SiteAttendPlanVo vo : voList){
            String rowKey = "第" + rowNum + "行:";
            Date planAttendTime;
            try{
                planAttendTime = sdf.parse(vo.getPlanAttendTime());
                if(uniqueDateSet.contains(planAttendTime)){
                    result.toFail(rowKey + "日期重复！");
                    return result;
                }
                //出勤日期不能为当日或者历史日期
                if(planAttendTime.getTime() < new Date().getTime() || sdf.format(planAttendTime).equals(sdf.format(new Date()))){
                    result.toFail(rowKey + "出勤日期不能为当日或者历史日期！");
                    return result;
                }
            } catch (Exception e) {
                result.toFail(rowKey + "日期格式不对！");
                return result;
            }
            uniqueDateSet.add(planAttendTime);

            if(CollectionUtils.isEmpty(vo.getDayShift())
                    || CollectionUtils.isEmpty(vo.getMidShift()) ||
                    CollectionUtils.isEmpty(vo.getNightShift())){
                result.toFail(rowKey + "请严格按照导入模板填写数据！");
                return result;
            }
        }
        return result;
    }

    private Result<Boolean> convertDto(SiteAttendPlanVo vo, List<SiteAttendPlan> importDataList){
        Result<Boolean> result = Result.success();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date planAttendTime;
        try{
            planAttendTime = sdf.parse(vo.getPlanAttendTime());
        } catch (Exception e) {
            result.toFail("日期格式不对！");
            return result;
        }
        //白班
        Integer dayShiftEmployeeNum = checkAndInitNewData(vo.getDayShift(), vo, planAttendTime, WaveTypeEnum.DAY, importDataList);
        //中班
        Integer midShiftEmployeeNum = checkAndInitNewData(vo.getMidShift(), vo, planAttendTime, WaveTypeEnum.MIDDLE, importDataList);
        //夜班
        Integer nightShiftEmployeeNum = checkAndInitNewData(vo.getNightShift(), vo, planAttendTime, WaveTypeEnum.NIGHT, importDataList);
        Integer totalPlanAttendNum = dayShiftEmployeeNum + midShiftEmployeeNum + nightShiftEmployeeNum;
        if(totalPlanAttendNum == 0){
            result.toFail("计划出勤人数为0，必须要有出勤人！");
            return result;
        }

        return result;
    }

    private SiteAttendPlan genSiteAttendPlan(SiteAttendPlanVo vo, Date planAttendTime){
        SiteAttendPlan siteAttendPlan = new SiteAttendPlan();
        siteAttendPlan.setPlanAttendTime(planAttendTime);
        siteAttendPlan.setOrgCode(vo.getOrgCode());
        siteAttendPlan.setOrgName(vo.getOrgName());
        siteAttendPlan.setSiteCode(vo.getSiteCode());
        siteAttendPlan.setSiteName(vo.getSiteName());
        siteAttendPlan.setBusinessLineCode(vo.getBusinessLineCode());
        siteAttendPlan.setBusinessLineName(vo.getBusinessLineName());
        siteAttendPlan.setUpdateUser(vo.getUpdateUser());
        siteAttendPlan.setUpdateUserName(vo.getUpdateUserName());
        siteAttendPlan.setUpdateTime(new Date());
        return siteAttendPlan;
    }

    /**
     * 列转行--将列数据拆分成数据库行
     * @param map
     * @param vo
     * @param planAttendTime
     * @param importDataList
     * @return
     */
    private Integer checkAndInitNewData(Map<String, Integer> map, SiteAttendPlanVo vo, Date planAttendTime, WaveTypeEnum wave, List<SiteAttendPlan> importDataList){
        //一个班次的总人数
        Integer shiftEmployeeNum = 0;
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            shiftEmployeeNum += entry.getValue();
            //设置查询条件
            SiteAttendPlan siteAttendPlan = genSiteAttendPlan(vo, planAttendTime);
            siteAttendPlan.setWaveCode(wave.getCode());
            siteAttendPlan.setJobType(WorkerTypeEnum.getJobTypeByName(entry.getKey()));
            SiteAttendPlan oldData = siteAttendPlanDao.queryOldDataByBusinessKey(siteAttendPlan);
            if(oldData != null){
                log.info("olddata{}", oldData);
                siteAttendPlan.setCreateUser(oldData.getCreateUser());
                siteAttendPlan.setCreateUserName(oldData.getCreateUserName());
                siteAttendPlan.setCreateTime(oldData.getCreateTime());
                siteAttendPlan.setVersionNum(oldData.getVersionNum() + 1);
            }else {
                siteAttendPlan.setCreateUser(vo.getCreateUser());
                siteAttendPlan.setCreateUserName(vo.getCreateUserName());
                siteAttendPlan.setCreateTime(new Date());
            }
            siteAttendPlan.setPlanAttendNum(entry.getValue());
            importDataList.add(siteAttendPlan);
        }
        return shiftEmployeeNum;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteAttendPlanServiceImpl.queryPageList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<PageDto<SiteAttendPlanVo>> queryPageList(SiteAttendPlanQuery query) {
        Result<PageDto<SiteAttendPlanVo>> result = Result.success();
        Result<Boolean> checkResult = checkAndFillQueryParam(query);
        if(!checkResult.isSuccess()){
            return Result.fail(checkResult.getMessage());
        }
        PageDto<SiteAttendPlanVo> pageData = new PageDto<>(query.getPageNumber(), query.getPageSize());
        log.info("query:{}",query);
        List<Long> totalListCount = siteAttendPlanDao.queryTotalCount(query);
        Long totalCount = totalListCount.stream().count();
        if(totalCount != null && totalCount > 0){
            List<SiteAttendPlanVo> returnList = new ArrayList<>();
            List<SiteAttendPlan> pageList = siteAttendPlanDao.queryPageList(query);

            log.info("pageList{}", JsonHelper.toJSONString(pageList));
            for (SiteAttendPlan plan : pageList){
                //行转列
                SiteAttendPlanVo returnVo = convertSiteAttendPlanVo(plan);
                returnList.add(returnVo);
            }
            pageData.setResult(returnList);
            pageData.setTotalRow(totalCount.intValue());
        }else {
            pageData.setResult(new ArrayList<SiteAttendPlanVo>());
            pageData.setTotalRow(0);
        }

        result.setData(pageData);
        return result;
    }

    /**
     * 数据库行转列--填充详细数据
     * @param plan
     * @return
     */
    private SiteAttendPlanVo convertSiteAttendPlanVo(SiteAttendPlan plan){
        log.info("{}", plan);
        SiteAttendPlanVo returnVo = new SiteAttendPlanVo();
        List<SiteAttendPlan> detailList = siteAttendPlanDao.queryPageDetail(plan);
        log.info("{}", detailList);
        if(detailList == null || detailList.size() != 15){
            log.error("数据查询出错！入参{}", JsonHelper.toJSONString(plan));
            throw new RuntimeException("数据出错！");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        returnVo.setPlanAttendTime(sdf.format(detailList.get(0).getPlanAttendTime()));
        returnVo.setOrgCode(detailList.get(0).getOrgCode());
        returnVo.setOrgName(detailList.get(0).getOrgName());
        returnVo.setSiteCode(detailList.get(0).getSiteCode());
        returnVo.setSiteName(detailList.get(0).getSiteName());
        returnVo.setBusinessLineCode(detailList.get(0).getBusinessLineCode());
        returnVo.setBusinessLineName(detailList.get(0).getBusinessLineName());
        returnVo.setStatus(detailList.get(0).getStatus());
        returnVo.setCreateUser(detailList.get(0).getCreateUser());
        returnVo.setCreateTime(detailList.get(0).getCreateTime());
        returnVo.setConfirmUser(detailList.get(0).getConfirmUser());
        returnVo.setConfirmTime(detailList.get(0).getConfirmTime());

        Integer dayShiftTotalEmployee = 0;
        Integer midShiftTotalEmployee = 0;
        Integer nightShiftTotalEmployee = 0;
        Map<String, Integer> dayShift = new HashMap<>();
        Map<String, Integer> midShift = new HashMap<>();
        Map<String, Integer> nightShift = new HashMap<>();

        for(SiteAttendPlan detail : detailList){

            if(WaveTypeEnum.DAY.getCode().equals(detail.getWaveCode())){
                dayShiftTotalEmployee += detail.getPlanAttendNum();
                dayShift.put(WorkerTypeEnum.getNameByJobType(detail.getJobType()), detail.getPlanAttendNum());
            }
            if(WaveTypeEnum.MIDDLE.getCode().equals(detail.getWaveCode())){
                midShiftTotalEmployee += detail.getPlanAttendNum();
                midShift.put(WorkerTypeEnum.getNameByJobType(detail.getJobType()), detail.getPlanAttendNum());
            }
            if(WaveTypeEnum.NIGHT.getCode().equals(detail.getWaveCode())){
                nightShiftTotalEmployee += detail.getPlanAttendNum();
                nightShift.put(WorkerTypeEnum.getNameByJobType(detail.getJobType()), detail.getPlanAttendNum());
            }
        }

        returnVo.setDayShiftTotalEmployeeNum(dayShiftTotalEmployee);
        returnVo.setMidShiftTotalEmployeeNum(midShiftTotalEmployee);
        returnVo.setNightShiftTotalEmployeeNum(nightShiftTotalEmployee);
        returnVo.setTotalEmployeeNum(dayShiftTotalEmployee + midShiftTotalEmployee + nightShiftTotalEmployee);
        returnVo.setDayShift(dayShift);
        returnVo.setMidShift(midShift);
        returnVo.setNightShift(nightShift);
        return returnVo;
    }

    private Result<Boolean> checkAndFillQueryParam(SiteAttendPlanQuery query){
        Result<Boolean> result = Result.success();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (query.getStartTimeStr() != null) {
                query.setStartTime(sdf.parse(query.getStartTimeStr()));
            }
            if (query.getEndTimeStr() != null){
                query.setEndTime(sdf.parse(query.getEndTimeStr()));
            }
        }catch (Exception e){
            log.error("查询失败",e);
            result.toFail("查询失败，请联系分拣小秘进行处理！");
        }
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

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteAttendPlanServiceImpl.confirmOneRecord", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> confirmOneRecord(SiteAttendPlanVo vo) {
        Result<Boolean> result = Result.success();
        if(vo.getPlanAttendTime() == null){
            result.toFail("计划出勤日期不能为空！");
            return result;
        }
        if(vo.getOrgCode() == null){
            result.toFail("区域不能为空！");
            return result;
        }
        if(vo.getSiteCode() == null){
            result.toFail("场地ID不能为空！");
            return result;
        }
        vo.setConfirmTime(new Date());
        log.info("confirm{}", vo);
        if(siteAttendPlanDao.confirmOneRecord(vo) < Constants.YN_YES){
            log.error("确认场地出勤计划失败！，入参{}", JsonHelper.toJSONString(vo));
            result.toFail("场地出勤计划数据不存在或已确认!");
            return result;
        }
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".SiteAttendPlanServiceImpl.confirmRecords", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Boolean> confirmRecords(List<SiteAttendPlanVo> dataList) {
        Result<Boolean> result= Result.success();
        for(SiteAttendPlanVo vo : dataList){
            Result<Boolean> confirmResult = confirmOneRecord(vo);
            if(confirmResult.isFail()){
                result.toFail(confirmResult.getMessage());
                return result;
            }
        }
        return result;
    }

    @Override
    public Result<Long> queryTotalCount(SiteAttendPlanQuery query) {
        List<Long> totalListCount = siteAttendPlanDao.queryTotalCount(query);
        Long totalCount = totalListCount.stream().count();
        Result<Long> result = Result.success();
        return result.setData(totalCount);
    }

}
