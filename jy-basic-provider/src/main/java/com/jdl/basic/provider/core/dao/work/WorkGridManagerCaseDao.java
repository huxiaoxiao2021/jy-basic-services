package com.jdl.basic.provider.core.dao.work;

import java.util.List;
import com.jdl.basic.api.domain.work.WorkGridManagerCase;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseQuery;

/**
 * 作业区巡检场景表--Dao接口
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridManagerCaseDao {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	int insert(WorkGridManagerCase insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	int updateById(WorkGridManagerCase updateData);
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	int deleteById(WorkGridManagerCase deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WorkGridManagerCase queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	List<WorkGridManagerCase> queryList(WorkGridManagerCaseQuery query);
	/**
	 * 按条件查询数量
	 * @param query
	 * @return
	 */
	long queryCount(WorkGridManagerCaseQuery query);
	/**
	 * 查询case列表
	 * @param caseCodeList
	 * @return
	 */
	List<WorkGridManagerCase> queryCaseListByCaseCodeList(List<String> caseCodeList);

}
