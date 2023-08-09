package com.jdl.basic.provider.core.provider.work;


import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.PageDto;

import com.jdl.basic.api.domain.work.WorkGridManagerTaskCase;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskCaseQuery;
import com.jdl.basic.provider.core.service.work.WorkGridManagerTaskCaseService;
import com.jdl.basic.api.service.work.WorkGridManagerTaskCaseJsfService;

/**
 * 作业区巡检场景表--JsfService接口实现
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
@Slf4j
@Service("workGridManagerTaskCaseJsfService")
public class WorkGridManagerTaskCaseJsfServiceImpl implements WorkGridManagerTaskCaseJsfService {

	@Autowired
	@Qualifier("workGridManagerTaskCaseService")
	private WorkGridManagerTaskCaseService workGridManagerTaskCaseService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridManagerTaskCase insertData){
		return workGridManagerTaskCaseService.insert(insertData);
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridManagerTaskCase updateData){
		return workGridManagerTaskCaseService.updateById(updateData);
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridManagerTaskCase deleteData){
		return workGridManagerTaskCaseService.deleteById(deleteData);
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridManagerTaskCase> queryById(Long id){
		return workGridManagerTaskCaseService.queryById(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridManagerTaskCase>> queryPageList(WorkGridManagerTaskCaseQuery query){
		return workGridManagerTaskCaseService.queryPageList(query);
	 }
}
