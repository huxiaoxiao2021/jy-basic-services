package com.jdl.basic.provider.core.service.workStation.impl;


import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.enums.WaveTypeEnum;
import com.jdl.basic.common.utils.CheckHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringHelper;
import com.jdl.basic.provider.core.components.IGenerateObjectId;
import com.jdl.basic.provider.core.dao.workStation.WorkStationAttendPlanDao;
import com.jdl.basic.provider.core.service.workStation.WorkStationAttendPlanService;
import com.jdl.basic.provider.core.service.workStation.WorkStationGridService;
import com.jdl.basic.provider.core.service.workStation.WorkStationService;
import com.jdl.basic.rpc.Rpc.BaseMajorRpc;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 岗位人员出勤计划表--Service接口实现
 * 
 * @author wuyoude
 * @date 2021年12月30日 14:30:43
 *
 */
@Slf4j
@Service("workStationAttendPlanService")
public class WorkStationAttendPlanServiceImpl implements WorkStationAttendPlanService {

	@Autowired
	@Qualifier("workStationAttendPlanDao")
	private WorkStationAttendPlanDao workStationAttendPlanDao;
	
	@Autowired
	@Qualifier("workStationService")
	WorkStationService workStationService;
	
	@Autowired
	@Qualifier("workStationGridService")
	WorkStationGridService workStationGridService;
	
	@Autowired
	private BaseMajorRpc baseMajorManager;
	
