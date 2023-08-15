package com.jdl.basic.provider.core.service.workStation.impl;

import com.jdl.basic.api.domain.workStation.WorkAreaLabel;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.provider.core.dao.workStation.WorkAreaLabelDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jd.jsf.gd.util.StringUtils;
import com.jdl.basic.api.domain.workStation.WorkArea;
import com.jdl.basic.api.domain.workStation.WorkAreaQuery;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.workStation.WorkAreaDao;
import com.jdl.basic.provider.core.service.workStation.WorkAreaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作业区信息表--Service接口实现
 *
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Slf4j
@Service("workAreaService")
public class WorkAreaServiceImpl implements WorkAreaService {

	@Autowired
	@Qualifier("workAreaDao")
	private WorkAreaDao workAreaDao;
	@Autowired
	WorkAreaLabelDao workAreaLabelDao;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkArea insertData){
		Result<Boolean> result = Result.success();
		result.setData(workAreaDao.insert(insertData) == 1);
		return result;
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkArea updateData){
		Result<Boolean> result = Result.success();
		result.setData(workAreaDao.updateById(updateData) == 1);
		if (ObjectHelper.isNotNull(updateData.getAreaCode())){
			updateLabels(updateData);
		}
		return result;
	 }

	private void updateLabels(WorkArea updateData) {
		List<WorkAreaLabel> workAreaLabelList =workAreaLabelDao.listLabelByAreaCode(updateData.getAreaCode());
		if (!CollectionUtils.isEmpty(workAreaLabelList)){
			workAreaLabelDao.deleteByAreaCode(updateData);
		}
		if (!CollectionUtils.isEmpty(updateData.getLabels())){
			List<WorkAreaLabel> workAreaLabels =assembleworkAreaLabels(updateData);
			workAreaLabelDao.batchInsert(workAreaLabels);
		}
	}

	private List<WorkAreaLabel> assembleworkAreaLabels(WorkArea updateData) {
		List<WorkAreaLabel> workAreaLabelList =new ArrayList<>();
		Date now =new Date();
		for (Integer label:updateData.getLabels()){
			WorkAreaLabel workAreaLabel =new WorkAreaLabel();
			workAreaLabel.setAreaCode(updateData.getAreaCode());
			workAreaLabel.setLabelCode(label);
			workAreaLabel.setCreateUser(updateData.getUpdateUser());
			workAreaLabel.setCreateUserName(updateData.getUpdateUserName());
			workAreaLabel.setUpdateUser(updateData.getUpdateUser());
			workAreaLabel.setUpdateUserName(updateData.getUpdateUserName());
			workAreaLabel.setCreateTime(now);
			workAreaLabel.setUpdateTime(now);
			workAreaLabelList.add(workAreaLabel);
		}
		return workAreaLabelList;
	}

	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkArea deleteData){
		Result<Boolean> result = Result.success();
		result.setData(workAreaDao.deleteById(deleteData) == 1);
		return result;
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkArea> queryById(Long id){
		Result<WorkArea> result = Result.success();
		result.setData(fillWorkArea(workAreaDao.queryById(id)));
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkArea>> queryPageList(WorkAreaQuery query){
		Result<PageDto<WorkArea>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkArea> voDataList = new ArrayList<WorkArea>();
		PageDto<WorkArea> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workAreaDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
		    List<WorkArea> dataList = workAreaDao.queryList(query);
		    for (WorkArea tmp : dataList) {
		    	voDataList.add(this.fillWorkArea(tmp));
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
	public Result<Boolean> checkParamForQueryPageList(WorkAreaQuery query){
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
	public WorkArea fillWorkArea(WorkArea data){
		if(data == null) {
			return null;
		}
		//特殊字段设置
		List<WorkAreaLabel> workAreaLabelList =workAreaLabelDao.listLabelByAreaCode(data.getAreaCode());
		if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(workAreaLabelList)){
			List<Integer> labels = workAreaLabelList.stream().map(workAreaLabel -> workAreaLabel.getLabelCode()).collect(Collectors.toList());
			data.setLabels(labels);
		}
		return data;
	 }
	@Override
	public Result<Boolean> saveData(WorkArea workArea) {
		Result<Boolean> result = Result.success();
		WorkArea oldData = workAreaDao.queryByAreaCode(workArea.getAreaCode());
		if(oldData != null) {
			WorkArea updateData = new WorkArea();
			updateData.setId(oldData.getId());
			updateData.setAreaName(workArea.getAreaName());
			updateData.setBusinessLineCode(workArea.getBusinessLineCode());
			if(workArea.getBusinessLineName() == null) {
				updateData.setBusinessLineName("");
			}else {
				updateData.setBusinessLineName(workArea.getBusinessLineName());
			}
			if(workArea.getAreaType() == null) {
				updateData.setAreaType(0);
			}else {
				updateData.setAreaType(workArea.getAreaType());
			}
			updateData.setFlowDirectionType(workArea.getFlowDirectionType());
			updateData.setUpdateUser(workArea.getUpdateUser());
			updateData.setUpdateUserName(workArea.getUpdateUserName());
			updateData.setUpdateTime(workArea.getUpdateTime());
			result.setData(workAreaDao.updateById(updateData) == 1);
		}else {
			result.setData(workAreaDao.insert(workArea) == 1);
		}
		return result;
	}
	@Override
	public WorkArea queryByAreaCode(String areaCode) {
		if(StringUtils.isNotBlank(areaCode)) {
			return workAreaDao.queryByAreaCode(areaCode);
		}
		return null;
	}
	@Override
	public Map<String,WorkArea> queryByAreaCodeListToMap(List<String> areaCodeList) {
		Map<String,WorkArea> mapData = new HashMap<>();
		if(CollectionUtils.isEmpty(areaCodeList)) {
			return mapData;
		}
		List<WorkArea> dataList = workAreaDao.queryByAreaCodeList(areaCodeList);
		if(CollectionUtils.isEmpty(dataList)) {
			return mapData;
		}
		for(WorkArea item: dataList) {
			mapData.put(item.getAreaCode(), item);
		}
		return mapData;
	}
	@Override
	public WorkArea loadByAreaCodeInMap(String areaCode, Map<String, WorkArea> cacheMap) {
		if(cacheMap == null) {
			return queryByAreaCode(areaCode);
		}
		if(cacheMap.containsKey(areaCode)){
			if(cacheMap.get(areaCode).getId() != null) {
				return cacheMap.get(areaCode);
			}
			return null;
		}
		WorkArea workArea = queryByAreaCode(areaCode);
		if(workArea != null) {
			cacheMap.put(areaCode, workArea);
			return workArea;
		}else {
			workArea = new WorkArea();
			cacheMap.put(areaCode, workArea);
			return null;
		}
	}
	@Override
	public List<WorkArea> queryListByAreaCodeList(List<String> areaCodeList) {
		if(CollectionUtils.isEmpty(areaCodeList)) {
			return new ArrayList<>();
		}
		return workAreaDao.queryByAreaCodeList(areaCodeList);
	}

	@Override
	public List<WorkArea> listAreaByLabel(WorkArea workArea) {
		return workAreaDao.listAreaByLabel(workArea);
	}
}
