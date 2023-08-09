package com.jdl.basic.provider.core.service.work;

import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

import com.jdl.basic.api.domain.work.WorkGridManagerTaskCase;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskCaseQuery;

/**
 * 作业区巡检场景表--Service接口
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridManagerTaskCaseService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkGridManagerTaskCase insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkGridManagerTaskCase updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkGridManagerTaskCase deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGridManagerTaskCase> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkGridManagerTaskCase>> queryPageList(WorkGridManagerTaskCaseQuery query);
	/**
	 * 查询任务case列表
	 * @param taskCode
	 * @return
	 */
	List<String> queryCaseCodeListByTaskCode(String taskCode);

}
