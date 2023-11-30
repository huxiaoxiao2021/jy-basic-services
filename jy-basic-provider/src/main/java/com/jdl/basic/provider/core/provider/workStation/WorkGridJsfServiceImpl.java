package com.jdl.basic.provider.core.provider.workStation;


import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.BatchAreaWorkGridQuery;
import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.domain.workStation.WorkGridDeviceVo;
import com.jdl.basic.api.domain.workStation.WorkGridEditVo;
import com.jdl.basic.api.domain.workStation.WorkGridImport;
import com.jdl.basic.api.domain.workStation.WorkGridQuery;
import com.jdl.basic.api.domain.workStation.WorkGridVo;
import com.jdl.basic.api.service.workStation.WorkGridJsfService;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.JYBasicRpcException;
import com.jdl.basic.provider.common.Jimdb.CacheService;
import com.jdl.basic.provider.config.lock.LockService;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.workStation.WorkGridService;
import com.jdl.basic.provider.hander.ResultHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 场地网格表--JsfService接口实现
 *
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Slf4j
@Service("workGridJsfService")
public class WorkGridJsfServiceImpl implements WorkGridJsfService {

	@Autowired
	@Qualifier("workGridService")
	private WorkGridService workGridService;

	@Autowired
	@Qualifier("jimdbRemoteLockService")
	private LockService lockService;

