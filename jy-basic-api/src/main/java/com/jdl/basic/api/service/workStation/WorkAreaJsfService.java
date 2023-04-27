package com.jdl.basic.api.service.workStation;

import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.api.domain.workStation.WorkArea;
import com.jdl.basic.api.domain.workStation.WorkAreaQuery;

/**
 * 作业区信息表--JsfService接口
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
public interface WorkAreaJsfService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkArea insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkArea updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkArea deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkArea> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkArea>> queryPageList(WorkAreaQuery query);

}
