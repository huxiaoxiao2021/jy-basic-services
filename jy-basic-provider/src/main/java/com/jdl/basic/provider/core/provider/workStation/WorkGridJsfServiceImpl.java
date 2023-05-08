package com.jdl.basic.provider.core.provider.workStation;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.domain.workStation.WorkGridImport;
import com.jdl.basic.api.domain.workStation.WorkGridQuery;
import com.jdl.basic.api.domain.workStation.WorkGridVo;
import com.jdl.basic.api.service.workStation.WorkGridJsfService;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.config.lock.LockService;
import com.jdl.basic.provider.core.service.workStation.WorkGridService;
import com.jdl.basic.provider.hander.ResultHandler;

import lombok.extern.slf4j.Slf4j;

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
	public Result<Boolean> updateById(WorkGrid updateData){
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
}
