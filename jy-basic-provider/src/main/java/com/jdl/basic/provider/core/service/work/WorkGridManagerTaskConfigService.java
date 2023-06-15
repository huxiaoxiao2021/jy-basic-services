package com.jdl.basic.provider.core.service.work;

import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfig;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigQuery;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigVo;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

/**
 * 作业区巡检任务配置表--Service接口
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridManagerTaskConfigService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkGridManagerTaskConfigVo insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkGridManagerTaskConfigVo updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkGridManagerTaskConfig deleteData);
	/**
	 * 根据taskConfigCode查询
	 * @param taskConfigCode
	 * @return
	 */
	Result<WorkGridManagerTaskConfigVo> queryByTaskConfigCode(String taskConfigCode);	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGridManagerTaskConfigVo> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkGridManagerTaskConfigVo>> queryPageList(WorkGridManagerTaskConfigQuery query);
}
