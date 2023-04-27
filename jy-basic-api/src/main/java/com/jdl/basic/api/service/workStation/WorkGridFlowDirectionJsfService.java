package com.jdl.basic.api.service.workStation;

import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirection;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionQuery;

/**
 * 场地网格流向表--JsfService接口
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
public interface WorkGridFlowDirectionJsfService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkGridFlowDirection insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkGridFlowDirection updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkGridFlowDirection deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGridFlowDirection> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkGridFlowDirection>> queryPageList(WorkGridFlowDirectionQuery query);

}