	@Autowired
	private BaseMajorManager baseMajorManager;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGrid insertData){
		return workGridService.insert(insertData);
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridEditVo updateData){
		return workGridService.updateById(updateData);
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGrid deleteData){
		return workGridService.deleteById(deleteData);
	 }
	@Override
	public Result<WorkGrid> queryById(Long id) {
		return workGridService.queryById(id);
	}
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridVo> queryByIdForConfigFlow(Long id){
		return workGridService.queryByIdForConfigFlow(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridVo>> queryPageList(WorkGridQuery query){
		return workGridService.queryPageList(query);
	 }
	@Override
	public Result<Long> queryCount(WorkGridQuery query) {
		return workGridService.queryCount(query);
	}
	@Override
	public Result<List<WorkGridVo>> queryListForExport(WorkGridQuery query) {
		return workGridService.queryListForExport(query);
	}
	@Override
	public Result<Boolean> deleteByIds(DeleteRequest<WorkGrid> deleteRequest) {
		log.info("场地网格管理 deleteByIds 入参-{}", JSON.toJSONString(deleteRequest));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_GRID_EDIT,DateHelper.FIVE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workGridService.deleteByIds(deleteRequest);
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
	public Result<Boolean> importDatas(List<WorkGridImport> dataList) {
		log.info("场地网格管理 deleteByIds 入参-{}", JSON.toJSONString(dataList));
		final Result<Boolean> result = Result.success();
		lockService.tryLock(CacheKeyConstants.CACHE_KEY_WORK_STATION_GRID_EDIT,DateHelper.FIVE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workGridService.importDatas(dataList);
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
	public Result<List<WorkGrid>> queryFloorDictList(WorkGrid queryParams) {
		return Result.success(workGridService.queryFloorDictList(queryParams));
	}

	@Override
	public Result<List<WorkGrid>> queryAreaDictList(WorkGrid queryParams) {
		return Result.success(workGridService.queryAreaDictList(queryParams));
	}

	@Override
	public Result<List<WorkGrid>> queryWorkGrid(WorkGrid queryParams) {
		return Result.success(workGridService.queryWorkGrid(queryParams));
	}

	@Override
	public Result<WorkGrid> queryByWorkGridKey(String workGridKey) {
		Result<WorkGrid> result = Result.success();
		result.setData(workGridService.queryByWorkGridKey(workGridKey));
		return result;
	}

	@Override
	public Result<List<WorkGrid>> batchQueryByWorkGridKey(List<String> workGridKeys) {
		return Result.success(workGridService.batchQueryByWorkGridKey(workGridKeys));
	}

	@Override
	public Result<List<WorkGrid>> queryAreaWorkGrid(WorkGridQuery query) {
		return Result.success(workGridService.queryAreaWorkGrid(query));
	}

	@Override
	public Result<WorkGrid> exactQueryWorkGridByBizKey(WorkGrid query) {
		checkWorkGridQuery(query);
		List<WorkGrid> workGridList =workGridService.queryWorkGrid(query);
		if (CollectionUtils.isNotEmpty(workGridList)){
			return Result.success(workGridList.get(0));
		}
		return Result.fail("未查询到相应的网格数据！");
	}

	@Override
	public Result<List<WorkGrid>> queryAllGridBySiteCode(WorkGridQuery query) {
		if (query.getSiteCode() == null) {
			return Result.fail("场地编码不能为空！");
		}
		return Result.success(workGridService.queryAllGridBySiteCode(query));
	}

	private void checkWorkGridQuery(WorkGrid query) {
		if (ObjectHelper.isEmpty(query.getSiteCode())){
			throw new JYBasicRpcException("参数错误：siteCode为空！");
		}
		if (ObjectHelper.isEmpty(query.getAreaCode())){
			throw new JYBasicRpcException("参数错误：areaCode为空！");
		}
		if (ObjectHelper.isEmpty(query.getFloor())){
			throw new JYBasicRpcException("参数错误：floor为空！");
		}
		if (ObjectHelper.isEmpty(query.getGridNo())){
			throw new JYBasicRpcException("参数错误：gridNo为空！");
		}
	}
	@Override
	public List<Integer> querySiteListForManagerScan(WorkGridQuery workGridQuery) {
		return workGridService.querySiteListForManagerScan(workGridQuery);
	}
	@Override
	public List<WorkGrid> queryListForManagerSiteScan(WorkGridQuery workGridQuery) {
		return workGridService.queryListForManagerSiteScan(workGridQuery);
	}

	/**
	 * 根据场地ID获取网格信息
	 * @param siteCodes
	 * @return
	 */
	@Override
	public List<WorkGrid> getGridInfoBySiteCodes(List<String> siteCodes) {
		return workGridService.getGridInfoBySiteCodes(siteCodes);
	}

	/**
	 * 根据场地+作业区+楼层+网格编码--精确查询网格数据
	 * <p>
	 * siteCode
	 * floor
	 * gridCode
	 * areaCode
	 *
	 * @param query
	 * @return
	 */
	@Override
	public Result<WorkGrid> queryWorkGridByBizKey(WorkGrid query) {
		checkWorkGrid4Query(query);
		List<WorkGrid> workGridList =workGridService.queryWorkGridByBizKey(query);
		if (CollectionUtils.isNotEmpty(workGridList)){
			return Result.success(workGridList.get(0));
		}
		return Result.fail("未查询到相应的网格数据！");
	}


	private void checkWorkGrid4Query(WorkGrid query) {
		if (ObjectHelper.isEmpty(query.getSiteCode())){
			throw new JYBasicRpcException("参数错误：siteCode为空！");
		}
		if (ObjectHelper.isEmpty(query.getAreaCode())){
			throw new JYBasicRpcException("参数错误：areaCode为空！");
		}
		if (ObjectHelper.isEmpty(query.getFloor())){
			throw new JYBasicRpcException("参数错误：floor为空！");
		}
		if (ObjectHelper.isEmpty(query.getGridCode())){
			throw new JYBasicRpcException("参数错误：gridCode为空！");
		}
	}

	@Override
	public Result<WorkGrid> queryByBusinessKeys(WorkGrid workGrid) {
		return workGridService.queryByBusinessKeys(workGrid);
	}

	@Override
	public Result<List<WorkGrid>> batchQueryAreaWorkGrid(BatchAreaWorkGridQuery query) {
		if (CollectionUtils.isEmpty(query.getSiteCodes())) {
			return Result.fail("场地编码不能为空！");
		}
		if (CollectionUtils.isEmpty(query.getAreaCodes())) {
			return Result.fail("作业区编码不能为空！");
		}
		return Result.success(workGridService.batchQueryAreaWorkGrid(query));
	}
	@Override
	public Result<PageDto<WorkGridDeviceVo>> queryMachineListData(WorkGridQuery query) {
		return workGridService.queryMachineListData(query);
	}
	@Resource
	@Qualifier("JimdbCacheService")
	private CacheService cacheService;
	@Override
	public Result<WorkGrid> queryByWorkGridKeyWithCache(String workGridKey) {
		String cacheKey = "WorkGridService.queryByWorkGridKeyWithCache" + workGridKey;
		log.info("WorkGridJsfServiceImpl,queryByWorkGridKeyWithCache before: {}", cacheService.get(cacheKey));
		Result<WorkGrid> result = Result.success();
		result.setData(workGridService.queryByWorkGridKeyWithCache(workGridKey));
		log.info("WorkGridJsfServiceImpl,queryByWorkGridKeyWithCache before: {}", cacheService.get(cacheKey));
		return result;
	}

}
