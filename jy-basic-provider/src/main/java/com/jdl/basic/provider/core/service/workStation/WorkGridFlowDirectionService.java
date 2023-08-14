package com.jdl.basic.provider.core.service.workStation;

import java.util.List;

import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirection;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionQuery;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionVo;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

/**
 * 场地网格流向表--Service接口
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
public interface WorkGridFlowDirectionService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkGridFlowDirection insertData);
	/**
	 * 批量插入
	 * @param flowListForAdd
	 * @return
	 */
	int batchInsert(List<WorkGridFlowDirection> flowListForAdd);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkGridFlowDirection updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkGridFlowDirection deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkGridFlowDirection> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkGridFlowDirection>> queryPageList(WorkGridFlowDirectionQuery query);
	/**
	 * 
	 * @param workGridKey
	 * @return
	 */
	List<WorkGridFlowDirection> queryListForWorkGridVo(WorkGridFlowDirectionQuery query);
	/**
	 * 批量删除逻辑
	 * @param deleteRequest
	 * @return
	 */
	Result<Boolean> deleteByIds(DeleteRequest<WorkGridFlowDirection> deleteRequest);
	/**
	 * 批量导入流向
	 * @param dataList
	 * @return
	 */
	Result<Boolean> importDatas(List<WorkGridFlowDirection> dataList);
	/**
	 * 查询流向站点列表
	 * @param query
	 * @return
	 */
	List<Integer> queryExistFlowSiteCodeList(WorkGridFlowDirectionQuery query);
	/**
	 * 查询流向-页面选择使用
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkGridFlowDirectionVo>> queryFlowDataForSelect(WorkGridFlowDirectionQuery query);
	
	Result<List<WorkGridFlowDirection>> queryListForExport(WorkGridFlowDirectionQuery query);
	Result<Long> queryCount(WorkGridFlowDirectionQuery query);
	/**
	 * 删除网格下的所有流向数据
	 * @param deleteData
	 * @return
	 */
	int deleteByRefGridKey(WorkGridFlowDirection deleteData);


	/**
	 * 查询流向-根据传入的流向
	 *
	 * @param query
	 * @return
	 */
	List<String> queryRefWorkGridKeyByFlowDirection(WorkGridFlowDirectionQuery query);
}
