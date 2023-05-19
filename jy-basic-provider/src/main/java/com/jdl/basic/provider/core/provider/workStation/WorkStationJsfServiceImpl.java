package com.jdl.basic.provider.core.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.api.service.workStation.WorkStationJsfService;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.config.lock.LockService;
import com.jdl.basic.provider.core.service.workStation.WorkStationService;
import com.jdl.basic.provider.hander.ResultHandler;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: WorkStationServiceImpl
 * @Description: 网格工序信息表--Service接口实现
 * @author wuyoude
 * @date 2022年02月23日 11:01:53
 *
 */
@Slf4j
@Service
public class WorkStationJsfServiceImpl implements WorkStationJsfService {

	@Autowired
	@Qualifier("workStationService")
	private WorkStationService workStationService;

	@Autowired
	@Qualifier("jimdbRemoteLockService")
	private LockService lockService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(final WorkStation insertData){
		log.info("网格工序管理 insert 入参-{}", JSON.toJSONString(insertData));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_EDIT, DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationService.insert(insertData);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改工序信息，请稍后操作！");
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
	public Result<Boolean> importDatas(final List<WorkStation> dataList) {
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_EDIT,DateHelper.FIVE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationService.importDatas(dataList);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改工序信息，请稍后操作！");
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
	public Result<Boolean> updateById(final WorkStation updateData){
		log.info("网格工序管理 updateById 入参-{}", JSON.toJSONString(updateData));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_EDIT,DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationService.updateById(updateData);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改工序信息，请稍后操作！");
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
	public Result<Boolean> deleteById(final WorkStation deleteData){
		log.info("网格工序管理 deleteById 入参-{}", JSON.toJSONString(deleteData));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_EDIT,DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationService.deleteById(deleteData);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改工序信息，请稍后操作！");
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
	public Result<WorkStation> queryById(Long id){
		log.info("网格工序管理 queryById 入参-{}", JSON.toJSONString(id));
		return workStationService.queryById(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkStation>> queryPageList(WorkStationQuery query){
		if(log.isInfoEnabled()) {
			log.info("网格工序管理 queryPageList 入参-{}", JSON.toJSONString(query));
		}
		return workStationService.queryPageList(query);
	 }
	/**
	 * 按条件查询统计信息
	 * @param query
	 * @return
	 */	
	@Override	
	public Result<WorkStationCountVo> queryPageCount(WorkStationQuery query){
		if(log.isInfoEnabled()) {
			log.info("网格工序管理 queryPageCount 入参-{}", JSON.toJSONString(query));
		}
		return workStationService.queryPageCount(query);
	 }
	@Override
	public Result<List<WorkStation>> queryAreaDictList(WorkStationQuery query) {
		if(log.isInfoEnabled()) {
			log.info("网格工序管理 queryAreaDictList 入参-{}", JSON.toJSONString(query));
		}
		return workStationService.queryAreaDictList(query);
	}
	@Override
	public Result<List<WorkStation>> queryWorkDictList(WorkStationQuery query) {
		if(log.isInfoEnabled()) {
			log.info("网格工序管理 queryWorkDictList 入参-{}", JSON.toJSONString(query));
		}
		return workStationService.queryWorkDictList(query);
	}
	@Override
	public Result<Boolean> deleteByIds(final DeleteRequest<WorkStation> deleteRequest) {
		log.info("网格工序管理 deleteByIds 入参-{}", JSON.toJSONString(deleteRequest));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_EDIT,DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationService.deleteByIds(deleteRequest);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改工序信息，请稍后操作！");
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
	public Result<Long> queryCount(WorkStationQuery query) {
		if(log.isInfoEnabled()) {
			log.info("网格工序管理 queryCount 入参-{}", JSON.toJSONString(query));
		}
		return workStationService.queryCount(query);
	}
	@Override
	public Result<List<WorkStation>> queryListForExport(WorkStationQuery query) {
		if(log.isInfoEnabled()) {
			log.info("网格工序管理 queryListForExport 入参-{}", JSON.toJSONString(query));
		}
		return workStationService.queryListForExport(query);
	}

	@Override
	public Result<WorkStation> queryByBusinessKey(WorkStation data) {
		if(log.isInfoEnabled()) {
			log.info("网格工序管理 queryByBusinessKey 入参-{}", JSON.toJSONString(data));
		}
		Result<WorkStation> result = Result.success();
		result.setData(workStationService.queryByBusinessKeyWithCache(data));
		return result;
	}

	@Override
	public Result<WorkStation> queryByRealBusinessKey(String businessKey) {
		if(log.isInfoEnabled()) {
			log.info("网格工序管理 queryByRealBusinessKey 入参-{}", JSON.toJSONString(businessKey));
		}
		Result<WorkStation> result = Result.success();
		if(StringUtils.isEmpty(businessKey)){
			result.toFail("业务主键不能为空!");
			return result;
		}
		result.setData(workStationService.queryByRealBusinessKeyWithCache(businessKey));
		return result;	
	}

	@Override
	public Result<WorkStation> queryWorkStationBybusinessKeyWithCache(String businessKey) {
		Result<WorkStation> result = new Result<>();
		try {
			if(log.isInfoEnabled()) {
				log.info("网格工序管理 queryWorkStationBybusinessKeyWithCache 入参-{}", JSON.toJSONString(businessKey));
			}
			if(StringUtils.isEmpty(businessKey)){
				result.toFail("业务主键不能为空!");
				return result;
			}
			return workStationService.queryWorkStationBybusinessKeyWithCache(businessKey);
		}catch (Exception e){
			log.error("获取网格工序异常! 入参-{}-{}",businessKey,e.getMessage(),e);
			result.toError("获取网格工序异常!");
		}
		return result;
	}

	@Override
	public Result<List<WorkStationJobTypeDto>> queryWorkStationJobTypeBybusinessKey(String businessKey) {
		Result<List<WorkStationJobTypeDto>> result = new Result<>();
		try {
			if(log.isInfoEnabled()) {
				log.info("网格工序管理 queryWorkStationJobTypeBybusinessKey 入参-{}", JSON.toJSONString(businessKey));
			}
			if(StringUtils.isEmpty(businessKey)){
				result.toFail("业务主键不能为空!");
				return result;
			}
			return workStationService.queryWorkStationJobTypeBybusinessKey(businessKey);
		}catch (Exception e){
			log.error("获取网格工序工种类型异常! 入参-{}-{}",businessKey,e.getMessage(),e);
			result.toError("获取网格工序工种类型异常!");
		}
		return result;
	}

	@Override
	public void initAllWorkArea() {
		log.info("网格工序管理 initAllWorkArea 开始");
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_EDIT,DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				workStationService.initAllWorkArea();
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改工序信息，请稍后操作！");
			}
			@Override
			public void error(Exception e) {
				log.error(e.getMessage(), e);
				result.toFail("操作异常，请稍后重试！");
			}
		});
		log.info("网格工序管理 initAllWorkArea 结束");
	}
	@Override
	public void initWorkArea(Long id) {
		log.info("网格工序管理 initWorkArea-{} 开始",id);
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_EDIT,DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				workStationService.initWorkArea(id);
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改工序信息，请稍后操作！");
			}
			@Override
			public void error(Exception e) {
				log.error(e.getMessage(), e);
				result.toFail("操作异常，请稍后重试！");
			}
		});
		log.info("网格工序管理 initWorkArea 结束");
	}
}
