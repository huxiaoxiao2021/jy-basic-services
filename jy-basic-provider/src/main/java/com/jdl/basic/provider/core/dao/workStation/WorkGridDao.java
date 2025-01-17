package com.jdl.basic.provider.core.dao.workStation;

import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.common.contants.Constants;
import org.apache.ibatis.annotations.Param;

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
	 * 查询场地和作业区下的网格key列表
	 * @param workGridQuery
	 * @return
	 */
	List<String> queryGridKeyListBySiteAndArea(WorkGridQuery workGridQuery);
	/**
	 * 根据场地查询网格信息
	 * @param siteCodes
	 * @return
	 */
    List<WorkGrid> getGridInfoBySiteCodes(@Param("siteCodes") List<String> siteCodes);

	/**
	 * 根据网格唯一信息查询网格信息
	 * @param query
	 * @return
	 */
	List<WorkGrid> queryWorkGridByBizKey(WorkGrid query);
	/**
	 * 根据场地编码更新
	 */
	Boolean updateBySiteCode(WorkGrid workGrid);

	List<Integer> selectDistinctSiteCode();

	int batchUpdateByIds(WorkGridBatchUpdateRequest request);

	List<WorkGrid> batchQueryAreaWorkGrid(BatchAreaWorkGridQuery query);


	@Cache(key = Constants.QUERY_BY_WORKGRID_KEY_CACHE_KEY + "@args0",
			memoryEnable = false, redisExpiredTime = 24 * 60 * 60 * 1000)
	WorkGrid queryByWorkGridKeyWithCache(String workGridKey);

	List<WorkGrid> queryAreaInfo(WorkGrid queryParams);

    /**
     * 更新指定ID的网格状态
     * @param updateRequest 更新请求对象
     * @return 更新后的网格对象
     */
	int updateStatusByIds(UpdateRequest<WorkGrid> updateRequest);
    /**
     * 根据ID更新审批通过的网格
     * @param workGrid 工作网格对象
     */
	void updatePassById(WorkGrid workGrid);
}
