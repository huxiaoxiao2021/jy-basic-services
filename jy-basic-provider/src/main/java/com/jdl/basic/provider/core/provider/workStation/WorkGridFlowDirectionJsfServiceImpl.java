package com.jdl.basic.provider.core.provider.workStation;


import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jd.jsf.gd.util.StringUtils;
import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOffline;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOfflineQuery;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirection;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionQuery;
import com.jdl.basic.api.service.workStation.WorkGridFlowDirectionJsfService;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.config.lock.LockService;
import com.jdl.basic.provider.core.service.workStation.WorkGridFlowDetailOfflineService;
import com.jdl.basic.provider.core.service.workStation.WorkGridFlowDirectionService;
import com.jdl.basic.provider.hander.ResultHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 场地网格流向表--JsfService接口实现
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Slf4j
@Service("workGridFlowDirectionJsfService")
public class WorkGridFlowDirectionJsfServiceImpl implements WorkGridFlowDirectionJsfService {

	@Autowired
	@Qualifier("workGridFlowDirectionService")
	private WorkGridFlowDirectionService workGridFlowDirectionService;
	
	@Autowired
	@Qualifier("jimdbRemoteLockService")
	private LockService lockService;
	
	@Autowired
	@Qualifier("workGridFlowDetailOfflineService")
	private WorkGridFlowDetailOfflineService workGridFlowDetailOfflineService;
	
	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridFlowDirection insertData){
		return workGridFlowDirectionService.insert(insertData);
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridFlowDirection updateData){
		return workGridFlowDirectionService.updateById(updateData);
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridFlowDirection deleteData){
		return workGridFlowDirectionService.deleteById(deleteData);
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridFlowDirection> queryById(Long id){
		return workGridFlowDirectionService.queryById(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridFlowDirection>> queryPageList(WorkGridFlowDirectionQuery query){
		return workGridFlowDirectionService.queryPageList(query);
	 }
	@Override
	public Result<Boolean> deleteByIds(DeleteRequest<WorkGridFlowDirection> deleteRequest) {
		log.info("场地网格-流向删除 deleteByIds 入参-{}", JSON.toJSONString(deleteRequest));
		final Result<Boolean> result = Result.success();
		String cacheKey = CacheKeyConstants.CACHE_KEY_WORK_GRID_FLOW_EDIT;
		if(deleteRequest != null && StringUtils.isNotBlank(deleteRequest.getOperateBusinessKey())) {
			cacheKey = String.format(CacheKeyConstants.CACHE_KEY_WORK_GRID_EDIT_ONE_FORMAT, deleteRequest.getOperateBusinessKey());
		}
		lockService.tryLock(cacheKey,DateHelper.FIVE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workGridFlowDirectionService.deleteByIds(deleteRequest);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格流向信息，请稍后操作！");
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
	public Result<Boolean> importDatas(List<WorkGridFlowDirection> dataList) {
		log.info("场地网格-流向导入 importDatas 入参-{}", JSON.toJSONString(dataList));
		final Result<Boolean> result = Result.success();
		String cacheKey = CacheKeyConstants.CACHE_KEY_WORK_GRID_FLOW_EDIT;
		if(CollectionUtils.isNotEmpty(dataList)) {
			cacheKey = String.format(CacheKeyConstants.CACHE_KEY_WORK_GRID_EDIT_ONE_FORMAT, dataList.get(0).getRefWorkGridKey());
		}
		lockService.tryLock(cacheKey,DateHelper.FIVE_MINUTES_MILLI, new ResultHandler() {
			@Override
			public void success() {
				Result<Boolean> apiResult = workGridFlowDirectionService.importDatas(dataList);
				if(!apiResult.isSuccess()) {
					result.setCode(apiResult.getCode());
					result.setMessage(apiResult.getMessage());
					result.setData(apiResult.getData());
					return ;
				}
			}
			@Override
			public void fail() {
				result.toFail("其他用户正在修改网格流向信息，请稍后操作！");
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
	public Result<Boolean> addFlowList(List<WorkGridFlowDirection> dataList) {
		return importDatas(dataList);
	}
	@Override
	public Result<PageDto<WorkGridFlowDetailOffline>> queryFlowDataForSelect(WorkGridFlowDetailOfflineQuery query) {
		return workGridFlowDetailOfflineService.queryPageList(query);
	}
	@Override
	public Result<PageDto<WorkGridFlowDirection>> queryFlowData(WorkGridFlowDirectionQuery query) {
		return queryPageList(query);
	}

}
