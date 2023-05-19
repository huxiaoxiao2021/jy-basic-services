package com.jdl.basic.api.service.workStation;

import java.util.List;

import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.domain.workStation.WorkGridImport;
import com.jdl.basic.api.domain.workStation.WorkGridQuery;
import com.jdl.basic.api.domain.workStation.WorkGridVo;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

/**
 * 场地网格表--JsfService接口
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
public interface WorkGridJsfService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkGrid insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkGrid updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkGrid deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGrid> queryById(Long id);	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGridVo> queryByIdForConfigFlow(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkGridVo>> queryPageList(WorkGridQuery query);
	/**
	 * 按条件查询-数量
	 * @param query
	 * @return
	 */
	Result<Long> queryCount(WorkGridQuery query);
	/**
	 * 按条件查询-列表导出用
	 * @param query
	 * @return
	 */
	Result<List<WorkGridVo>> queryListForExport(WorkGridQuery query);
	/**
	 * 批量删除
	 * @param deleteRequest
	 * @return
	 */
	Result<Boolean> deleteByIds(DeleteRequest<WorkGrid> deleteRequest);
	/**
	 * 导入数据
	 * @param dataList
	 * @return
	 */
	Result<Boolean> importDatas(List<WorkGridImport> dataList);	
}
