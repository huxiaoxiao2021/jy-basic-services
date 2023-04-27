package com.jdl.basic.provider.core.dao.workStation;

import java.util.List;
import com.jdl.basic.api.domain.workStation.WorkArea;
import com.jdl.basic.api.domain.workStation.WorkAreaQuery;

/**
 * 作业区信息表--Dao接口
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
public interface WorkAreaDao {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	int insert(WorkArea insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	int updateById(WorkArea updateData);
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	int deleteById(WorkArea deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WorkArea queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	List<WorkArea> queryList(WorkAreaQuery query);
	/**
	 * 按条件查询数量
	 * @param query
	 * @return
	 */
	long queryCount(WorkAreaQuery query);
	/**
	 * 根据areaCode查询
	 * @param areaCode
	 * @return
	 */	
	WorkArea queryByAreaCode(String areaCode);

}
