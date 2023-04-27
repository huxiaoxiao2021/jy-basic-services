package com.jdl.basic.provider.core.service.workStation.impl;

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

import com.jdl.basic.api.domain.workStation.WorkGridFlowDirection;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionQuery;
import com.jdl.basic.provider.core.dao.workStation.WorkGridFlowDirectionDao;
import com.jdl.basic.provider.core.service.workStation.WorkGridFlowDirectionService;

/**
 * 场地网格流向表--Service接口实现
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Slf4j
@Service("workGridFlowDirectionService")
public class WorkGridFlowDirectionServiceImpl implements WorkGridFlowDirectionService {

	@Autowired
	@Qualifier("workGridFlowDirectionDao")
	private WorkGridFlowDirectionDao workGridFlowDirectionDao;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridFlowDirection insertData){
		Result<Boolean> result = Result.success();
		result.setData(workGridFlowDirectionDao.insert(insertData) == 1);
		return result;
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridFlowDirection updateData){
		Result<Boolean> result = Result.success();
		result.setData(workGridFlowDirectionDao.updateById(updateData) == 1);
		return result;
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridFlowDirection deleteData){
		Result<Boolean> result = Result.success();
		result.setData(workGridFlowDirectionDao.deleteById(deleteData) == 1);
		return result;
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridFlowDirection> queryById(Long id){
		Result<WorkGridFlowDirection> result = Result.success();
		result.setData(fillWorkGridFlowDirection(workGridFlowDirectionDao.queryById(id)));
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridFlowDirection>> queryPageList(WorkGridFlowDirectionQuery query){
		Result<PageDto<WorkGridFlowDirection>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkGridFlowDirection> voDataList = new ArrayList<WorkGridFlowDirection>();
		PageDto<WorkGridFlowDirection> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workGridFlowDirectionDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
		    List<WorkGridFlowDirection> dataList = workGridFlowDirectionDao.queryList(query);
		    for (WorkGridFlowDirection tmp : dataList) {
		    	voDataList.add(this.fillWorkGridFlowDirection(tmp));
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
	public Result<Boolean> checkParamForQueryPageList(WorkGridFlowDirectionQuery query){
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
	public WorkGridFlowDirection fillWorkGridFlowDirection(WorkGridFlowDirection data){
		if(data == null) {
			return null;
		}
		//特殊字段设置
		return data;
	 }
	@Override
	public List<WorkGridFlowDirection> queryListForWorkGridVo(WorkGridFlowDirectionQuery query) {
		return workGridFlowDirectionDao.queryListForWorkGridVo(query);
	}
}
