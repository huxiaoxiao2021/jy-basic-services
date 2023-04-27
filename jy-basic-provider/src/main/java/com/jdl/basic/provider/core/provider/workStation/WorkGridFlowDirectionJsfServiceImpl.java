package com.jdl.basic.provider.core.provider.workStation;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.PageDto;

import com.jdl.basic.api.domain.workStation.WorkGridFlowDirection;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionQuery;
import com.jdl.basic.provider.core.service.workStation.WorkGridFlowDirectionService;
import com.jdl.basic.api.service.workStation.WorkGridFlowDirectionJsfService;

/**
 * 场地网格流向表--JsfService接口实现
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Slf4j
@Service("workGridFlowDirectionJsfService")
public class WorkGridFlowDirectionJsfServiceImpl implements WorkGridFlowDirectionJsfService {

	@Autowired
	@Qualifier("workGridFlowDirectionService")
	private WorkGridFlowDirectionService workGridFlowDirectionService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridFlowDirection insertData){
		return workGridFlowDirectionService.insert(insertData);
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridFlowDirection updateData){
		return workGridFlowDirectionService.updateById(updateData);
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridFlowDirection deleteData){
		return workGridFlowDirectionService.deleteById(deleteData);
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridFlowDirection> queryById(Long id){
		return workGridFlowDirectionService.queryById(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridFlowDirection>> queryPageList(WorkGridFlowDirectionQuery query){
		return workGridFlowDirectionService.queryPageList(query);
	 }

}
