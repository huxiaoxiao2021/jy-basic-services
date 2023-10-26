package com.jdl.basic.provider.core.dao.work;

import java.util.List;
import com.jdl.basic.api.domain.work.WorkGridManagerTask;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskQuery;
import com.jdl.basic.common.utils.Result;

/**
 * 作业区巡检任务表--Dao接口
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridManagerTaskDao {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	int insert(WorkGridManagerTask insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	int updateById(WorkGridManagerTask updateData);
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	int deleteById(WorkGridManagerTask deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WorkGridManagerTask queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	List<WorkGridManagerTask> queryList(WorkGridManagerTaskQuery query);
	/**
	 * 按条件查询数量
	 * @param query
	 * @return
	 */
	long queryCount(WorkGridManagerTaskQuery query);
	
	WorkGridManagerTask queryByTaskCode(String taskCode);
	/**
	 * 查询任务字典
	 * @return
	 */
	List<WorkGridManagerTask> queryWorkGridManagerTaskDictList();
    List<WorkGridManagerTask> queryByBizType(Integer taskBizType);
}
