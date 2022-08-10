package com.jdl.basic.provider.core.service.workStation;

import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkStationAttendPlan;
import com.jdl.basic.api.domain.workStation.WorkStationAttendPlanQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * 岗位人员出勤计划表--Service接口
 * 
 * @author wuyoude
 * @date 2021年12月30日 14:30:43
 *
 */
public interface WorkStationAttendPlanService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkStationAttendPlan insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkStationAttendPlan updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkStationAttendPlan deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkStationAttendPlan> queryById(Long id);
	/**
	 * 根据businessKeys查询
	 * @param data
	 * @return
	 */
	Result<WorkStationAttendPlan> queryByBusinessKeys(WorkStationAttendPlan data);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkStationAttendPlan>> queryPageList(WorkStationAttendPlanQuery query);
	/**
	 * 导入数据
	 * @param dataList
	 * @return
	 */
	Result<Boolean> importDatas(List<WorkStationAttendPlan> dataList);
	/**
	 * 查询场地下的班次字典
	 * @param query
	 * @return
	 */
	Result<List<WorkStationAttendPlan>> queryWaveDictList(WorkStationAttendPlanQuery query);
	/**
	 * 批量删除
	 * @param deleteRequest
	 * @return
	 */
	Result<Boolean> deleteByIds(DeleteRequest<WorkStationAttendPlan> deleteRequest);
	/**
	 * 查询数量
	 * @param query
	 * @return
	 */
	Result<Long> queryCount(WorkStationAttendPlanQuery query);
	/**
	 * 查询列表导出
	 * @param query
	 * @return
	 */
	Result<List<WorkStationAttendPlan>> queryListForExport(WorkStationAttendPlanQuery query);	
}
