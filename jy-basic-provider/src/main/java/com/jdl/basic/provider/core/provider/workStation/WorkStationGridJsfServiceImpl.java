package com.jdl.basic.provider.core.provider.workStation;


import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.api.domain.workStation.WorkStationGridCountVo;
import com.jdl.basic.api.domain.workStation.WorkStationGridQuery;
import com.jdl.basic.api.service.workStation.WorkStationGridJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.workStation.WorkStationGridService;
import lombok.extern.slf4j.Slf4j;
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

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkStationGrid insertData){
		log.info("场地网格工序管理 insert 入参-{}", JSON.toJSONString(insertData));
		return workStationGridService.insert(insertData);
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkStationGrid updateData){
		log.info("场地网格工序管理 updateById 入参-{}", JSON.toJSONString(updateData));
		return workStationGridService.updateById(updateData);
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkStationGrid deleteData){
		log.info("场地网格工序管理 deleteById 入参-{}", JSON.toJSONString(deleteData));
		return workStationGridService.deleteById(deleteData);
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
		log.info("场地网格工序管理 queryPageList 入参-{}", JSON.toJSONString(query));
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
		log.info("场地网格工序管理 queryAllGridBySiteCode 入参-{}", JSON.toJSONString(query));

		return workStationGridService.queryAllGridBySiteCode(query);
	}
	@Override
	public Result<Boolean> importDatas(List<WorkStationGrid> dataList) {
		return workStationGridService.importDatas(dataList);
	}
	@Override
	public Result<List<WorkStationGrid>> queryGridWorkDictList(WorkStationGridQuery query) {
		log.info("场地网格工序管理 queryGridWorkDictList 入参-{}", JSON.toJSONString(query));
		return workStationGridService.queryGridWorkDictList(query);
	}
	@Override
	public Result<List<WorkStationGrid>> queryGridAreaDictList(WorkStationGridQuery query) {
		log.info("场地网格工序管理 queryGridAreaDictList 入参-{}", JSON.toJSONString(query));
		return workStationGridService.queryGridAreaDictList(query);
	}
	@Override
	public Result<List<WorkStationGrid>> queryGridNoDictList(WorkStationGridQuery query) {
		log.info("场地网格工序管理 queryGridNoDictList 入参-{}", JSON.toJSONString(query));
		return workStationGridService.queryGridNoDictList(query);
	}
	@Override
	public Result<List<WorkStationGrid>> queryGridFloorDictList(WorkStationGridQuery query) {
		log.info("场地网格工序管理 queryGridFloorDictList 入参-{}", JSON.toJSONString(query));
		return workStationGridService.queryGridFloorDictList(query);
	}
	@Override
	public Result<Boolean> deleteByIds(DeleteRequest<WorkStationGrid> deleteRequest) {
		log.info("场地网格工序管理 deleteByIds 入参-{}", JSON.toJSONString(deleteRequest));
		return workStationGridService.deleteByIds(deleteRequest);
	}
	@Override
	public Result<Long> queryCount(WorkStationGridQuery query) {
		log.info("场地网格工序管理 queryCount 入参-{}", JSON.toJSONString(query));
		return workStationGridService.queryCount(query);
	}
	@Override
	public Result<List<WorkStationGrid>> queryListForExport(WorkStationGridQuery query) {
		log.info("场地网格工序管理 queryListForExport 入参-{}", JSON.toJSONString(query));
		return workStationGridService.queryListForExport(query);
	}
	@Override
	public List<String> queryOwnerUserErpListBySiteCode(WorkStationGridQuery query) {
		log.info("场地网格工序管理 query 入参-{}", JSON.toJSONString(query));
		return workStationGridService.queryOwnerUserErpListBySiteCode(query);
	}
	@Override
	public List<WorkStationGrid> querySiteListByOrgCode(WorkStationGridQuery query) {
		log.info("场地网格工序管理 querySiteListByOrgCode 入参-{}", JSON.toJSONString(query));
		return workStationGridService.querySiteListByOrgCode(query);
	}

	@Override
	public Result<WorkStationGrid> queryByBusinessKey(WorkStationGrid data) {
		log.info("场地网格工序管理 queryByBusinessKey 入参-{}", JSON.toJSONString(data));
		return workStationGridService.queryByBusinessKey(data);
	}

	@Override
	public Result<WorkStationGrid> queryByGridKey(WorkStationGridQuery workStationGridCheckQuery) {
		log.info("场地网格工序管理 queryByGridKey 入参-{}", JSON.toJSONString(workStationGridCheckQuery));
		return workStationGridService.queryByGridKey(workStationGridCheckQuery);
	}
}
