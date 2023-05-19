package com.jdl.basic.provider.core.provider.workStation;


import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jd.jsf.gd.util.StringUtils;
import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOffline;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDetailOfflineQuery;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirection;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionQuery;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionVo;
import com.jdl.basic.api.domain.workStation.WorkGridQuery;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.api.domain.workStation.WorkStationGridQuery;
import com.jdl.basic.api.service.workStation.WorkGridFlowDirectionJsfService;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.config.lock.LockService;
import com.jdl.basic.provider.core.service.workStation.WorkGridFlowDetailOfflineService;
import com.jdl.basic.provider.core.service.workStation.WorkGridFlowDirectionService;
import com.jdl.basic.provider.core.service.workStation.WorkGridService;
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
	@Autowired
	@Qualifier("workGridService")
	private WorkGridService workGridService;
	
	private static boolean initDataFlag = false;
	
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
	public Result<PageDto<WorkGridFlowDirectionVo>> queryFlowDataForSelect(WorkGridFlowDirectionQuery query) {
		return workGridFlowDirectionService.queryFlowDataForSelect(query);
	}
	@Override
	public Result<PageDto<WorkGridFlowDirection>> queryFlowData(WorkGridFlowDirectionQuery query) {
		return queryPageList(query);
	}
	@Override
	public Result<List<WorkGridFlowDirection>> queryListForExport(WorkGridFlowDirectionQuery query) {
		return workGridFlowDirectionService.queryListForExport(query);
	}
	@Override
	public Result<Long> queryCount(WorkGridFlowDirectionQuery query) {
		return workGridFlowDirectionService.queryCount(query);
	}
	@Override
	public void stopInit() {
		initDataFlag = false;
	}
	@Override
	public void initWorkGridFlowOffline() {
		initDataFlag = true;
		int pageNum = 1;
		WorkGridFlowDetailOfflineQuery query = new WorkGridFlowDetailOfflineQuery();
		List<WorkGridFlowDetailOffline> dataList = null;
		do {
			query.setDt(DateFormatUtils.format(DateHelper.addDays(new Date(), -1), DateHelper.DATE_FORMAT_YYYY_MM_DD));
			query.setPageNumber(pageNum);
			query.setPageSize(50);
			Result<PageDto<WorkGridFlowDetailOffline>> pageResult = workGridFlowDetailOfflineService.queryPageList(query);
			if(pageResult != null 
					&& pageResult.getData() != null ) {
				dataList = pageResult.getData().getResult();
			}
			if(dataList != null) {
				if((!CollectionUtils.isEmpty(dataList))) {
					for(WorkGridFlowDetailOffline data : dataList) {
						if(StringUtils.isNotBlank(data.getRefWorkGridKey())) {
							log.warn("refWorkGridKey值已存在，不处理！"+data.getId());
							continue;
						}
						if(initDataFlag) {
							WorkGrid queryGrid = new WorkGrid();
							if(data.getSiteCode() != null) {
								queryGrid.setSiteCode(Integer.parseInt(data.getSiteCode()));
							}
							queryGrid.setFloor(data.getFloor());
							queryGrid.setGridNo(data.getGridNo());
							queryGrid.setAreaCode(data.getAreaCode());
							Result<WorkGrid> gridResult = workGridService.queryByBusinessKeys(queryGrid);
							if(gridResult != null && gridResult.getData() != null) {
								log.warn("initWorkGridFlowOffline："+data.getId());
								data.setRefWorkGridKey(gridResult.getData().getBusinessKey());
								workGridFlowDetailOfflineService.updateRefWorkGridKeyById(data);
							}
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}else {
							log.warn("initAllWorkGrid-stop！"+data.getId());
							break;
						}
					}
				}
			}
			pageNum++;
		}while(initDataFlag && !CollectionUtils.isEmpty(dataList));	
		initDataFlag = false;
	}
}
