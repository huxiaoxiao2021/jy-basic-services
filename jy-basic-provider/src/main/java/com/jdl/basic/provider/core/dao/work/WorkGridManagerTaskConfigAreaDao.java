package com.jdl.basic.provider.core.dao.work;

import java.util.List;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigArea;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigAreaQuery;

/**
 * 作业区巡检任务配置-作业区表--Dao接口
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridManagerTaskConfigAreaDao {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	int insert(WorkGridManagerTaskConfigArea insertData);
	/**
	 * 批量插入
	 * @param configAreaList
	 * @return
	 */
	int batchInsert(List<WorkGridManagerTaskConfigArea> configAreaList);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	int updateById(WorkGridManagerTaskConfigArea updateData);
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	int deleteById(WorkGridManagerTaskConfigArea deleteData);
	/**
	 * 删除配置下所有配置
	 * @param deleteData
	 * @return
	 */
	int deleteByTaskConfigCode(WorkGridManagerTaskConfigArea deleteData);	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WorkGridManagerTaskConfigArea queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	List<WorkGridManagerTaskConfigArea> queryList(WorkGridManagerTaskConfigAreaQuery query);
	/**
	 * 按条件查询数量
	 * @param query
	 * @return
	 */
	long queryCount(WorkGridManagerTaskConfigAreaQuery query);
	/**
	 * 查询配置的列表
	 * @param taskConfigCode
	 * @return
	 */
	List<String> queryAreaCodeListByTaskConfigCode(String taskConfigCode);

}
