package com.jdl.basic.provider.core.dao.workStation;

import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirection;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionQuery;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionVo;

import java.util.List;

/**
 * 场地网格流向表--Dao接口
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
public interface WorkGridFlowDirectionDao {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	int insert(WorkGridFlowDirection insertData);
	/**
	 * 插入数据列表
	 * @param insertData
	 * @return
	 */
	int batchInsert(List<WorkGridFlowDirection> flowList);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	int updateById(WorkGridFlowDirection updateData);
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	int deleteById(WorkGridFlowDirection deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WorkGridFlowDirection queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	List<WorkGridFlowDirection> queryList(WorkGridFlowDirectionQuery query);
	/**
	 * 按条件查询数量
	 * @param query
	 * @return
	 */
	long queryCount(WorkGridFlowDirectionQuery query);
	/**
	 * 根据workGridKey查询数据列表
	 * @param workGridKey
	 * @return
	 */
	List<WorkGridFlowDirection> queryListForWorkGridVo(WorkGridFlowDirectionQuery query);
	/**
	 * 根据id列表查询
	 * @param deleteRequest
	 * @return
	 */
	List<WorkGridFlowDirection> queryByIds(DeleteRequest<WorkGridFlowDirection> deleteRequest);
	/**
	 * 根据id列表删除
	 * @param deleteRequest
	 * @return
	 */
	int deleteByIds(DeleteRequest<WorkGridFlowDirection> deleteRequest);
	/**
	 * 查询流向列表
	 * @param query
	 * @return
	 */
	List<Integer> queryExistFlowSiteCodeList(WorkGridFlowDirectionQuery query);
	/**
	 * 选择流向-查询数量
	 * @param query
	 * @return
	 */
	long queryFlowDataForSelectCount(WorkGridFlowDirectionQuery query);
	/**
	 * 选择流向-查询数据
	 * @param query
	 * @return
	 */
	List<WorkGridFlowDirectionVo> queryFlowDataForSelect(WorkGridFlowDirectionQuery query);
	/**
	 * 选择流向-查询路由推荐未配置的流向
	 * @param query
	 * @return
	 */
	List<WorkGridFlowDirectionVo> queryFlowDataForSelect1(WorkGridFlowDirectionQuery query);	
	long queryFlowDataForSelectCount1(WorkGridFlowDirectionQuery query);
	/**
	 * 选择流向-查询路由未推荐已配置的流向
	 * @param query
	 * @return
	 */
	List<WorkGridFlowDirectionVo> queryFlowDataForSelect2(WorkGridFlowDirectionQuery query);
	long queryFlowDataForSelectCount2(WorkGridFlowDirectionQuery query);
	/**
	 * 选择流向-查询路由推荐已配置的流向
	 * @param query
	 * @return
	 */
	List<WorkGridFlowDirectionVo> queryFlowDataForSelect3(WorkGridFlowDirectionQuery query);	
	long queryFlowDataForSelectCount3(WorkGridFlowDirectionQuery query);

	/**
	 * 刷数-分页查询
	 * @param startId
	 * @return
	 */
	List<WorkGridFlowDirection> brushQueryAllByPage(Integer startId);

	/**
	 * 刷数-批量更新
	 * @param list
	 * @return
	 */
	Integer brushUpdateById(List<WorkGridFlowDirection> list);
}
