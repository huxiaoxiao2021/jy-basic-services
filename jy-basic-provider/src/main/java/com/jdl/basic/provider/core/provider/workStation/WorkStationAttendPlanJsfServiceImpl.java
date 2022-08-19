package com.jdl.basic.provider.core.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkStationAttendPlan;
import com.jdl.basic.api.domain.workStation.WorkStationAttendPlanQuery;
import com.jdl.basic.api.service.workStation.WorkStationAttendPlanJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.workStation.WorkStationAttendPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位人员出勤计划表--JsfService接口实现
 * 
 * @author wuyoude
 * @date 2021年12月30日 14:30:43
 *
 */
@Slf4j
@Service
public class WorkStationAttendPlanJsfServiceImpl implements WorkStationAttendPlanJsfService {

	@Autowired
	@Qualifier("workStationAttendPlanService")
	private WorkStationAttendPlanService workStationAttendPlanService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkStationAttendPlan insertData){
		log.info("岗位人员出勤计划 insert 入参-{}", JSON.toJSONString(insertData));
		return workStationAttendPlanService.insert(insertData);
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkStationAttendPlan updateData){
		log.info("岗位人员出勤计划 updateById 入参-{}", JSON.toJSONString(updateData));
		return workStationAttendPlanService.updateById(updateData);
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkStationAttendPlan deleteData){
		log.info("岗位人员出勤计划 deleteById 入参-{}", JSON.toJSONString(deleteData));
		return workStationAttendPlanService.deleteById(deleteData);
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkStationAttendPlan> queryById(Long id){
		log.info("岗位人员出勤计划 queryById 入参-{}", JSON.toJSONString(id));
		return workStationAttendPlanService.queryById(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkStationAttendPlan>> queryPageList(WorkStationAttendPlanQuery query){
		log.info("岗位人员出勤计划 queryPageList 入参-{}", JSON.toJSONString(query));
		return workStationAttendPlanService.queryPageList(query);
	 }
	@Override
	public Result<Boolean> importDatas(List<WorkStationAttendPlan> dataList) {
		log.info("岗位人员出勤计划 importDatas");
		return workStationAttendPlanService.importDatas(dataList);
	}
	@Override
	public Result<List<WorkStationAttendPlan>> queryWaveDictList(WorkStationAttendPlanQuery query) {
		log.info("岗位人员出勤计划 queryWaveDictList 入参-{}", JSON.toJSONString(query));
		return workStationAttendPlanService.queryWaveDictList(query);
	}
	@Override
	public Result<Boolean> deleteByIds(DeleteRequest<WorkStationAttendPlan> deleteRequest) {
		log.info("岗位人员出勤计划 deleteByIds 入参-{}", JSON.toJSONString(deleteRequest));
		return workStationAttendPlanService.deleteByIds(deleteRequest);
	}
	@Override
	public Result<Long> queryCount(WorkStationAttendPlanQuery query) {
		log.info("岗位人员出勤计划 queryCount 入参-{}", JSON.toJSONString(query));
		return workStationAttendPlanService.queryCount(query);
	}
	@Override
	public Result<List<WorkStationAttendPlan>> queryListForExport(WorkStationAttendPlanQuery query) {
		log.info("岗位人员出勤计划 queryListForExport 入参-{}", JSON.toJSONString(query));
		return workStationAttendPlanService.queryListForExport(query);
	}

	@Override
	public Result<WorkStationAttendPlan> queryByBusinessKeys(WorkStationAttendPlan data) {
		log.info("岗位人员出勤计划 queryByBusinessKeys 入参-{}", JSON.toJSONString(data));
		return workStationAttendPlanService.queryByBusinessKeys(data);
	}

}
