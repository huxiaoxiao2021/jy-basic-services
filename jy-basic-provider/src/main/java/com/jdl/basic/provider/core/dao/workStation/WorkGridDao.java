package com.jdl.basic.provider.core.dao.workStation;

import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.domain.workStation.WorkGridBatchUpdateRequest;
import com.jdl.basic.api.domain.workStation.WorkGridQuery;

import java.util.List;

/**
 * 场地网格表--Dao接口
 *
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
public interface WorkGridDao {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	int insert(WorkGrid insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	int updateById(WorkGrid updateData);
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	int deleteById(WorkGrid deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WorkGrid queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	List<WorkGrid> queryList(WorkGridQuery query);
	/**
	 * 按条件查询数量
	 * @param query
	 * @return
	 */
	long queryCount(WorkGridQuery query);
	/**
	 * 根据业务主键查询
	 * @param workGrid
	 * @return
	 */
	WorkGrid queryByBusinessKeys(WorkGrid workGrid);
	/**
	 * 根据id列表查询
	 * @param workGrid
	 * @return
	 */
	List<WorkGrid> queryByIds(DeleteRequest<WorkGrid> deleteRequest);
	/**
	 * 根据id列表删除
	 * @param deleteRequest
	 * @return
	 */
	int deleteByIds(DeleteRequest<WorkGrid> deleteRequest);
	/**
	 * 根据workGridKey查询
	 * @param workGridKey
	 * @return
	 */
	WorkGrid queryByWorkGridKey(String workGridKey);

  List<WorkGrid> queryFloorDictList(WorkGrid queryParams);

	List<WorkGrid> queryAreaDictList(WorkGrid queryParams);

	List<WorkGrid> queryWorkGrid(WorkGrid queryParams);

	List<WorkGrid> batchQueryByWorkGridKey(List<String> workGridKeys);

	List<WorkGrid> queryAreaWorkGrid(WorkGridQuery query);

	List<WorkGrid> queryAllGridBySiteCode(WorkGridQuery query);
	
	List<Integer> querySiteListForManagerScan(WorkGridQuery workGridQuery);
	
	List<WorkGrid> queryListForManagerSiteScan(WorkGridQuery workGridQuery);
	/**
	 * 刷数-分页查询
	 * @param startId
	 * @return
	 */
	List<WorkGrid> brushQueryAllByPage(Integer startId);

	/**
	 * 刷数-批量更新
	 * @param list
	 * @return
	 */
	Integer brushUpdateById(List<WorkGrid> list);

	/**
	 * 根据场地编码更新
	 */
	Boolean updateBySiteCode(WorkGrid workGrid);

	List<Integer> selectDistinctSiteCode();

	int batchUpdateByIds(WorkGridBatchUpdateRequest request);
}
