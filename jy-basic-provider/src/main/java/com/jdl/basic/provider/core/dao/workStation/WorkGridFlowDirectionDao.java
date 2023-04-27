package com.jdl.basic.provider.core.dao.workStation;

import java.util.List;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirection;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionQuery;

/**
 * 场地网格流向表--Dao接口
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
public interface WorkGridFlowDirectionDao {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	int insert(WorkGridFlowDirection insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	int updateById(WorkGridFlowDirection updateData);
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	int deleteById(WorkGridFlowDirection deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WorkGridFlowDirection queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	List<WorkGridFlowDirection> queryList(WorkGridFlowDirectionQuery query);
	/**
	 * 按条件查询数量
	 * @param query
	 * @return
	 */
	long queryCount(WorkGridFlowDirectionQuery query);
	/**
	 * 根据workGridKey查询数据列表
	 * @param workGridKey
	 * @return
	 */
	List<WorkGridFlowDirection> queryListForWorkGridVo(WorkGridFlowDirectionQuery query);

}
