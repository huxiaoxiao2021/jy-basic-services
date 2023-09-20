package com.jdl.basic.provider.core.service.workStation;

import java.util.List;

import com.jdl.basic.api.domain.workStation.WorkGridOwnerUser;
import com.jdl.basic.api.domain.workStation.WorkGridOwnerUserQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

/**
 * 场地网格-负责人表--Service接口
 * 
 * @author wuyoude
 * @date 2023年09月18日 22:43:58
 *
 */
public interface WorkGridOwnerUserService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkGridOwnerUser insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkGridOwnerUser updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	int deleteByGridKey(WorkGridOwnerUser deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGridOwnerUser> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkGridOwnerUser>> queryPageList(WorkGridOwnerUserQuery query);
	
	List<WorkGridOwnerUser> queryListByGridKey(String businessKey);

}
