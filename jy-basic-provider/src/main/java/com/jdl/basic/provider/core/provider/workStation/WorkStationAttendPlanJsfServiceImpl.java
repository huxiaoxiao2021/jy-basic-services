package com.jdl.basic.provider.core.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkStationAttendPlan;
import com.jdl.basic.api.domain.workStation.WorkStationAttendPlanQuery;
import com.jdl.basic.api.service.workStation.WorkStationAttendPlanJsfService;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.config.lock.LockService;
import com.jdl.basic.provider.core.service.workStation.WorkStationAttendPlanService;
import com.jdl.basic.provider.hander.ResultHandler;
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

	@Autowired
	@Qualifier("jimdbRemoteLockService")
	private LockService lockService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(final WorkStationAttendPlan insertData){
		log.info("岗位人员出勤计划 insert 入参-{}", JSON.toJSONString(insertData));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_ATTEND_PLAN_EDIT, DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationAttendPlanService.insert(insertData);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格计划信息，请稍后操作！");
			}

			@Override
			public void error(Exception e) {
				log.error(e.getMessage(), e);
				result.toFail("操作异常，请稍后重试！");
			}
		});
		return result;
	}
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(final WorkStationAttendPlan updateData){
		log.info("岗位人员出勤计划 updateById 入参-{}", JSON.toJSONString(updateData));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_ATTEND_PLAN_EDIT,DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationAttendPlanService.updateById(updateData);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格计划信息，请稍后操作！");
			}

			@Override
			public void error(Exception e) {
				log.error(e.getMessage(), e);
				result.toFail("操作异常，请稍后重试！");
			}
		});
		return result;
	}
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(final WorkStationAttendPlan deleteData){
		log.info("岗位人员出勤计划 deleteById 入参-{}", JSON.toJSONString(deleteData));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_ATTEND_PLAN_EDIT,DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationAttendPlanService.deleteById(deleteData);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格计划信息，请稍后操作！");
			}

			@Override
			public void error(Exception e) {
				log.error(e.getMessage(), e);
				result.toFail("操作异常，请稍后重试！");
			}
		});
		return result;
	}
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkStationAttendPlan> queryById(Long id){
		if(log.isInfoEnabled()){
			log.info("岗位人员出勤计划 queryById 入参-{}", JSON.toJSONString(id));
		}
		return workStationAttendPlanService.queryById(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkStationAttendPlan>> queryPageList(WorkStationAttendPlanQuery query){
		if(log.isInfoEnabled()){
			log.info("岗位人员出勤计划 queryPageList 入参-{}", JSON.toJSONString(query));
		}
		return workStationAttendPlanService.queryPageList(query);
	 }
	@Override
	public Result<Boolean> importDatas(final List<WorkStationAttendPlan> dataList) {
		log.info("岗位人员出勤计划 importDatas");
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_ATTEND_PLAN_EDIT,DateHelper.FIVE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationAttendPlanService.importDatas(dataList);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格计划信息，请稍后操作！");
			}

			@Override
			public void error(Exception e) {
				log.error(e.getMessage(), e);
				result.toFail("操作异常，请稍后重试！");
			}
		});
		return result;
	}
	@Override
	public Result<List<WorkStationAttendPlan>> queryWaveDictList(WorkStationAttendPlanQuery query) {
		if(log.isInfoEnabled()){
			log.info("岗位人员出勤计划 queryWaveDictList 入参-{}", JSON.toJSONString(query));
		}
		return workStationAttendPlanService.queryWaveDictList(query);
	}
	@Override
	public Result<Boolean> deleteByIds(final DeleteRequest<WorkStationAttendPlan> deleteRequest) {
		log.info("岗位人员出勤计划 deleteByIds 入参-{}", JSON.toJSONString(deleteRequest));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_ATTEND_PLAN_EDIT,DateHelper.FIVE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationAttendPlanService.deleteByIds(deleteRequest);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格计划信息，请稍后操作！");
			}

			@Override
			public void error(Exception e) {
				log.error(e.getMessage(), e);
				result.toFail("操作异常，请稍后重试！");
			}
		});
		return result;
	}
	@Override
	public Result<Long> queryCount(WorkStationAttendPlanQuery query) {
		log.info("岗位人员出勤计划 queryCount 入参-{}", JSON.toJSONString(query));
		return workStationAttendPlanService.queryCount(query);
	}
	@Override
	public Result<List<WorkStationAttendPlan>> queryListForExport(WorkStationAttendPlanQuery query) {
		if(log.isInfoEnabled()){
			log.info("岗位人员出勤计划 queryListForExport 入参-{}", JSON.toJSONString(query));
		}
		return workStationAttendPlanService.queryListForExport(query);
	}

	@Override
	public Result<WorkStationAttendPlan> queryByBusinessKeys(WorkStationAttendPlan data) {
		if(log.isInfoEnabled()){
			log.info("岗位人员出勤计划 queryByBusinessKeys 入参-{}", JSON.toJSONString(data));
		}
		Result<WorkStationAttendPlan> result = Result.success();
		result.setData(workStationAttendPlanService.queryByBusinessKeysWithCache(data));
		return result;		
	}

}
