package com.jdl.basic.provider.core.dao.workStation;

import java.util.List;
import com.jdl.basic.api.domain.workStation.WorkGridOwnerUser;
import com.jdl.basic.api.domain.workStation.WorkGridOwnerUserQuery;

/**
 * 场地网格-负责人表--Dao接口
 * 
 * @author wuyoude
 * @date 2023年09月18日 22:43:58
 *
 */
public interface WorkGridOwnerUserDao {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	int insert(WorkGridOwnerUser insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	int updateById(WorkGridOwnerUser updateData);
	/**
	 * 根据gridKey删除数据
	 * @param id
	 * @return
	 */
	int deleteByGridKey(WorkGridOwnerUser deleteData);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WorkGridOwnerUser queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	List<WorkGridOwnerUser> queryList(WorkGridOwnerUserQuery query);
	/**
	 * 按条件查询数量
	 * @param query
	 * @return
	 */
	long queryCount(WorkGridOwnerUserQuery query);
	/**
	 * 查询网格下所有数据
	 * @param businessKey
	 * @return
	 */
	List<WorkGridOwnerUser> queryListByGridKey(String businessKey);

}
