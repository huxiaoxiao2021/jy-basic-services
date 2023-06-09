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

import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigArea;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigAreaQuery;
import com.jdl.basic.provider.core.dao.work.WorkGridManagerTaskConfigAreaDao;
import com.jdl.basic.provider.core.service.work.WorkGridManagerTaskConfigAreaService;

/**
 * 作业区巡检任务配置-作业区表--Service接口实现
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
@Slf4j
@Service("workGridManagerTaskConfigAreaService")
public class WorkGridManagerTaskConfigAreaServiceImpl implements WorkGridManagerTaskConfigAreaService {

	@Autowired
	@Qualifier("workGridManagerTaskConfigAreaDao")
	private WorkGridManagerTaskConfigAreaDao workGridManagerTaskConfigAreaDao;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridManagerTaskConfigArea insertData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerTaskConfigAreaDao.insert(insertData) == 1);
		return result;
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridManagerTaskConfigArea updateData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerTaskConfigAreaDao.updateById(updateData) == 1);
		return result;
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridManagerTaskConfigArea deleteData){
		Result<Boolean> result = Result.success();
		result.setData(workGridManagerTaskConfigAreaDao.deleteById(deleteData) == 1);
		return result;
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridManagerTaskConfigArea> queryById(Long id){
		Result<WorkGridManagerTaskConfigArea> result = Result.success();
		result.setData(fillWorkGridManagerTaskConfigArea(workGridManagerTaskConfigAreaDao.queryById(id)));
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridManagerTaskConfigArea>> queryPageList(WorkGridManagerTaskConfigAreaQuery query){
		Result<PageDto<WorkGridManagerTaskConfigArea>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkGridManagerTaskConfigArea> voDataList = new ArrayList<WorkGridManagerTaskConfigArea>();
		PageDto<WorkGridManagerTaskConfigArea> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workGridManagerTaskConfigAreaDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
		    List<WorkGridManagerTaskConfigArea> dataList = workGridManagerTaskConfigAreaDao.queryList(query);
		    for (WorkGridManagerTaskConfigArea tmp : dataList) {
		    	voDataList.add(this.fillWorkGridManagerTaskConfigArea(tmp));
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
	public Result<Boolean> checkParamForQueryPageList(WorkGridManagerTaskConfigAreaQuery query){
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
	public WorkGridManagerTaskConfigArea fillWorkGridManagerTaskConfigArea(WorkGridManagerTaskConfigArea data){
		if(data == null) {
			return null;
		}
		//特殊字段设置
		return data;
	 }

}
