package com.jdl.basic.api.service.workStation;

import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkStation;
import com.jdl.basic.api.domain.workStation.WorkStationCountVo;
import com.jdl.basic.api.domain.workStation.WorkStationQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * 工序岗位信息表--JsfService接口
 * 
 * @author wuyoude
 * @date 2021年12月30日 14:30:43
 *
 */
public interface WorkStationJsfService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkStation insertData);
	/**
	 * 导入数据
	 * @param dataList
	 * @return
	 */
	Result<Boolean> importDatas(List<WorkStation> dataList);	
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkStation updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkStation deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkStation> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkStation>> queryPageList(WorkStationQuery query);
	/**
	 * 按条件查询统计信息
	 * @param query
	 * @return
	 */
	Result<WorkStationCountVo> queryPageCount(WorkStationQuery query);
	/**
	 * 查询作业区字典
	 * @return
	 */
	Result<List<WorkStation>> queryAreaDictList(WorkStationQuery query);	
	/**
	 * 查询工序字典
	 * @return
	 */
	Result<List<WorkStation>> queryWorkDictList(WorkStationQuery query);
	/**
	 * 批量删除
	 * @param deleteRequest
	 * @return
	 */
	Result<Boolean> deleteByIds(DeleteRequest<WorkStation> deleteRequest);
	/**
	 * 查询数量
	 * @param query
	 * @return
	 */
	Result<Long> queryCount(WorkStationQuery query);
	/**
	 * 查询列表导出
	 * @param query
	 * @return
	 */
	Result<List<WorkStation>> queryListForExport(WorkStationQuery query);


	/**
	 * 根据业务主键查询
	 * @param data
	 * @return
	 */
	Result<WorkStation> queryByBusinessKey(WorkStation data);

	/**
	 * 根据业务主键查询
	 * @param businessKey
	 * @return
	 */
	Result<WorkStation> queryByRealBusinessKey(String businessKey);

}
