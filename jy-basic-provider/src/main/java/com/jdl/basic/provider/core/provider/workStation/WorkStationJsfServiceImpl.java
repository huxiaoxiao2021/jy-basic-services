package com.jdl.basic.provider.core.provider.workStation;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkStation;
import com.jdl.basic.api.domain.workStation.WorkStationCountVo;
import com.jdl.basic.api.domain.workStation.WorkStationQuery;
import com.jdl.basic.api.service.workStation.WorkStationJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.workStation.WorkStationService;
import lombok.extern.slf4j.Slf4j;
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

	private static final Logger logger = LoggerFactory.getLogger(WorkStationJsfServiceImpl.class);

	@Autowired
	@Qualifier("workStationService")
	private WorkStationService workStationService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkStation insertData){
		log.info("网格工序管理 insert 入参-{}", JSON.toJSONString(insertData));
		return workStationService.insert(insertData);
	 }
	@Override
	public Result<Boolean> importDatas(List<WorkStation> dataList) {
		return workStationService.importDatas(dataList);
	}	
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkStation updateData){
		logger.info("网格工序管理 updateById 入参-{}", JSON.toJSONString(updateData));
		return workStationService.updateById(updateData);
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkStation deleteData){
		log.info("网格工序管理 deleteById 入参-{}", JSON.toJSONString(deleteData));
		return workStationService.deleteById(deleteData);
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
			logger.info("网格工序管理 queryPageCount 入参-{}", JSON.toJSONString(query));
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
	public Result<Boolean> deleteByIds(DeleteRequest<WorkStation> deleteRequest) {
		log.info("网格工序管理 deleteByIds 入参-{}", JSON.toJSONString(deleteRequest));
		return workStationService.deleteByIds(deleteRequest);
	}
	@Override
	public Result<Long> queryCount(WorkStationQuery query) {
		if(log.isInfoEnabled()) {
			logger.info("网格工序管理 queryCount 入参-{}", JSON.toJSONString(query));
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
		return workStationService.queryByBusinessKey(data);
	}

	@Override
	public Result<WorkStation> queryByRealBusinessKey(String businessKey) {
		if(log.isInfoEnabled()) {
			log.info("网格工序管理 queryByRealBusinessKey 入参-{}", JSON.toJSONString(businessKey));
		}
		return workStationService.queryByRealBusinessKey(businessKey);
	}
}
