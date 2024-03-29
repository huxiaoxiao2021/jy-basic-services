package com.jdl.basic.provider.core.provider.workStation;


import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.api.service.workStation.WorkStationGridJsfService;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.config.lock.LockService;
import com.jdl.basic.provider.core.service.workStation.WorkStationGridService;
import com.jdl.basic.provider.hander.ResultHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工序岗位网格表--JsfService接口实现
 *
 * @author wuyoude
 * @date 2021年12月30日 14:30:43
 *
 */
@Slf4j
@Service
public class WorkStationGridJsfServiceImpl implements WorkStationGridJsfService {

	@Autowired
	@Qualifier("workStationGridService")
	private WorkStationGridService workStationGridService;

	@Autowired
	@Qualifier("jimdbRemoteLockService")
	private LockService lockService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(final WorkStationGrid insertData){
		log.info("场地网格工序管理 insert 入参-{}", JSON.toJSONString(insertData));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_GRID_EDIT, DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationGridService.insert(insertData);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格信息，请稍后操作！");
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
	public Result<Boolean> updateById(final WorkStationGrid updateData){
		log.info("场地网格工序管理 updateById 入参-{}", JSON.toJSONString(updateData));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_GRID_EDIT,DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationGridService.updateById(updateData);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格信息，请稍后操作！");
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
	public Result<Boolean> deleteById(final WorkStationGrid deleteData){
		log.info("场地网格工序管理 deleteById 入参-{}", JSON.toJSONString(deleteData));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_GRID_EDIT,DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationGridService.deleteById(deleteData);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格信息，请稍后操作！");
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
	public Result<WorkStationGrid> queryById(Long id){
		log.info("场地网格工序管理 queryById 入参-{}", id);
		return workStationGridService.queryById(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkStationGrid>> queryPageList(WorkStationGridQuery query){
		if(log.isInfoEnabled()){
			log.info("场地网格工序管理 queryPageList 入参-{}", JSON.toJSONString(query));
		}
		return workStationGridService.queryPageList(query);
	 }
	/**
	 * 按条件查询统计信息
	 * @param query
	 * @return
	 */
	@Override
	public Result<WorkStationGridCountVo> queryPageCount(WorkStationGridQuery query){
		log.info("场地网格工序管理 queryPageCount 入参-{}", JSON.toJSONString(query));
		return workStationGridService.queryPageCount(query);
	 }
	@Override
	public Result<List<WorkStationGrid>> queryAllGridBySiteCode(WorkStationGridQuery query) {
		if(log.isInfoEnabled()){
			log.info("场地网格工序管理 queryAllGridBySiteCode 入参-{}", JSON.toJSONString(query));
		}
		return workStationGridService.queryAllGridBySiteCode(query);
	}
	@Override
	public Result<Boolean> importDatas(final List<WorkStationGrid> dataList) {
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_GRID_EDIT,DateHelper.FIVE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationGridService.importDatas(dataList);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格信息，请稍后操作！");
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
	public Result<List<WorkStationGrid>> queryGridWorkDictList(WorkStationGridQuery query) {
		if(log.isInfoEnabled()){
			log.info("场地网格工序管理 queryGridWorkDictList 入参-{}", JSON.toJSONString(query));
		}
		return workStationGridService.queryGridWorkDictList(query);
	}
	@Override
	public Result<List<WorkStationGrid>> queryGridAreaDictList(WorkStationGridQuery query) {
		if(log.isInfoEnabled()) {
			log.info("场地网格工序管理 queryGridAreaDictList 入参-{}", JSON.toJSONString(query));
		}
		return workStationGridService.queryGridAreaDictList(query);
	}
	@Override
	public Result<List<WorkStationGrid>> queryGridNoDictList(WorkStationGridQuery query) {
		if(log.isInfoEnabled()) {
			log.info("场地网格工序管理 queryGridNoDictList 入参-{}", JSON.toJSONString(query));
		}
		return workStationGridService.queryGridNoDictList(query);
	}
	@Override
	public Result<List<WorkStationGrid>> queryGridFloorDictList(WorkStationGridQuery query) {
		if(log.isInfoEnabled()) {
			log.info("场地网格工序管理 queryGridFloorDictList 入参-{}", JSON.toJSONString(query));
		}
		return workStationGridService.queryGridFloorDictList(query);
	}
	@Override
	public Result<Boolean> deleteByIds(final DeleteRequest<WorkStationGrid> deleteRequest) {
		log.info("场地网格工序管理 deleteByIds 入参-{}", JSON.toJSONString(deleteRequest));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_GRID_EDIT,DateHelper.FIVE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workStationGridService.deleteByIds(deleteRequest);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格信息，请稍后操作！");
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
	public Result<Long> queryCount(WorkStationGridQuery query) {
		log.info("场地网格工序管理 queryCount 入参-{}", JSON.toJSONString(query));
		return workStationGridService.queryCount(query);
	}
	@Override
	public Result<List<WorkStationGrid>> queryListForExport(WorkStationGridQuery query) {
		if(log.isInfoEnabled()) {
			log.info("场地网格工序管理 queryListForExport 入参-{}", JSON.toJSONString(query));
		}
		return workStationGridService.queryListForExport(query);
	}
	@Override
	public List<String> queryOwnerUserErpListBySiteCode(WorkStationGridQuery query) {
		if(log.isInfoEnabled()) {
			log.info("场地网格工序管理 query 入参-{}", JSON.toJSONString(query));
		}
		return workStationGridService.queryOwnerUserErpListBySiteCode(query);
	}
	@Override
	public List<WorkStationGrid> querySiteListByOrgCode(WorkStationGridQuery query) {
		if(log.isInfoEnabled()) {
			log.info("场地网格工序管理 querySiteListByOrgCode 入参-{}", JSON.toJSONString(query));
		}
		return workStationGridService.querySiteListByOrgCode(query);
	}

	@Override
	public Result<WorkStationGrid> queryByBusinessKey(WorkStationGrid data) {
		if(log.isInfoEnabled()) {
			log.info("场地网格工序管理 queryByBusinessKey 入参-{}", JSON.toJSONString(data));
		}
		Result<WorkStationGrid> result = Result.success();
		result.setData(workStationGridService.queryByBusinessKeyWithCache(data));
		return result;
	}

	@Override
	public Result<WorkStationGrid> queryByGridKey(WorkStationGridQuery workStationGridCheckQuery) {
		if(log.isInfoEnabled()) {
			log.info("场地网格工序管理 queryByGridKey 入参-{}", JSON.toJSONString(workStationGridCheckQuery));
		}
		Result<WorkStationGrid> result = Result.success();
		result.setData(workStationGridService.queryByGridKeyWithCache(workStationGridCheckQuery));
		return result;
	}

	@Override
	public Result<WorkStationGrid> queryWorkStationGridBybusinessKeyWithCache(String businessKey) {
		Result<WorkStationGrid> result = new Result<>();
		try {
			if(log.isInfoEnabled()) {
				log.info("场地网格工序管理 queryWorkStationGridBybusinessKeyWithCache 入参-{}", JSON.toJSONString(businessKey));
			}
			if(StringUtils.isEmpty(businessKey)){
				result.toFail("业务主键不能为空!");
				return result;
			}
			return workStationGridService.queryWorkStationGridBybusinessKeyWithCache(businessKey);
		}catch (Exception e){
			log.error("获取场地网格工序异常! 入参-{}-{}",businessKey,e.getMessage(),e);
			result.toError("获取场地网格工序异常!");
		}
		return result;
	}

    @Override
    public Result<PageDto<WorkStationGrid>> queryAllWorkGridList(WorkStationGridQuery query) {
        if(log.isInfoEnabled()){
            log.info("场地网格工序管理 queryAllWorkGridList 入参-{}", JSON.toJSONString(query));
        }
        return workStationGridService.queryAllWorkGridList(query);
    }
	@Override
	public void stopInit() {
		workStationGridService.stopInit();
	}
	@Override
	public void initAllWorkGrid() {
		log.info("场地网格工序管理 initAllWorkGrid ");
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_GRID_EDIT,120 * DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				workStationGridService.initAllWorkGrid();
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格信息，请稍后操作！");
			}
			@Override
			public void error(Exception e) {
				log.error(e.getMessage(), e);
				result.toFail("操作异常，请稍后重试！");
			}
		});		
	}
	@Override
	public void initWorkGrid(Long id) {
		log.info("场地网格工序管理 initWorkGrid 入参-{}", id);
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_GRID_EDIT,DateHelper.ONE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				workStationGridService.initWorkGrid(id);
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格信息，请稍后操作！");
			}
			@Override
			public void error(Exception e) {
				log.error(e.getMessage(), e);
				result.toFail("操作异常，请稍后重试！");
			}
		});
	}
	@Override
	public List<Integer> querySiteListForManagerScan(WorkStationGridQuery workStationGridQuery) {
		return workStationGridService.querySiteListForManagerScan(workStationGridQuery);
	}
	@Override
	public List<WorkStationGrid> queryListForManagerSiteScan(WorkStationGridQuery workStationGridQuery) {
		return workStationGridService.queryListForManagerSiteScan(workStationGridQuery);
	}

	@Override
	public List<WorkStationGrid> queryListForWorkGridVo(WorkStationGridQuery query) {
		return workStationGridService.queryListForWorkGridVo(query);
	}

	@Override
	public Result<List<WorkStationGrid>> queryWorkStationGridBySiteCode(WorkStationGridQuery query) {
		log.info("场地网格工序管理 queryWorkStationGridBySiteCode 入参-{}", JSON.toJSONString(query));
		return workStationGridService.queryWorkStationGridBySiteCode(query);
	}


	@Override
	public List<String> queryBusinessKeyByRefWorkGridKeys(List<String> refWorkGridKeys){
		return workStationGridService.queryBusinessKeyByRefWorkGridKeys(refWorkGridKeys);
	}
}
