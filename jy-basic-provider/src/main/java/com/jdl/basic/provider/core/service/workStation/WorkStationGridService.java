package com.jdl.basic.provider.core.service.workStation;


import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

import java.util.List;


/**
 * 工序岗位网格表--Service接口
 *
 * @author wuyoude
 * @date 2021年12月30日 14:30:43
 *
 */
public interface WorkStationGridService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkStationGrid insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkStationGrid updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkStationGrid deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkStationGrid> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkStationGrid>> queryPageList(WorkStationGridQuery query);
	/**
	 * 查询站点下所有网格
	 * @param query
	 * @return
	 */
	Result<List<WorkStationGrid>> queryAllGridBySiteCode(WorkStationGridQuery query);
	/**
	 * 导入数据
	 * @param dataList
	 * @return
	 */
	Result<Boolean> importDatas(List<WorkStationGrid> dataList);
	/**
	 * 根据业务主键查询
	 * @param data
	 * @return
	 */
	Result<WorkStationGrid> queryByBusinessKey(WorkStationGrid data);
	/**
	 * 根据业务主键查询-缓存结果
	 * @param data
	 * @return
	 */
	WorkStationGrid queryByBusinessKeyWithCache(WorkStationGrid data);
	/**
	 * 根据业务主键判断是否存在
	 * @param data
	 * @return
	 */
	boolean isExist(WorkStationGrid data);
	/**
	 * 查询场地网格作业区列表
	 * @param query
	 * @return
	 */
	Result<List<WorkStationGrid>> queryGridAreaDictList(WorkStationGridQuery query);
	/**
	 * 查询场地网格工序列表
	 * @param query
	 * @return
	 */
	Result<List<WorkStationGrid>> queryGridWorkDictList(WorkStationGridQuery query);
	/**
	 * 查询场地网格序号列表
	 * @param query
	 * @return
	 */
	Result<List<WorkStationGrid>> queryGridNoDictList(WorkStationGridQuery query);
	/**
	 * 查询场地网格楼层列表
	 * @param query
	 * @return
	 */
	Result<List<WorkStationGrid>> queryGridFloorDictList(WorkStationGridQuery query);
	/**
	 * 按条件查询统计信息
	 * @param query
	 * @return
	 */
	Result<WorkStationGridCountVo> queryPageCount(WorkStationGridQuery query);
	/**
	 *
	 * @param stationKey
	 * @return
	 */
	boolean hasGridData(String stationKey);
	/**
	 * 批量删除
	 * @param deleteRequest
	 * @return
	 */
	Result<Boolean> deleteByIds(DeleteRequest<WorkStationGrid> deleteRequest);
	/**
	 * 查询数量
	 * @param query
	 * @return
	 */
	Result<Long> queryCount(WorkStationGridQuery query);
	/**
	 * 查询列表导出
	 * @param query
	 * @return
	 */
	Result<List<WorkStationGrid>> queryListForExport(WorkStationGridQuery query);
	/**
	 * 查询有网格的站点列表
	 * @param query
	 */
	List<WorkStationGrid> querySiteListByOrgCode(WorkStationGridQuery query);
	/**
	 * 查询erp列表
	 * @param query
	 */
	List<String> queryOwnerUserErpListBySiteCode(WorkStationGridQuery query);
	/**
	 * 根据gridKey查询
	 * @param workStationGridCheckQuery
	 * @return
	 */
	WorkStationGrid queryByGridKeyWithCache(WorkStationGridQuery workStationGridCheckQuery);

	Result<WorkStationGrid> queryWorkStationGridBybusinessKeyWithCache(String businessKey);

    Result<PageDto<WorkStationGrid>> queryAllWorkGridList(WorkStationGridQuery query);
    /**
     * 停止数据初始化
     */
    void stopInit();
    /**
	 * 初始化-所有数据
	 */
	void initAllWorkGrid();
	/**
	 * 初始化-单条数据
	 */
	void initWorkGrid(Long id);
	/**
	 * 查询岗位工序列表
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryListForWorkGridVo(WorkStationGridQuery query);
	/**
	 * 查询作业区下的站点列表
	 * @param workStationGridQuery
	 * @return
	 */
	List<Integer> querySiteListForManagerScan(WorkStationGridQuery workStationGridQuery);
	/**
	 * 查询场地、作业区下的网格列表
	 * @param workStationGridQuery
	 * @return
	 */	
	List<WorkStationGrid> queryListForManagerSiteScan(WorkStationGridQuery workStationGridQuery);
	/**
	 *
	 * @param refGridKey
	 * @return
	 */
	int queryCountByRefGridKey(String refGridKey);

	/**
	 * 获取月台号
	 *
	 * @param workGridFlowDirectionQuery
	 */
	List<String> queryDockCodeByFlowDirection(WorkGridFlowDirectionQuery workGridFlowDirectionQuery);

	/**
	 * 获取网格信息
	 *
	 * @param workGridFlowDirectionQuery
	 */
	List<WorkStationGrid> queryPhoneByDockCodeForTms(WorkGridFlowDirectionQuery workGridFlowDirectionQuery);
	/**
	 * 同步更新网格下所有工序信息
	 * @param updateData
	 * @return
	 */
	int syncWorkGridInfo(WorkGrid gridData);


	/**
	 * 查询场地下工序
	 * @param query
	 * @return
	 */
	Result<List<WorkStationGrid>> queryWorkStationGridBySiteCode(WorkStationGridQuery query);



    List<String> queryBusinessKeyByRefWorkGridKeys(List<String> refWorkGridKeys);
	/**
	 * 根据指定的ID列表更新工作站网格信息
	 * @param updateRequest 更新请求对象，包含要更新的工作站网格信息的ID列表
	 * @return 更新操作的响应对象
	 */
	Result<Boolean> updateStatusByIds(UpdateRequest<WorkStationGrid> updateRequest);
	/**
	 * 查询历史删除记录页面列表
	 * @param query 工作站网格查询
	 * @return 历史页面列表的分页数据传输对象
	 */
	Result<PageDto<WorkStationGrid>> queryHistoryPageList(WorkStationGridQuery query);

	/**
	 * 更新审批通过的工作站网格工序信息
	 * @param updateRequest 更新请求对象
	 * @return 更新后的工作站网格信息
	 */
	Result<Boolean> updatePassByIds(UpdateRequest<WorkStationGrid> updateRequest);
	/**
	 * 关联场地网格批量查询
	 *
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryListForRefWorkGridKeyList(WorkStationGridQuery query);
}
