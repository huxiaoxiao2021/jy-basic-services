package com.jdl.basic.provider.core.service.work;

import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigArea;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigAreaQuery;

/**
 * 作业区巡检任务配置-作业区表--Service接口
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridManagerTaskConfigAreaService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkGridManagerTaskConfigArea insertData);
	/**
	 * 批量添加
	 * @param configAreaList
	 * @return
	 */
	int batchInsert(List<WorkGridManagerTaskConfigArea> configAreaList);	
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkGridManagerTaskConfigArea updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkGridManagerTaskConfigArea deleteData);
	/**
	 * 根据taskConfigCode删除数据
	 * @param deleteData
	 * @return
	 */
	int deleteByTaskConfigCode(WorkGridManagerTaskConfigArea deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGridManagerTaskConfigArea> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkGridManagerTaskConfigArea>> queryPageList(WorkGridManagerTaskConfigAreaQuery query);
	/**
	 * 查询配置对应的areaCode列表
	 * @param taskConfigCode
	 * @return
	 */
	List<String> queryAreaCodeListByTaskConfigCode(String taskConfigCode);
}
