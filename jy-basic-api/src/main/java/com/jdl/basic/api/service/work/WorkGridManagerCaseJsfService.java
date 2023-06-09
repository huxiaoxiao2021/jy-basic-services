package com.jdl.basic.api.service.work;

import java.util.List;

import com.jdl.basic.api.domain.work.WorkGridManagerCase;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseQuery;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseWithItem;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

/**
 * 作业区巡检场景表--JsfService接口
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridManagerCaseJsfService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkGridManagerCase insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkGridManagerCase updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkGridManagerCase deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGridManagerCase> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkGridManagerCase>> queryPageList(WorkGridManagerCaseQuery query);
	/**
	 * 查询任务下所有case列表
	 * @param taskCode
	 * @return
	 */
	List<WorkGridManagerCaseWithItem> queryCaseWithItemListByTaskCode(String taskCode);

}
