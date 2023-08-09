package com.jdl.basic.provider.core.dao.work;

import java.util.List;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskCase;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskCaseQuery;

/**
 * 作业区巡检场景表--Dao接口
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridManagerTaskCaseDao {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	int insert(WorkGridManagerTaskCase insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	int updateById(WorkGridManagerTaskCase updateData);
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	int deleteById(WorkGridManagerTaskCase deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WorkGridManagerTaskCase queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	List<WorkGridManagerTaskCase> queryList(WorkGridManagerTaskCaseQuery query);
	/**
	 * 按条件查询数量
	 * @param query
	 * @return
	 */
	long queryCount(WorkGridManagerTaskCaseQuery query);
	/**
	 * 查询caseCode列表
	 * @param taskCode
	 * @return
	 */
	List<String> queryCaseCodeListByTaskCode(String taskCode);

}
