package com.jdl.basic.provider.core.dao.work;

import java.util.List;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseItem;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseItemQuery;

/**
 * 作业区巡检-项目明细表--Dao接口
 * 
 * @author wuyoude
 * @date 2023年05月31日 20:24:11
 *
 */
public interface WorkGridManagerCaseItemDao {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	int insert(WorkGridManagerCaseItem insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	int updateById(WorkGridManagerCaseItem updateData);
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	int deleteById(WorkGridManagerCaseItem deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WorkGridManagerCaseItem queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	List<WorkGridManagerCaseItem> queryList(WorkGridManagerCaseItemQuery query);
	/**
	 * 按条件查询数量
	 * @param query
	 * @return
	 */
	long queryCount(WorkGridManagerCaseItemQuery query);
	/**
	 * 根据caseCodeList查询列表
	 * @param caseCodeList
	 * @return
	 */
	List<WorkGridManagerCaseItem> queryCaseItemListByCaseCodeList(List<String> caseCodeList);

}
