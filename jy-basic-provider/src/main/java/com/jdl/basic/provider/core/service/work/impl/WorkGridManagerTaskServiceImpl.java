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

import com.jdl.basic.api.domain.work.WorkGridManagerTask;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskQuery;
import com.jdl.basic.provider.core.dao.work.WorkGridManagerTaskDao;
import com.jdl.basic.provider.core.service.work.WorkGridManagerTaskService;

/**
 * 作业区巡检任务表--Service接口实现
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
@Slf4j
@Service("workGridManagerTaskService")
public class WorkGridManagerTaskServiceImpl implements WorkGridManagerTaskService {

	@Autowired
	@Qualifier("workGridManagerTaskDao")
	private WorkGridManagerTaskDao workGridManagerTaskDao;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridManagerTask insertData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerTaskDao.insert(insertData) == 1);
		return result;
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridManagerTask updateData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerTaskDao.updateById(updateData) == 1);
		return result;
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridManagerTask deleteData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerTaskDao.deleteById(deleteData) == 1);
		return result;
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridManagerTask> queryById(Long id){
		Result<WorkGridManagerTask> result = Result.success();
		result.setData(fillWorkGridManagerTask(workGridManagerTaskDao.queryById(id)));
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridManagerTask>> queryPageList(WorkGridManagerTaskQuery query){
		Result<PageDto<WorkGridManagerTask>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkGridManagerTask> voDataList = new ArrayList<WorkGridManagerTask>();
		PageDto<WorkGridManagerTask> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workGridManagerTaskDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
		    List<WorkGridManagerTask> dataList = workGridManagerTaskDao.queryList(query);
		    for (WorkGridManagerTask tmp : dataList) {
		    	voDataList.add(this.fillWorkGridManagerTask(tmp));
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
	public Result<Boolean> checkParamForQueryPageList(WorkGridManagerTaskQuery query){
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
	public WorkGridManagerTask fillWorkGridManagerTask(WorkGridManagerTask data){
		if(data == null) {
			return null;
		}
		//特殊字段设置
		return data;
	 }
	@Override
	public Result<WorkGridManagerTask> queryByTaskCode(String taskCode) {
		Result<WorkGridManagerTask> result = Result.success();
		result.setData(workGridManagerTaskDao.queryByTaskCode(taskCode));
		return result;
	}

}
