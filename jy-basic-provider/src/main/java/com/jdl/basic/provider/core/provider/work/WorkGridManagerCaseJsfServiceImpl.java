package com.jdl.basic.provider.core.provider.work;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jdl.basic.api.domain.work.WorkGridManagerCase;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseQuery;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseWithItem;
import com.jdl.basic.api.service.work.WorkGridManagerCaseJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.work.WorkGridManagerCaseService;

import lombok.extern.slf4j.Slf4j;

/**
 * 作业区巡检场景表--JsfService接口实现
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
@Slf4j
@Service("workGridManagerCaseJsfService")
public class WorkGridManagerCaseJsfServiceImpl implements WorkGridManagerCaseJsfService {

	@Autowired
	@Qualifier("workGridManagerCaseService")
	private WorkGridManagerCaseService workGridManagerCaseService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridManagerCase insertData){
		return workGridManagerCaseService.insert(insertData);
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridManagerCase updateData){
		return workGridManagerCaseService.updateById(updateData);
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridManagerCase deleteData){
		return workGridManagerCaseService.deleteById(deleteData);
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridManagerCase> queryById(Long id){
		return workGridManagerCaseService.queryById(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridManagerCase>> queryPageList(WorkGridManagerCaseQuery query){
		return workGridManagerCaseService.queryPageList(query);
	 }
	@Override
	public List<WorkGridManagerCaseWithItem> queryCaseWithItemListByTaskCode(String taskCode) {
		return workGridManagerCaseService.queryCaseWithItemListByTaskCode(taskCode);
	}

}
