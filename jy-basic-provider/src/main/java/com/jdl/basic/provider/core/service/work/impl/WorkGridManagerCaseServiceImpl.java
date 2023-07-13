package com.jdl.basic.provider.core.service.work.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jdl.basic.api.domain.work.WorkGridManagerCase;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseItem;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseQuery;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseWithItem;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.work.WorkGridManagerCaseDao;
import com.jdl.basic.provider.core.service.work.WorkGridManagerCaseItemService;
import com.jdl.basic.provider.core.service.work.WorkGridManagerCaseService;
import com.jdl.basic.provider.core.service.work.WorkGridManagerTaskCaseService;

import lombok.extern.slf4j.Slf4j;

/**
 * 作业区巡检场景表--Service接口实现
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
@Slf4j
@Service("workGridManagerCaseService")
public class WorkGridManagerCaseServiceImpl implements WorkGridManagerCaseService {

	@Autowired
	@Qualifier("workGridManagerCaseDao")
	private WorkGridManagerCaseDao workGridManagerCaseDao;
	
	@Autowired
	@Qualifier("workGridManagerTaskCaseService")
	private WorkGridManagerTaskCaseService workGridManagerTaskCaseService;
	
	@Autowired
	@Qualifier("workGridManagerCaseItemService")
	private WorkGridManagerCaseItemService workGridManagerCaseItemService;
	
	

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridManagerCase insertData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerCaseDao.insert(insertData) == 1);
		return result;
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridManagerCase updateData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerCaseDao.updateById(updateData) == 1);
		return result;
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridManagerCase deleteData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerCaseDao.deleteById(deleteData) == 1);
		return result;
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridManagerCase> queryById(Long id){
		Result<WorkGridManagerCase> result = Result.success();
		result.setData(fillWorkGridManagerCase(workGridManagerCaseDao.queryById(id)));
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridManagerCase>> queryPageList(WorkGridManagerCaseQuery query){
		Result<PageDto<WorkGridManagerCase>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkGridManagerCase> voDataList = new ArrayList<WorkGridManagerCase>();
		PageDto<WorkGridManagerCase> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workGridManagerCaseDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
		    List<WorkGridManagerCase> dataList = workGridManagerCaseDao.queryList(query);
		    for (WorkGridManagerCase tmp : dataList) {
		    	voDataList.add(this.fillWorkGridManagerCase(tmp));
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
	public Result<Boolean> checkParamForQueryPageList(WorkGridManagerCaseQuery query){
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
	public WorkGridManagerCase fillWorkGridManagerCase(WorkGridManagerCase data){
		if(data == null) {
			return null;
		}
		//特殊字段设置
		return data;
	 }
	@Cache(key = "workGridManagerCaseService.queryCaseWithItemListByTaskCode@args0", memoryEnable = true, memoryExpiredTime = 30 * 60 * 1000
	,redisEnable = true, redisExpiredTime = 60 * 60 * 1000)	
	@Override
	public List<WorkGridManagerCaseWithItem> queryCaseWithItemListByTaskCode(String taskCode) {
		List<String> caseCodeList = workGridManagerTaskCaseService.queryCaseCodeListByTaskCode(taskCode);
		List<WorkGridManagerCaseWithItem> caseWithItemList = new ArrayList<>();
		if(CollectionUtils.isEmpty(caseCodeList)) {
			return caseWithItemList;
		}
		List<WorkGridManagerCase> caseList = workGridManagerCaseDao.queryCaseListByCaseCodeList(caseCodeList);
		if(CollectionUtils.isEmpty(caseList)) {
			return caseWithItemList;
		}
		Map<String,WorkGridManagerCaseWithItem> caseWithItemMap = new HashMap<>();
		for(WorkGridManagerCase data: caseList) {
			WorkGridManagerCaseWithItem caseWithItem = new WorkGridManagerCaseWithItem();
			BeanUtils.copyProperties(data, caseWithItem);
			caseWithItem.setCaseItemList(new ArrayList<WorkGridManagerCaseItem>());
			caseWithItemList.add(caseWithItem);
			caseWithItemMap.put(data.getCaseCode(), caseWithItem);
		}
		//查询item列表
		List<WorkGridManagerCaseItem> caseItemList = workGridManagerCaseItemService.queryCaseItemListByCaseCodeList(caseCodeList);
		if(CollectionUtils.isEmpty(caseItemList)) {
			return caseWithItemList;
		}
		//按caseCode添加item列表
		for(WorkGridManagerCaseItem itemdata: caseItemList) {
			if(caseWithItemMap.containsKey(itemdata.getCaseCode())) {
				caseWithItemMap.get(itemdata.getCaseCode()).getCaseItemList().add(itemdata);
			}
		}
		//排序
		for(WorkGridManagerCaseWithItem data: caseWithItemList) {
			Collections.sort(data.getCaseItemList(), new Comparator<WorkGridManagerCaseItem>(){
				@Override
				public int compare(WorkGridManagerCaseItem o1, WorkGridManagerCaseItem o2) {
					if(o1 != null 
							&& o2!= null
							&& o1.getOrderNum() != null
							&& o2.getOrderNum() != null) {
						return o1.getOrderNum().compareTo(o2.getOrderNum());
					}
					return 0;
				}
				
			});
		}
		return caseWithItemList;
	}

}
