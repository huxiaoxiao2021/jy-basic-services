package com.jdl.basic.provider.core.service.workStation;


import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.po.WorkStationJobTypePO;

import java.util.List;

/**
 * @ClassName: WorkStationService
 * @Description: 网格工序信息表--Service接口
 * @author wuyoude
 * @date 2022年02月23日 11:01:53
 *
 */
public interface WorkStationService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkStation insertData);
	/**
	 * 导入数据
	 * @param dataList
	 * @return
	 */
	Result<Boolean> importDatas(List<WorkStation> dataList);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkStation updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkStation deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkStation> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkStation>> queryPageList(WorkStationQuery query);
	/**
	 * 查询作业区字典
	 * @return
	 */
	Result<List<WorkStation>> queryAreaDictList(WorkStationQuery query);
	/**
	 * 查询工序字典
	 * @return
	 */
	Result<List<WorkStation>> queryWorkDictList(WorkStationQuery query);
	/**
	 * 根据业务主键查询
	 * @param data
	 * @return
	 */
	Result<WorkStation> queryByBusinessKey(WorkStation data);
	/**
	 * 根据业务主键查询-缓存结果
	 * @param data
	 * @return
	 */
	WorkStation queryByBusinessKeyWithCache(WorkStation data);

	/**
	 * 根据业务主键查询
	 * @param businessKey
	 * @return
	 */
	Result<WorkStation> queryByRealBusinessKey(String businessKey);
	/**
	 * 根据业务主键查询
	 * @param businessKey
	 * @return
	 */
	WorkStation queryByRealBusinessKeyWithCache(String businessKey);
	/**
	 * 根据业务主键判断是否存在
	 * @param data
	 * @return
	 */
	boolean isExist(WorkStation data);
	/**
	 * 按条件查询统计信息
	 * @param query
	 * @return
	 */
	Result<WorkStationCountVo> queryPageCount(WorkStationQuery query);
	/**
	 * 批量删除
	 * @param deleteRequest
	 * @return
	 */
	Result<Boolean> deleteByIds(DeleteRequest<WorkStation> deleteRequest);
	/**
	 * 查询数量
	 * @param query
	 * @return
	 */
	Result<Long> queryCount(WorkStationQuery query);
	/**
	 * 查询列表导出
	 * @param query
	 * @return
	 */
	Result<List<WorkStation>> queryListForExport(WorkStationQuery query);


	/**
	 * 根据businessKey查询 单条数据
	 * @param businessKey
	 * @return
	 */
	Result<WorkStation> queryWorkStationBybusinessKeyWithCache(String businessKey);

	/**
	 * 根据businessKey查询 网格工种
	 * @param businessKey
	 * @return
	 */
	Result<List<WorkStationJobTypeDto>> queryWorkStationJobTypeBybusinessKey(String businessKey);
	/**
	 * 初始化-所有数据
	 */
	void initAllWorkArea();
	/**
	 * 初始化-单条数据
	 */
	void initWorkArea(Long id);
}
