package com.jdl.basic.provider.core.provider.work;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.PageDto;

import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigArea;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigAreaQuery;
import com.jdl.basic.provider.core.service.work.WorkGridManagerTaskConfigAreaService;
import com.jdl.basic.api.service.work.WorkGridManagerTaskConfigAreaJsfService;

/**
 * 作业区巡检任务配置-作业区表--JsfService接口实现
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
@Slf4j
@Service("workGridManagerTaskConfigAreaJsfService")
public class WorkGridManagerTaskConfigAreaJsfServiceImpl implements WorkGridManagerTaskConfigAreaJsfService {

	@Autowired
	@Qualifier("workGridManagerTaskConfigAreaService")
	private WorkGridManagerTaskConfigAreaService workGridManagerTaskConfigAreaService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridManagerTaskConfigArea insertData){
		return workGridManagerTaskConfigAreaService.insert(insertData);
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridManagerTaskConfigArea updateData){
		return workGridManagerTaskConfigAreaService.updateById(updateData);
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridManagerTaskConfigArea deleteData){
		return workGridManagerTaskConfigAreaService.deleteById(deleteData);
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridManagerTaskConfigArea> queryById(Long id){
		return workGridManagerTaskConfigAreaService.queryById(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridManagerTaskConfigArea>> queryPageList(WorkGridManagerTaskConfigAreaQuery query){
		return workGridManagerTaskConfigAreaService.queryPageList(query);
	 }

}
