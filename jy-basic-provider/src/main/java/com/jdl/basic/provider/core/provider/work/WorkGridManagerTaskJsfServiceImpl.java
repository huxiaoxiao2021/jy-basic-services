package com.jdl.basic.provider.core.provider.work;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.PageDto;

import com.jdl.basic.api.domain.work.WorkGridManagerTask;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskQuery;
import com.jdl.basic.provider.core.service.work.WorkGridManagerTaskService;
import com.jdl.basic.api.service.work.WorkGridManagerTaskJsfService;

/**
 * 作业区巡检任务表--JsfService接口实现
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
@Slf4j
@Service("workGridManagerTaskJsfService")
public class WorkGridManagerTaskJsfServiceImpl implements WorkGridManagerTaskJsfService {

	@Autowired
	@Qualifier("workGridManagerTaskService")
	private WorkGridManagerTaskService workGridManagerTaskService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridManagerTask insertData){
		return workGridManagerTaskService.insert(insertData);
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridManagerTask updateData){
		return workGridManagerTaskService.updateById(updateData);
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridManagerTask deleteData){
		return workGridManagerTaskService.deleteById(deleteData);
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridManagerTask> queryById(Long id){
		return workGridManagerTaskService.queryById(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridManagerTask>> queryPageList(WorkGridManagerTaskQuery query){
		return workGridManagerTaskService.queryPageList(query);
	 }
	@Override
	public Result<WorkGridManagerTask> queryByTaskCode(String taskCode) {
		return workGridManagerTaskService.queryByTaskCode(taskCode);
	}

}
