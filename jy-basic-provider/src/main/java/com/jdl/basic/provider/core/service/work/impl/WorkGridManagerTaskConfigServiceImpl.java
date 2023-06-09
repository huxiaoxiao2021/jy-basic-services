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

import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfig;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigQuery;
import com.jdl.basic.provider.core.dao.work.WorkGridManagerTaskConfigDao;
import com.jdl.basic.provider.core.service.work.WorkGridManagerTaskConfigService;

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

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridManagerTaskConfig insertData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerTaskConfigDao.insert(insertData) == 1);
		return result;
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridManagerTaskConfig updateData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerTaskConfigDao.updateById(updateData) == 1);
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
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridManagerTaskConfig> queryById(Long id){
		Result<WorkGridManagerTaskConfig> result = Result.success();
		result.setData(fillWorkGridManagerTaskConfig(workGridManagerTaskConfigDao.queryById(id)));
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridManagerTaskConfig>> queryPageList(WorkGridManagerTaskConfigQuery query){
		Result<PageDto<WorkGridManagerTaskConfig>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkGridManagerTaskConfig> voDataList = new ArrayList<WorkGridManagerTaskConfig>();
		PageDto<WorkGridManagerTaskConfig> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
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
	public WorkGridManagerTaskConfig fillWorkGridManagerTaskConfig(WorkGridManagerTaskConfig data){
		if(data == null) {
			return null;
		}
		//特殊字段设置
		return data;
	 }

}
