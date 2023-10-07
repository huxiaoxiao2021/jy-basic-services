package com.jdl.basic.provider.core.service.workStation;

import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * 场地网格表--Service接口
 *
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
public interface WorkGridService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkGrid insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkGrid updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkGrid deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGrid> queryById(Long id);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGridVo> queryByIdForConfigFlow(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkGridVo>> queryPageList(WorkGridQuery query);
	/**
	 * 保存网格信息
	 * @param workGrid
	 * @return
	 */
	Result<WorkGrid> saveData(WorkGrid workGrid);
	/**
	 * 根据业务主键查询-业务主键：site_code、floor、grid_no、area_code
	 * @param workGrid
	 * @return
	 */
	Result<WorkGrid> queryByBusinessKeys(WorkGrid workGrid);
	/**
	 * 根据条件查询数量
	 * @param query
	 * @return
	 */
	Result<Long> queryCount(WorkGridQuery query);
	/**
	 * 根据条件查询列表-导出
	 * @param query
	 * @return
	 */
	Result<List<WorkGridVo>> queryListForExport(WorkGridQuery query);
	/**
	 * 批量删除
	 * @param deleteRequest
	 * @return
	 */
	Result<Boolean> deleteByIds(DeleteRequest<WorkGrid> deleteRequest);
	/**
	 * 批量导入
	 * @param dataList
	 * @return
	 */
	Result<Boolean> importDatas(List<WorkGridImport> dataList);
	/**
	 * 根据key查询
	 * @param workGridKey
	 * @return
	 */
	WorkGrid queryByWorkGridKey(String workGridKey);
	/**
	 * 获取-唯一业务主键字符串，多个字段组合结果
	 * @param data
	 * @return
	 */
	String getUniqueKeysStr(WorkGrid data);

	/**
	 * 查询场地网格的楼层列表信息
	 * @return
	 */
	List<WorkGrid> queryFloorDictList(WorkGrid queryParams);

	/**
	 * 查询场地网格的作业区信息
	 * @return
	 */
	List<WorkGrid> queryAreaDictList(WorkGrid queryParams);

	/**
	 * 查询场地网格信息
	 * @param queryParams
	 * @return
	 */
	List<WorkGrid> queryWorkGrid(WorkGrid queryParams);

	/**
	 * 根据网格主键批量查询网格
	 * @param workGridKeys
	 * @return
	 */
	List<WorkGrid> batchQueryByWorkGridKey(List<String> workGridKeys);

	List<WorkGrid> queryAreaWorkGrid(WorkGridQuery query);

	List<WorkGrid> queryAllGridBySiteCode(WorkGridQuery query);
	/**
	 * 查询作业区下的站点列表
	 * @param workGridQuery
	 * @return
	 */
	List<Integer> querySiteListForManagerScan(WorkGridQuery workGridQuery);
	/**
	 * 查询站点+作业区下的网格列表
	 * @param workGridQuery
	 * @return
	 */
	List<WorkGrid> queryListForManagerSiteScan(WorkGridQuery workGridQuery);
	/**
	 * 根据key删除一条记录
	 * @param workGridKey
	 * @return
	 */
	Result<Boolean> deleteByWorkGridKey(WorkGrid deleteData);

	/**
	 * 根据场地获取网格信息
	 * @param siteCodes
	 * @return
	 */
	List<WorkGrid> getGridInfoBySiteCodes(List<String> siteCodes);

	/**
	 * 根据唯一码查询网格信息
	 * @param query
	 * @return
	 */
    List<WorkGrid> queryWorkGridByBizKey(WorkGrid query);

	Result<Boolean> updateBySiteCode(WorkGrid workGrid);

	List<Integer> selectDistinctSiteCode();

	int batchUpdateByIds(WorkGridBatchUpdateRequest request);

	List<WorkGrid> batchQueryAreaWorkGrid(BatchAreaWorkGridQuery query);
}
