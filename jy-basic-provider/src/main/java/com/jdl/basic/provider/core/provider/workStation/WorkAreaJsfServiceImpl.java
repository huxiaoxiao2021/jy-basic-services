package com.jdl.basic.provider.core.provider.workStation;


import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.PageDto;

import com.jdl.basic.api.domain.workStation.WorkArea;
import com.jdl.basic.api.domain.workStation.WorkAreaQuery;
import com.jdl.basic.provider.core.service.workStation.WorkAreaService;
import com.jdl.basic.api.service.workStation.WorkAreaJsfService;

/**
 * 作业区信息表--JsfService接口实现
 *
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Slf4j
@Service("workAreaJsfService")
public class WorkAreaJsfServiceImpl implements WorkAreaJsfService {

	@Autowired
	@Qualifier("workAreaService")
	private WorkAreaService workAreaService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkArea insertData){
		return workAreaService.insert(insertData);
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkArea updateData){
		return workAreaService.updateById(updateData);
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkArea deleteData){
		return workAreaService.deleteById(deleteData);
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkArea> queryById(Long id){
		return workAreaService.queryById(id);
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkArea>> queryPageList(WorkAreaQuery query){
		return workAreaService.queryPageList(query);
	 }

	@Override
	public Result<WorkArea> queryByAreaCode(String areaCode) {
		return Result.success(workAreaService.queryByAreaCode(areaCode));
	}

	@Override
	public Result<List<WorkArea>> listAreaByLabel(WorkArea workArea) {
		return Result.success(workAreaService.listAreaByLabel(workArea));
	}
}
