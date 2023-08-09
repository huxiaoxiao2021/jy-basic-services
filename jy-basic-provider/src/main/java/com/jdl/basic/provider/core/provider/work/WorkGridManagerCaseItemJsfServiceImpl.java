package com.jdl.basic.provider.core.provider.work;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.PageDto;

import com.jdl.basic.api.domain.work.WorkGridManagerCaseItem;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseItemQuery;
import com.jdl.basic.provider.core.service.work.WorkGridManagerCaseItemService;
import com.jdl.basic.api.service.work.WorkGridManagerCaseItemJsfService;

/**
 * 作业区巡检-项目明细表--JsfService接口实现
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
@Slf4j
@Service("workGridManagerCaseItemJsfService")
public class WorkGridManagerCaseItemJsfServiceImpl implements WorkGridManagerCaseItemJsfService {

	@Autowired
	@Qualifier("workGridManagerCaseItemService")
	private WorkGridManagerCaseItemService workGridManagerCaseItemService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridManagerCaseItem insertData){
		return workGridManagerCaseItemService.insert(insertData);
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridManagerCaseItem updateData){
		return workGridManagerCaseItemService.updateById(updateData);
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridManagerCaseItem deleteData){
		return workGridManagerCaseItemService.deleteById(deleteData);
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridManagerCaseItem> queryById(Long id){
		return workGridManagerCaseItemService.queryById(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridManagerCaseItem>> queryPageList(WorkGridManagerCaseItemQuery query){
		return workGridManagerCaseItemService.queryPageList(query);
	 }

}
