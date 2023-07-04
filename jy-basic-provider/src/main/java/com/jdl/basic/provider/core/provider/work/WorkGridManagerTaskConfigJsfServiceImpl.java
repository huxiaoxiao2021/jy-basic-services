package com.jdl.basic.provider.core.provider.work;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfig;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigQuery;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigVo;
import com.jdl.basic.api.service.work.WorkGridManagerTaskConfigJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.work.WorkGridManagerTaskConfigService;

import lombok.extern.slf4j.Slf4j;

/**
 * 作业区巡检任务配置表--JsfService接口实现
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
@Slf4j
@Service("workGridManagerTaskConfigJsfService")
public class WorkGridManagerTaskConfigJsfServiceImpl implements WorkGridManagerTaskConfigJsfService {

	@Autowired
	@Qualifier("workGridManagerTaskConfigService")
	private WorkGridManagerTaskConfigService workGridManagerTaskConfigService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridManagerTaskConfigVo insertData){
		return workGridManagerTaskConfigService.insert(insertData);
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridManagerTaskConfigVo updateData){
		return workGridManagerTaskConfigService.updateById(updateData);
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridManagerTaskConfig deleteData){
		return workGridManagerTaskConfigService.deleteById(deleteData);
	 }
	@Override
	public Result<WorkGridManagerTaskConfigVo> queryByTaskConfigCode(String taskConfigCode) {
		Result<WorkGridManagerTaskConfigVo> result = Result.success();
		result.setData(workGridManagerTaskConfigService.queryByTaskConfigCodeWithCache(taskConfigCode));
		return result;
	}	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridManagerTaskConfigVo> queryById(Long id){
		return workGridManagerTaskConfigService.queryById(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridManagerTaskConfigVo>> queryPageList(WorkGridManagerTaskConfigQuery query){
		return workGridManagerTaskConfigService.queryPageList(query);
	 }
}
