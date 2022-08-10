package com.jdl.basic.provider.core.dao.workStation;

import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkStationAttendPlan;
import com.jdl.basic.api.domain.workStation.WorkStationAttendPlanQuery;

import java.util.List;


/**
 * 岗位人员出勤计划表--Dao接口
 * 
 * @author wuyoude
 * @date 2021年12月30日 14:30:43
 *
 */
public interface WorkStationAttendPlanDao {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	int insert(WorkStationAttendPlan insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	//int updateById(WorkStationAttendPlan updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	int deleteById(WorkStationAttendPlan deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WorkStationAttendPlan queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	List<WorkStationAttendPlan> queryList(WorkStationAttendPlanQuery query);
	/**
	 * 按条件查询数量
	 * @param query
	 * @return
	 */
	long queryCount(WorkStationAttendPlanQuery query);
	/**
	 * 根据业务主键删除
	 * @param data
	 */
	int deleteByBusinessKey(WorkStationAttendPlan data);
	/**
	 * 根据业务主键查询
	 * @param data
	 * @return
	 */
	WorkStationAttendPlan queryByBusinessKey(WorkStationAttendPlan data);
	/**
	 * 查询场地班次列表
	 * @param query
	 * @return
	 */
	List<WorkStationAttendPlan> queryWaveDictList(WorkStationAttendPlanQuery query);
	/**
	 * 导出查询列表
	 * @param query
	 * @return
	 */
	List<WorkStationAttendPlan> queryListForExport(WorkStationAttendPlanQuery query);
	/**
	 * 根据id列表查询
	 * @param deleteRequest
	 * @return
	 */
	List<WorkStationAttendPlan> queryByIds(DeleteRequest<WorkStationAttendPlan> deleteRequest);
	/**
	 * 根据id批量删除
	 * @param deleteRequest
	 * @return
	 */
	int deleteByIds(DeleteRequest<WorkStationAttendPlan> deleteRequest);
}
