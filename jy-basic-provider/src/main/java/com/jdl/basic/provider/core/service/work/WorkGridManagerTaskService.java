package com.jdl.basic.provider.core.service.work;

import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.api.domain.work.WorkGridManagerTask;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskQuery;

/**
 * 作业区巡检任务表--Service接口
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridManagerTaskService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkGridManagerTask insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkGridManagerTask updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkGridManagerTask deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGridManagerTask> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkGridManagerTask>> queryPageList(WorkGridManagerTaskQuery query);
	/**
	 * 查询单个任务定义
	 * @param taskCode
	 * @return
	 */
	Result<WorkGridManagerTask> queryByTaskCode(String taskCode);

}
