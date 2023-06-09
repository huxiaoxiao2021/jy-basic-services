package com.jdl.basic.provider.core.service.work.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jdl.basic.api.domain.work.WorkGridManagerCaseItem;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseItemQuery;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.work.WorkGridManagerCaseItemDao;
import com.jdl.basic.provider.core.service.work.WorkGridManagerCaseItemService;

import lombok.extern.slf4j.Slf4j;

/**
 * 作业区巡检-项目明细表--Service接口实现
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
@Slf4j
@Service("workGridManagerCaseItemService")
public class WorkGridManagerCaseItemServiceImpl implements WorkGridManagerCaseItemService {

	@Autowired
	@Qualifier("workGridManagerCaseItemDao")
	private WorkGridManagerCaseItemDao workGridManagerCaseItemDao;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridManagerCaseItem insertData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerCaseItemDao.insert(insertData) == 1);
		return result;
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridManagerCaseItem updateData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerCaseItemDao.updateById(updateData) == 1);
		return result;
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridManagerCaseItem deleteData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerCaseItemDao.deleteById(deleteData) == 1);
		return result;
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridManagerCaseItem> queryById(Long id){
		Result<WorkGridManagerCaseItem> result = Result.success();
		result.setData(fillWorkGridManagerCaseItem(workGridManagerCaseItemDao.queryById(id)));
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridManagerCaseItem>> queryPageList(WorkGridManagerCaseItemQuery query){
		Result<PageDto<WorkGridManagerCaseItem>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkGridManagerCaseItem> voDataList = new ArrayList<WorkGridManagerCaseItem>();
		PageDto<WorkGridManagerCaseItem> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workGridManagerCaseItemDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
		    List<WorkGridManagerCaseItem> dataList = workGridManagerCaseItemDao.queryList(query);
		    for (WorkGridManagerCaseItem tmp : dataList) {
		    	voDataList.add(this.fillWorkGridManagerCaseItem(tmp));
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
	public Result<Boolean> checkParamForQueryPageList(WorkGridManagerCaseItemQuery query){
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
	public WorkGridManagerCaseItem fillWorkGridManagerCaseItem(WorkGridManagerCaseItem data){
		if(data == null) {
			return null;
		}
		//特殊字段设置
		return data;
	 }
	@Override
	public List<WorkGridManagerCaseItem> queryCaseItemListByCaseCodeList(List<String> caseCodeList) {
		if(CollectionUtils.isEmpty(caseCodeList)) {
			return new ArrayList<WorkGridManagerCaseItem>();
		}
		return workGridManagerCaseItemDao.queryCaseItemListByCaseCodeList(caseCodeList);
	}

}
