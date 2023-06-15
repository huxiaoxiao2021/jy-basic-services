package com.jdl.basic.provider.core.dao.work;

import java.util.List;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfig;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigQuery;

/**
 * 作业区巡检任务配置表--Dao接口
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridManagerTaskConfigDao {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	int insert(WorkGridManagerTaskConfig insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	int updateById(WorkGridManagerTaskConfig updateData);
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	int deleteById(WorkGridManagerTaskConfig deleteData);
	/**
	 * 根据taskConfigCode查询
	 * @param taskConfigCode
	 * @return
	 */
	WorkGridManagerTaskConfig queryByTaskConfigCode(String taskConfigCode);	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WorkGridManagerTaskConfig queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	List<WorkGridManagerTaskConfig> queryList(WorkGridManagerTaskConfigQuery query);
	/**
	 * 按条件查询数量
	 * @param query
	 * @return
	 */
	long queryCount(WorkGridManagerTaskConfigQuery query);

}
