package com.jdl.basic.api.service.work;

import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseItem;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseItemQuery;

/**
 * 作业区巡检-项目明细表--JsfService接口
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridManagerCaseItemJsfService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkGridManagerCaseItem insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkGridManagerCaseItem updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkGridManagerCaseItem deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGridManagerCaseItem> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkGridManagerCaseItem>> queryPageList(WorkGridManagerCaseItemQuery query);

}