	@Autowired
	private IGenerateObjectId genObjectId;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationAttendPlanServiceImpl.insert", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> insert(WorkStationAttendPlan insertData){
		Result<Boolean> result = checkAndFillBeforeAdd(insertData);
		if(!result.isSuccess()) {
			return result;
		}
		insertData.setBusinessKey(DmsConstants.CODE_PREFIX_WORK_STATION_ATTEND_PLAN.concat(StringHelper.padZero(this.genObjectId.getObjectId(WorkStationAttendPlan.class.getName()),11)));
		result.setData(workStationAttendPlanDao.insert(insertData) == 1);
		return result;
	 }
	/**
	 * 校验并填充数据add
	 * @param insertData
	 * @return
	 */
	private Result<Boolean> checkAndFillBeforeAdd(WorkStationAttendPlan insertData){
		Result<Boolean> result = checkAndFillNewData(insertData);
		if(!result.isSuccess()) {
			return result;
		}
		if(this.isExist(insertData)) {
			return result.toFail("该场地网格人员计划已存在，请修改！");
		}
		return result;
	}	
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationAttendPlanServiceImpl.updateById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> updateById(WorkStationAttendPlan updateData){
		Result<Boolean> result = Result.success();
		String planName = updateData.getPlanName();
		Integer planAttendNum = updateData.getPlanAttendNum();
		if(!CheckHelper.checkStr("方案名称", planName, 50, result).isSuccess()) {
			return result;
		}
		if(!CheckHelper.checkInteger("出勤计划人数", planAttendNum, 0,1000000, result).isSuccess()) {
			return result;
		}
		WorkStationAttendPlan oldData = workStationAttendPlanDao.queryById(updateData.getId());
		if(oldData == null) {
			return result.toFail("该计划数据已变更，请重新查询后修改！");
		}		
		workStationAttendPlanDao.deleteById(updateData);
		updateData.setId(null);		
		result.setData(workStationAttendPlanDao.insert(updateData) == 1);
		return result;
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationAttendPlanServiceImpl.deleteById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> deleteById(WorkStationAttendPlan deleteData){
		Result<Boolean> result = Result.success();
		result.setData(workStationAttendPlanDao.deleteById(deleteData) == 1);
		return result;
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationAttendPlanServiceImpl.queryById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<WorkStationAttendPlan> queryById(Long id){
		Result<WorkStationAttendPlan> result = Result.success();
		result.setData(this.fillOtherInfo(workStationAttendPlanDao.queryById(id)));
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationAttendPlanServiceImpl.queryPageList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<PageDto<WorkStationAttendPlan>> queryPageList(WorkStationAttendPlanQuery query){
		Result<PageDto<WorkStationAttendPlan>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		PageDto<WorkStationAttendPlan> pageData = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workStationAttendPlanDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
			List<WorkStationAttendPlan> dataList = workStationAttendPlanDao.queryList(query);
			if(CollectionUtils.isNotEmpty(dataList)) {
				for(WorkStationAttendPlan tmp: dataList) {
					this.fillOtherInfo(tmp);
				}
			}
			pageData.setResult(dataList);
			pageData.setTotalRow(totalCount.intValue());
		}else {
			pageData.setResult(new ArrayList<WorkStationAttendPlan>());
			pageData.setTotalRow(0);
		}
		
		result.setData(pageData);
		return result;
	 }
	/**
	 * 补充其他信息
	 * @param data
	 */
	private WorkStationAttendPlan fillOtherInfo(WorkStationAttendPlan data) {
		if(data != null) {
			data.setWaveName(WaveTypeEnum.getNameByCode(data.getWaveCode()));
		}
		return data;
	}
	/**
	 * 查询参数校验
	 * @param query
	 * @return
	 */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationAttendPlanServiceImpl.checkParamForQueryPageList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> checkParamForQueryPageList(WorkStationAttendPlanQuery query){
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
	
	private boolean isExist(WorkStationAttendPlan data) {
		return workStationAttendPlanDao.queryByBusinessKey(data) != null;
	}
	
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationAttendPlanServiceImpl.queryByBusinessKeys", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	@Cache(key = "WorkStationAttendPlanServiceImpl.queryByBusinessKeys@args0", memoryEnable = true, memoryExpiredTime = 2 * 60 * 1000
			,redisEnable = true, redisExpiredTime = 2 * 60 * 1000)
	public Result<WorkStationAttendPlan> queryByBusinessKeys(WorkStationAttendPlan data) {
		Result<WorkStationAttendPlan> result = Result.success();
		result.setData(this.fillOtherInfo(workStationAttendPlanDao.queryByBusinessKey(data)));
		return result;
	}	
	
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationAttendPlanServiceImpl.importDatas", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> importDatas(List<WorkStationAttendPlan> dataList) {
		Result<Boolean> result = checkAndFillImportDatas(dataList);
		if(!result.isSuccess()) {
			return result;
		}
		//先删除后插入新记录
		for(WorkStationAttendPlan data : dataList) {
			WorkStationAttendPlan oldData = workStationAttendPlanDao.queryByBusinessKey(data);
			if(oldData != null) {
				oldData.setUpdateUser(data.getCreateUser());
				oldData.setUpdateUserName(data.getCreateUserName());
				oldData.setUpdateTime(data.getCreateTime());
				workStationAttendPlanDao.deleteById(oldData);
				data.setBusinessKey(oldData.getBusinessKey());
			}else {
				data.setBusinessKey(DmsConstants.CODE_PREFIX_WORK_STATION_ATTEND_PLAN.concat(StringHelper.padZero(this.genObjectId.getObjectId(WorkStationAttendPlan.class.getName()),11)));
			}			
			workStationAttendPlanDao.deleteByBusinessKey(data);
			workStationAttendPlanDao.insert(data);
		}
		return result;
	}
	/**
	 * 校验并填充导入数据
	 * @param dataList
	 * @return
	 */
	private Result<Boolean> checkAndFillImportDatas(List<WorkStationAttendPlan> dataList){
		Result<Boolean> result = Result.success();
		if(CollectionUtils.isEmpty(dataList)) {
			return result.toFail("导入数据为空！");
		}
		//逐条校验
		int rowNum = 1;
		Map<String,Integer> uniqueKeysRowNumMap = new HashMap<String,Integer>();
		for(WorkStationAttendPlan data : dataList) {
			String rowKey = "第" + rowNum + "行";
			Result<Boolean> result0 = checkAndFillNewData(data);
			if(!result0.isSuccess()) {
				return result0.toFail(rowKey + result0.getMessage());
			}
			//导入数据防重校验
			String uniqueKeysStr = getUniqueKeysStr(data);
			if(uniqueKeysRowNumMap.containsKey(uniqueKeysStr)) {
				return result0.toFail(rowKey + "和第"+uniqueKeysRowNumMap.get(uniqueKeysStr)+"行数据重复！");
			}
			uniqueKeysRowNumMap.put(uniqueKeysStr, rowNum);
			rowNum ++;
		}
		return result;
	}
	private String getUniqueKeysStr(WorkStationAttendPlan data) {
		if(data != null ) {
			return data.getRefGridKey().toString()
					.concat(DmsConstants.KEYS_SPLIT)
					.concat(data.getWaveCode().toString());
		}
		return null;
	}
	/**
	 * 校验并填充导入数据
	 * @param data
	 * @return
	 */
	private Result<Boolean> checkAndFillNewData(WorkStationAttendPlan data){
		Result<Boolean> result = Result.success();
		Integer siteCode = data.getSiteCode();
		Integer floor = data.getFloor();
		String gridNo = data.getGridNo();
		String workCode = data.getWorkCode();
		String areaCode = data.getAreaCode();
		Integer waveCode = data.getWaveCode();
		String planName = data.getPlanName();
		Integer planAttendNum = data.getPlanAttendNum();
		if(!CheckHelper.checkStr("方案名称", planName, 50, result).isSuccess()) {
			return result;
		}		
		if(siteCode == null) {
			return result.toFail("场地ID为空！");
		}
		if(!CheckHelper.checkInteger("楼层", floor, 1,5, result).isSuccess()) {
			return result;
		}
		//校验gridNo
		if(StringHelper.isEmpty(gridNo)) {
			return result.toFail("网格号为空！");
		}
		if(gridNo.length()<2) {
			gridNo = "0".concat(gridNo);
			data.setGridNo(gridNo);
		}
		if(!CheckHelper.checkGridNo(gridNo, result).isSuccess()) {
			return result;
		}
		if(!CheckHelper.checkStr("作业区ID", areaCode, 50, result).isSuccess()) {
			return result;
		}		
		if(!CheckHelper.checkStr("工序ID", workCode, 50, result).isSuccess()) {
			return result;
		}
		if(WaveTypeEnum.getEnum(waveCode) == null) {
			return result.toFail("班次类型只能录入【"+WaveTypeEnum.getAllNames()+"】！");
		}
		if(!CheckHelper.checkInteger("出勤计划人数", planAttendNum, 0,1000000, result).isSuccess()) {
			return result;
		}
		BaseStaffSiteOrgDto siteInfo = baseMajorManager.getBaseSiteBySiteId(siteCode);
		if(siteInfo == null) {
			return result.toFail("青龙ID在基础资料中不存在！");
		}
		data.setOrgCode(siteInfo.getOrgId());
		
		WorkStation workStationCheckQuery = new WorkStation();
		workStationCheckQuery.setWorkCode(workCode);
		workStationCheckQuery.setAreaCode(areaCode);
		Result<WorkStation> workStationData = workStationService.queryByBusinessKey(workStationCheckQuery);
		if(workStationData == null
				|| workStationData.getData() == null) {
			return result.toFail("作业区工序信息不存在，请先维护作业区及工序信息！");
		}
		WorkStation workStation = workStationData.getData();
		data.setRefStationKey(workStation.getBusinessKey());
		//校验并设置网格信息
		WorkStationGrid workStationGridCheckQuery = new WorkStationGrid();
		workStationGridCheckQuery.setSiteCode(siteCode);
		workStationGridCheckQuery.setFloor(floor);
		workStationGridCheckQuery.setGridNo(gridNo);
		workStationGridCheckQuery.setRefStationKey(workStation.getBusinessKey());
		Result<WorkStationGrid> workStationGridData = workStationGridService.queryByBusinessKey(workStationGridCheckQuery);
		if(workStationGridData == null
				|| workStationGridData.getData() == null) {
			return result.toFail("网格信息不存在，请先维护场地网格信息！");
		}
		data.setRefGridKey(workStationGridData.getData().getBusinessKey());
		return result;
	}
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationAttendPlanServiceImpl.queryWaveDictList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<List<WorkStationAttendPlan>> queryWaveDictList(WorkStationAttendPlanQuery query) {
		Result<List<WorkStationAttendPlan>> result = Result.success();
		result.setData(workStationAttendPlanDao.queryWaveDictList(query));
		return result;
	}
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationAttendPlanServiceImpl.deleteByIds", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> deleteByIds(DeleteRequest<WorkStationAttendPlan> deleteRequest) {
		Result<Boolean> result = Result.success();
		if(deleteRequest == null
				|| CollectionUtils.isEmpty(deleteRequest.getDataList())) {
			return result.toFail("参数错误，删除列表不能为空！");
		}
		List<WorkStationAttendPlan> oldDataList = workStationAttendPlanDao.queryByIds(deleteRequest);
		if(CollectionUtils.isEmpty(oldDataList)
				|| oldDataList.size() < deleteRequest.getDataList().size()) {
			return result.toFail("参数错误，数据已变更请刷新列表后重新选择！");
		}
		for(WorkStationAttendPlan oldData : oldDataList) {
			if(!ObjectUtils.equals(oldData.getSiteCode(), deleteRequest.getOperateSiteCode())) {
				return result.toFail("网格计划【"+oldData.getPlanName()+ "】非本人所在场地数据，无法删除！");
			}
		}
		result.setData(workStationAttendPlanDao.deleteByIds(deleteRequest) > 0);
		return result;
	}
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationAttendPlanServiceImpl.queryCount", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Long> queryCount(WorkStationAttendPlanQuery query) {
		Result<Long> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		result.setData(workStationAttendPlanDao.queryCount(query));
		return result;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationAttendPlanServiceImpl.queryListForExport", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<List<WorkStationAttendPlan>> queryListForExport(WorkStationAttendPlanQuery query) {
		Result<List<WorkStationAttendPlan>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkStationAttendPlan> dataList = workStationAttendPlanDao.queryListForExport(query);
		if(CollectionUtils.isNotEmpty(dataList)) {
			for(WorkStationAttendPlan tmp: dataList) {
				this.fillOtherInfo(tmp);
			}
		}
		result.setData(dataList);
		return result;
	}
}
