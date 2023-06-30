package com.jdl.basic.provider.core.service.work.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfig;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigArea;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigQuery;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigVo;
import com.jdl.basic.api.enums.FrequencyTypeEnum;
import com.jdl.basic.api.enums.FrequencyTypeEnum.FrequencyItem;
import com.jdl.basic.api.enums.WorkFinishTypeEnum;
import com.jdl.basic.api.enums.WorkTurnTypeEnum;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringHelper;
import com.jdl.basic.provider.core.dao.work.WorkGridManagerTaskConfigDao;
import com.jdl.basic.provider.core.service.work.WorkGridManagerTaskConfigAreaService;
import com.jdl.basic.provider.core.service.work.WorkGridManagerTaskConfigService;
import com.jdl.basic.provider.core.service.workStation.WorkAreaService;
import com.jdl.basic.provider.mq.producer.DefaultJMQProducer;

import lombok.extern.slf4j.Slf4j;

/**
 * 作业区巡检任务配置表--Service接口实现
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
@Slf4j
@Service("workGridManagerTaskConfigService")
public class WorkGridManagerTaskConfigServiceImpl implements WorkGridManagerTaskConfigService {

	@Autowired
	@Qualifier("workGridManagerTaskConfigDao")
	private WorkGridManagerTaskConfigDao workGridManagerTaskConfigDao;
	
	@Autowired
	@Qualifier("workGridManagerTaskConfigAreaService")
	private WorkGridManagerTaskConfigAreaService workGridManagerTaskConfigAreaService;
	
	@Autowired
	@Qualifier("workAreaService")
	private WorkAreaService workAreaService;
	
	@Autowired
	@Qualifier("workGridManagerTaskConfigMq")
	DefaultJMQProducer workGridManagerTaskConfigMq;
	

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridManagerTaskConfigVo insertData){
		Result<Boolean> result = Result.success();
		//设置推送天
		StringBuffer frequencyDaysStr = new StringBuffer();
		FrequencyTypeEnum frequencyTypeEnum = FrequencyTypeEnum.getEnum(insertData.getFrequencyType());
		if(frequencyTypeEnum == null) {
			return result.toFail("推送周期值无效！");
		}
		if(CollectionUtils.isNotEmpty(insertData.getFrequencyDayList())) {
			for(Integer day : insertData.getFrequencyDayList()) {
				FrequencyItem dayItem = frequencyTypeEnum.getFrequencyItem(day);
				if(dayItem != null) {
					frequencyDaysStr.append(dayItem.getCode());
					frequencyDaysStr.append(StringHelper.DAY_NAME__SPLIT);
				}else {
					return result.toFail("推送工作日值无效！");
				}
			}
			insertData.setFrequencyDays(frequencyDaysStr.substring(0, frequencyDaysStr.length() -1));
		}
		//设置area值
		if(CollectionUtils.isNotEmpty(insertData.getWorkAreaCodeList())) {
			List<WorkGridManagerTaskConfigArea> configAreaList = new ArrayList<>();
			for(String areaCode : insertData.getWorkAreaCodeList()) {
				WorkGridManagerTaskConfigArea configArea = new WorkGridManagerTaskConfigArea();
				configArea.setAreaCode(areaCode);
				configArea.setTaskConfigCode(insertData.getTaskConfigCode());
				configArea.setCreateTime(insertData.getCreateTime());
				configArea.setCreateUser(insertData.getCreateUser());
				configArea.setCreateUserName(insertData.getCreateUserName());
				configAreaList.add(configArea);
			}
			workGridManagerTaskConfigAreaService.batchInsert(configAreaList);
		}
		
		result.setData(workGridManagerTaskConfigDao.insert(insertData) == 1);
		
		return result;
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridManagerTaskConfigVo updateData){
		Result<Boolean> result = Result.success();
		//设置推送天
		StringBuffer frequencyDaysStr = new StringBuffer();
		FrequencyTypeEnum frequencyTypeEnum = FrequencyTypeEnum.getEnum(updateData.getFrequencyType());
		if(frequencyTypeEnum == null) {
			return result.toFail("推送周期值无效！");
		}
		if(CollectionUtils.isNotEmpty(updateData.getFrequencyDayList())) {
			for(Integer day : updateData.getFrequencyDayList()) {
				FrequencyItem dayItem = frequencyTypeEnum.getFrequencyItem(day);
				if(dayItem != null) {
					frequencyDaysStr.append(dayItem.getCode());
					frequencyDaysStr.append(StringHelper.COMMON_SPLIT);
				}else {
					return result.toFail("推送工作日值无效！");
				}
			}
			updateData.setFrequencyDays(frequencyDaysStr.substring(0, frequencyDaysStr.length() -1));
		}
		WorkGridManagerTaskConfigArea deleteData = new WorkGridManagerTaskConfigArea();
		deleteData.setTaskConfigCode(updateData.getTaskConfigCode());
		deleteData.setUpdateTime(updateData.getUpdateTime());
		deleteData.setUpdateUser(updateData.getUpdateUser());
		deleteData.setUpdateUserName(updateData.getUpdateUserName());				
		workGridManagerTaskConfigAreaService.deleteByTaskConfigCode(deleteData);
		//设置area值
		if(CollectionUtils.isNotEmpty(updateData.getWorkAreaCodeList())) {
			List<WorkGridManagerTaskConfigArea> configAreaList = new ArrayList<>();
			for(String areaCode : updateData.getWorkAreaCodeList()) {
				WorkGridManagerTaskConfigArea configArea = new WorkGridManagerTaskConfigArea();
				configArea.setAreaCode(areaCode);
				configArea.setTaskConfigCode(updateData.getTaskConfigCode());
				configArea.setCreateTime(updateData.getCreateTime());
				configArea.setCreateUser(updateData.getCreateUser());
				configArea.setCreateUserName(updateData.getCreateUserName());
				configAreaList.add(configArea);
			}
			workGridManagerTaskConfigAreaService.batchInsert(configAreaList);
		}		
		result.setData(workGridManagerTaskConfigDao.updateById(updateData) == 1);
		workGridManagerTaskConfigMq.sendOnFailPersistent(updateData.getTaskConfigCode(), JsonHelper.toJSONString(updateData));
		return result;
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridManagerTaskConfig deleteData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerTaskConfigDao.deleteById(deleteData) == 1);
		return result;
	 }
	@Cache(key = "workGridManagerTaskConfigService.queryByTaskConfigCode@args0", memoryEnable = true, memoryExpiredTime = 2 * 60 * 1000
	,redisEnable = true, redisExpiredTime = 2 * 60 * 1000)	
	@Override
	public Result<WorkGridManagerTaskConfigVo> queryByTaskConfigCode(String taskConfigCode) {
		Result<WorkGridManagerTaskConfigVo> result = Result.success();
		result.setData(fillWorkGridManagerTaskConfig(workGridManagerTaskConfigDao.queryByTaskConfigCode(taskConfigCode)));
		return result;
	}	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridManagerTaskConfigVo> queryById(Long id){
		Result<WorkGridManagerTaskConfigVo> result = Result.success();
		result.setData(fillWorkGridManagerTaskConfig(workGridManagerTaskConfigDao.queryById(id)));
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridManagerTaskConfigVo>> queryPageList(WorkGridManagerTaskConfigQuery query){
		Result<PageDto<WorkGridManagerTaskConfigVo>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkGridManagerTaskConfigVo> voDataList = new ArrayList<>();
		PageDto<WorkGridManagerTaskConfigVo> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workGridManagerTaskConfigDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
		    List<WorkGridManagerTaskConfig> dataList = workGridManagerTaskConfigDao.queryList(query);
		    for (WorkGridManagerTaskConfig tmp : dataList) {
		    	voDataList.add(this.fillWorkGridManagerTaskConfig(tmp));
		    }
		}
		pageDto.setResult(voDataList);
		pageDto.setTotalRow(totalCount.intValue());
		result.setData(pageDto);
		return result;
	 }
	/**
	 * 查询参数校验
	 * @param query
	 * @return
	 */
	public Result<Boolean> checkParamForQueryPageList(WorkGridManagerTaskConfigQuery query){
		Result<Boolean> result = Result.success();
		if(query.getPageSize() == null || query.getPageSize() <= 0) {
			query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
		};
		query.setOffset(0);
		query.setLimit(query.getPageSize());
		if(query.getPageSize() == null || query.getPageNumber() > 0) {
			query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
		};
		return result;
	 }
	/**
	 * 对象转换成vo
	 * @param data
	 * @return
	 */
	public WorkGridManagerTaskConfigVo fillWorkGridManagerTaskConfig(WorkGridManagerTaskConfig data){
		if(data == null) {
			return null;
		}
		WorkGridManagerTaskConfigVo voData = new WorkGridManagerTaskConfigVo();
		BeanUtils.copyProperties(data, voData);
		voData.setFrequencyTypeName(FrequencyTypeEnum.getNameByCode(voData.getFrequencyType()));
		voData.setFinishTypeName(WorkFinishTypeEnum.getNameByCode(voData.getFinishType()));
		voData.setGridTurnTypeName(WorkTurnTypeEnum.getNameByCode(voData.getGridTurnType()));
		FrequencyTypeEnum frequencyTypeEnum = FrequencyTypeEnum.getEnum(voData.getFrequencyType());
		List<Integer> frequencyDayList = new ArrayList<>();
		//设置推送日期
		if(frequencyTypeEnum != null 
				&& StringUtils.isNotBlank(voData.getFrequencyDays())) {
			List<String> daysList = StringHelper.splitToList(voData.getFrequencyDays(), StringHelper.COMMON_SPLIT);
			
			StringBuffer frequencyDaysName = new StringBuffer();
			int daysNum = 0;
			if(CollectionUtils.isNotEmpty(daysList)) {
				for(String day : daysList) {
					FrequencyItem itemData = frequencyTypeEnum.getFrequencyItem(day);
					if(itemData != null) {
						daysNum++;
						frequencyDaysName.append(itemData.getName());
						frequencyDayList.add(itemData.getCode());
						frequencyDaysName.append(StringHelper.DAY_NAME__SPLIT);
					}
				}
			}
			if(daysNum > 0) {
				voData.setFrequencyDaysName(frequencyDaysName.substring(0, frequencyDaysName.length() -1));
			}
		}
		voData.setFrequencyDayList(frequencyDayList);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
		if(voData.getFrequencyHour() != null) {
			calendar.set(Calendar.HOUR_OF_DAY,voData.getFrequencyHour());
		}
		if(voData.getFrequencyMinute() != null) {
			calendar.set(Calendar.MINUTE,voData.getFrequencyMinute());
		}
		voData.setFrequencyTimeStr(DateHelper.getDateOfHH_mm(calendar.getTime()));
		List<String> areaCodeList = workGridManagerTaskConfigAreaService.queryAreaCodeListByTaskConfigCode(voData.getTaskConfigCode());
		voData.setWorkAreaList(workAreaService.queryListByAreaCodeList(areaCodeList));
		voData.setWorkAreaCodeList(areaCodeList);
		//特殊字段设置
		return voData;
	 }

}
