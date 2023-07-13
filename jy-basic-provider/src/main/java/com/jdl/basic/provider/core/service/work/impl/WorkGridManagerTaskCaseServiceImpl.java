package com.jdl.basic.provider.core.service.work.impl;

import java.util.List;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.contants.DmsConstants;

import com.jdl.basic.api.domain.work.WorkGridManagerTaskCase;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskCaseQuery;
import com.jdl.basic.provider.core.dao.work.WorkGridManagerTaskCaseDao;
import com.jdl.basic.provider.core.service.work.WorkGridManagerTaskCaseService;

/**
 * 作业区巡检场景表--Service接口实现
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
@Slf4j
@Service("workGridManagerTaskCaseService")
public class WorkGridManagerTaskCaseServiceImpl implements WorkGridManagerTaskCaseService {

	@Autowired
	@Qualifier("workGridManagerTaskCaseDao")
	private WorkGridManagerTaskCaseDao workGridManagerTaskCaseDao;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridManagerTaskCase insertData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerTaskCaseDao.insert(insertData) == 1);
		return result;
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridManagerTaskCase updateData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerTaskCaseDao.updateById(updateData) == 1);
		return result;
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridManagerTaskCase deleteData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerTaskCaseDao.deleteById(deleteData) == 1);
		return result;
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridManagerTaskCase> queryById(Long id){
		Result<WorkGridManagerTaskCase> result = Result.success();
		result.setData(fillWorkGridManagerTaskCase(workGridManagerTaskCaseDao.queryById(id)));
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridManagerTaskCase>> queryPageList(WorkGridManagerTaskCaseQuery query){
		Result<PageDto<WorkGridManagerTaskCase>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkGridManagerTaskCase> voDataList = new ArrayList<WorkGridManagerTaskCase>();
		PageDto<WorkGridManagerTaskCase> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workGridManagerTaskCaseDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
		    List<WorkGridManagerTaskCase> dataList = workGridManagerTaskCaseDao.queryList(query);
		    for (WorkGridManagerTaskCase tmp : dataList) {
		    	voDataList.add(this.fillWorkGridManagerTaskCase(tmp));
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
	public Result<Boolean> checkParamForQueryPageList(WorkGridManagerTaskCaseQuery query){
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
	public WorkGridManagerTaskCase fillWorkGridManagerTaskCase(WorkGridManagerTaskCase data){
		if(data == null) {
			return null;
		}
		//特殊字段设置
		return data;
	 }
	@Override
	public List<String> queryCaseCodeListByTaskCode(String taskCode) {
		return workGridManagerTaskCaseDao.queryCaseCodeListByTaskCode(taskCode);
	}

}
