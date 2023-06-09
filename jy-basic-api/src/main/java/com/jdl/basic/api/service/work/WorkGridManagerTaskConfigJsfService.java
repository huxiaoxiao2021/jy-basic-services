package com.jdl.basic.api.service.work;

import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfig;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigQuery;

/**
 * 作业区巡检任务配置表--JsfService接口
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridManagerTaskConfigJsfService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkGridManagerTaskConfig insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkGridManagerTaskConfig updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkGridManagerTaskConfig deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGridManagerTaskConfig> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkGridManagerTaskConfig>> queryPageList(WorkGridManagerTaskConfigQuery query);

}
