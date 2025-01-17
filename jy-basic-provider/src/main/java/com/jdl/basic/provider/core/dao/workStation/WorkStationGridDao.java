package com.jdl.basic.provider.core.dao.workStation;


import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.api.domain.workStation.WorkStationGridCountVo;
import com.jdl.basic.api.domain.workStation.WorkStationGridQuery;
import com.jdl.basic.api.domain.workStation.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工序岗位网格表--Dao接口
 * 
 * @author wuyoude
 * @date 2021年12月30日 14:30:43
 *
 */
public interface WorkStationGridDao {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	int insert(WorkStationGrid insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	int updateById(WorkStationGrid updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	int deleteById(WorkStationGrid deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WorkStationGrid queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryList(WorkStationGridQuery query);
	/**
	 * 按条件查询数量
	 * @param query
	 * @return
	 */
	long queryCount(WorkStationGridQuery query);
	/**
	 * 查询站点下所有网格
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryAllGridBySiteCode(WorkStationGridQuery query);
	/**
	 * 
	 * @param deleteData
	 */
	int deleteByBusinessKey(WorkStationGrid deleteData);
	/**
	 * 根据业务主键查询
	 * @param data
	 * @return
	 */
	WorkStationGrid queryByBusinessKey(WorkStationGrid data);
	/**
	 * 查询场地网格作业区列表
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryGridAreaDictList(WorkStationGridQuery query);
	/**
	 * 查询场地网格工序列表
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryGridWorkDictList(WorkStationGridQuery query);
	/**
	 * 查询场地网格序号列表
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryGridNoDictList(WorkStationGridQuery query);
	/**
	 * 查询场地网格楼层列表
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryGridFloorDictList(WorkStationGridQuery query);
	/**
	 * 按条件查询统计信息
	 * @param query
	 * @return
	 */
	WorkStationGridCountVo queryPageCount(WorkStationGridQuery query);
	/**
	 * 根据refStationKey 查询数据记录条数
	 * @param stationKey
	 * @return
	 */
	long queryCountByRefStationKey(String stationKey);

	/**
	 * 分页查询所有数据（用于初始化数据）
	 *
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryAllByPage(WorkStationGridQuery query);
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
	 * 导出查询列表
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryListForExport(WorkStationGridQuery query);
	/**
	 * 根据id列表查询
	 * @param deleteRequest
	 * @return
	 */
	List<WorkStationGrid> queryByIds(DeleteRequest<WorkStationGrid> deleteRequest);
	/**
	 * 根据id批量删除
	 * @param deleteRequest
	 * @return
	 */
	int deleteByIds(DeleteRequest<WorkStationGrid> deleteRequest);
	/**
	 * 根据
	 * @param workStationGridQuery
	 * @return
	 */
	WorkStationGrid queryByGridKey(WorkStationGridQuery workStationGridQuery);

	/**
	 * 去重查询
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryListDistinct(WorkStationFloorGridQuery query);
	/**
	 * 去重查询求总数
	 * @param query
	 * @return
	 */
	Long queryDistinctCount(WorkStationFloorGridQuery query);


	/**
	 * 根据businessKey查询 单条数据
	 * @param businessKey
	 * @return
	 */
	@Cache(key = "WorkStationGridDao.queryWorkStationGridBybusinessKeyWithCache@args0", memoryEnable = false,
			redisEnable = true, redisExpiredTime = 5 * 60 * 1000)
	WorkStationGrid queryWorkStationGridBybusinessKeyWithCache(String businessKey);
	/**
	 * 查询场地网格工序列表
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryListForWorkGridVo(WorkStationGridQuery query);
	/**
	 * 查询作业区下站点列表
	 * @param query
	 * @return
	 */
	List<Integer> querySiteListForManagerScan(WorkStationGridQuery query);
	/**
	 * 查询场地、作业区下的网格列表
	 * @param workStationGridQuery
	 * @return
	 */	
	List<WorkStationGrid> queryListForManagerSiteScan(WorkStationGridQuery query);

	/**
	 * 刷数-分页查询
	 * @param startId
	 * @return
	 */
	List<WorkStationGrid> brushQueryAllByPage(Integer startId);

	/**
	 * 刷数-批量更新
	 * @param list
	 * @return
	 */
	Integer brushUpdateById(List<WorkStationGrid> list);
	/**
	 * 查询网格下的数据条数
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
	 * 根据场地ID获取网格信息
	 * @param siteCodes
	 * @return
	 */
    List<WorkStationGrid> getGridInfoBySiteCodes(List<String> siteCodes);
	
	int syncWorkGridInfo(WorkStationGrid updateData);

	/**
	 * 查询场地下工序
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryWorkStationGridBySiteCode(WorkStationGridQuery query);

	List<String> queryBusinessKeyByRefWorkGridKeys(@Param("refWorkGridKeys") List<String> refWorkGridKeys);

    /**
     * 根据指定的ID列表更新工作站网格信息
     * @param updateRequest 更新请求对象，包含要更新的工作站网格信息的ID列表
     * @return 更新操作的响应对象
     */
	int updateStatusByIds(UpdateRequest<WorkStationGrid> updateRequest);
    /**
     * 更新指定审批通过网格工序
     * @param updateRequest 更新请求对象，包含要更新的工作站网格信息
     * @return 更新后的工作站网格信息
     */
	int updatePassById(WorkStationGrid updateRequest);
	/**
	 * 按条件查询历史删除数量
	 * @param query
	 * @return
	 */
	long queryHistoryCount(WorkStationGridQuery query);

	/**
	 * 按条件分页查询历史删除记录
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryHistoryList(WorkStationGridQuery query);

	/**
	 * 关联场地网格批量查询
	 *
	 * @param query
	 * @return
	 */
	List<WorkStationGrid> queryListForRefWorkGridKeyList(WorkStationGridQuery query);

    /**
     * 根据参考站点键查询工作站点网格列表
     * @param refStationKey 参考站点的键，用于查询与之关联的工作站点网格
     * @return 返回与参考站点键相关联的工作站点网格列表
     */
	List<WorkStationGrid> queryListByRefStationKey(String refStationKey);

    /**
     * 根据ID批量更新标准数量
     * @param updateRequest 更新请求，包含工作站网格的列表和需要更新的标准数量
     * @return 更新的记录数，表示成功更新的工作站网格数量
     */
	int updateStandardNumByIds(UpdateRequest<WorkStationGrid> updateRequest);
}
