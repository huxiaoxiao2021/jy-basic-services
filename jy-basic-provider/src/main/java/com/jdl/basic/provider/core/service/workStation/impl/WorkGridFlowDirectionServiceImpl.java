package com.jdl.basic.provider.core.service.workStation.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirection;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionQuery;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionVo;
import com.jdl.basic.api.domain.workStation.WorkGridQuery;
import com.jdl.basic.api.enums.ConfigFlowStatusEnum;
import com.jdl.basic.api.enums.FlowSiteUseStatusEnum;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.enums.AreaEnum;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.workStation.WorkGridFlowDirectionDao;
import com.jdl.basic.provider.core.service.workStation.WorkGridFlowDirectionService;
import com.jdl.basic.provider.core.service.workStation.WorkGridService;
import com.jdl.basic.rpc.Rpc.BaseMajorRpc;

import lombok.extern.slf4j.Slf4j;

/**
 * 场地网格流向表--Service接口实现
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Slf4j
@Service("workGridFlowDirectionService")
public class WorkGridFlowDirectionServiceImpl implements WorkGridFlowDirectionService {

	@Autowired
	@Qualifier("workGridFlowDirectionDao")
	private WorkGridFlowDirectionDao workGridFlowDirectionDao;
	
	@Autowired
	@Qualifier("workGridService")
	private WorkGridService workGridService;
	
	@Autowired
	private BaseMajorRpc baseMajorManager;
	
	@Value("${beans.workGridFlowDirectionService.importDatasLimit:100}")
	private int importDatasLimit;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridFlowDirection insertData){
		Result<Boolean> result = Result.success(); 
		if(insertData == null) {
			return result.toFail("传入数据为空！");
		}
		WorkGrid gridData = workGridService.queryByWorkGridKey(insertData.getRefWorkGridKey());
		if(gridData == null) {
			return result.toFail("网格数据无效，请重新进入配置页面！");
		}
		List<Integer> flowSiteCodes = new ArrayList<>();
		flowSiteCodes.add(insertData.getFlowSiteCode());
		result = checkAndFillNewData(insertData,gridData);
		if(!result.isSuccess()) {
			return result;
		}
		WorkGridFlowDirectionQuery query = new WorkGridFlowDirectionQuery();
		query.setFlowDirectionType(insertData.getFlowDirectionType());
		query.setRefWorkGridKey(insertData.getRefWorkGridKey());
		query.setFlowSiteCodeList(flowSiteCodes);
		query.setLineType(insertData.getLineType());
		List<Integer> flowSiteCodesExist = this.queryExistFlowSiteCodeList(query);
		//todo- contains
		if(flowSiteCodesExist.contains(insertData.getFlowSiteCode())) {
			return result.toFail("添加失败，流向站点已存在！");
		}
		//更新-配置状态
		WorkGrid updateData = new WorkGrid();
		updateData.setId(gridData.getId());
		updateData.setConfigFlowStatus(ConfigFlowStatusEnum.CONFIG.getCode());
		updateData.setConfigFlowUser(gridData.getConfigFlowUser());
		updateData.setConfigFlowTime(new Date());
		workGridService.updateById(updateData);
		result.setData(workGridFlowDirectionDao.insert(insertData) == 1);
		return result;
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridFlowDirection updateData){
		Result<Boolean> result = Result.success();
		result.setData(workGridFlowDirectionDao.updateById(updateData) == 1);
		return result;
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGridFlowDirection deleteData){
		Result<Boolean> result = Result.success();
		result.setData(workGridFlowDirectionDao.deleteById(deleteData) == 1);
		return result;
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridFlowDirection> queryById(Long id){
		Result<WorkGridFlowDirection> result = Result.success();
		result.setData(fillWorkGridFlowDirection(workGridFlowDirectionDao.queryById(id)));
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridFlowDirection>> queryPageList(WorkGridFlowDirectionQuery query){
		Result<PageDto<WorkGridFlowDirection>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkGridFlowDirection> voDataList = new ArrayList<WorkGridFlowDirection>();
		PageDto<WorkGridFlowDirection> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workGridFlowDirectionDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
		    List<WorkGridFlowDirection> dataList = workGridFlowDirectionDao.queryList(query);
		    for (WorkGridFlowDirection tmp : dataList) {
		    	voDataList.add(this.fillWorkGridFlowDirection(tmp));
		    }
		}
		pageDto.setResult(voDataList);
		pageDto.setTotalRow(totalCount.intValue());
		result.setData(pageDto);
		return result;
	 }
	/**
	 * 查询参数校验
	 * @param query
	 * @return
	 */
	public Result<Boolean> checkParamForQueryPageList(WorkGridFlowDirectionQuery query){
		Result<Boolean> result = Result.success();
		if(query.getPageSize() == null || query.getPageSize() <= 0) {
			query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
		};
		query.setOffset(0);
		query.setLimit(query.getPageSize());
		if(query.getPageSize() == null || query.getPageNumber() > 0) {
			query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
		};
		return result;
	 }
	/**
	 * 对象转换成vo
	 * @param data
	 * @return
	 */
	public WorkGridFlowDirection fillWorkGridFlowDirection(WorkGridFlowDirection data){
		if(data == null) {
			return null;
		}
		//特殊字段设置
		return data;
	 }
	@Override
	public List<WorkGridFlowDirection> queryListForWorkGridVo(WorkGridFlowDirectionQuery query) {
		return workGridFlowDirectionDao.queryListForWorkGridVo(query);
	}
	@Override
	public Result<Boolean> deleteByIds(DeleteRequest<WorkGridFlowDirection> deleteRequest) {
		Result<Boolean> result = Result.success();
		if(deleteRequest == null
				|| CollectionUtils.isEmpty(deleteRequest.getDataList())) {
			return result.toFail("参数错误，删除列表不能为空！");
		}
		List<WorkGridFlowDirection> oldDataList = workGridFlowDirectionDao.queryByIds(deleteRequest);
		if(CollectionUtils.isEmpty(oldDataList)
				|| oldDataList.size() < deleteRequest.getDataList().size()) {
			return result.toFail("参数错误，数据已变更请刷新列表后重新选择！");
		}
//		for(WorkGridFlowDirection oldData : oldDataList) {
//			if(!ObjectUtils.equals(oldData.getSiteCode(), deleteRequest.getOperateSiteCode())) {
//				return result.toFail("非本人所在场地数据，无法删除！");
//			}
//		}
		result.setData(workGridFlowDirectionDao.deleteByIds(deleteRequest) > 0);
		return result;
	}
	@Override
	public Result<Boolean> importDatas(List<WorkGridFlowDirection> flowList) {
		Result<Boolean> result = Result.success(); 
		if(CollectionUtils.isEmpty(flowList)) {
			return result.toFail("批量添加流向数据为空！");
		}
		if(flowList.size() > importDatasLimit) {
			return result.toFail("批量添加流向数据不能超过"+importDatasLimit+"条！");
		}
		WorkGridFlowDirection data0 = flowList.get(0);
		WorkGrid gridData = workGridService.queryByWorkGridKey(data0.getRefWorkGridKey());
		if(gridData == null) {
			return result.toFail("网格数据无效，请重新进入配置页面！");
		}
		List<WorkGridFlowDirection> flowListForAdd = new ArrayList<>();
		List<Integer> flowSiteCodes = new ArrayList<>();
		for(WorkGridFlowDirection flowData: flowList) {
			flowSiteCodes.add(flowData.getFlowSiteCode());
		}
		result = checkAndFillImportDatas(flowList,gridData);
		if(!result.isSuccess()) {
			return result;
		}
		WorkGridFlowDirectionQuery query = new WorkGridFlowDirectionQuery();
		query.setFlowDirectionType(data0.getFlowDirectionType());
		query.setRefWorkGridKey(data0.getRefWorkGridKey());
		query.setFlowSiteCodeList(flowSiteCodes);
		query.setLineType(data0.getLineType());
		List<Integer> flowSiteCodesExist = this.queryExistFlowSiteCodeList(query);
		for(WorkGridFlowDirection flowData: flowList) {
			if(!flowSiteCodesExist.contains(flowData.getFlowSiteCode())) {
				flowListForAdd.add(flowData);
			}
		}
		//更新-配置状态
		WorkGrid updateData = new WorkGrid();
		updateData.setId(gridData.getId());
		updateData.setConfigFlowStatus(ConfigFlowStatusEnum.CONFIG.getCode());
		updateData.setConfigFlowUser(gridData.getConfigFlowUser());
		updateData.setConfigFlowTime(new Date());
		workGridService.updateById(updateData);
		this.batchInsert(flowListForAdd);
		result.setMessage("操作成功！本次新增"+flowListForAdd.size()+"个流向");
		return result;
	}
	/**
	 * 校验并填充导入数据
	 * @param dataList
	 * @return
	 */
	private Result<Boolean> checkAndFillImportDatas(List<WorkGridFlowDirection> dataList,WorkGrid gridData){
		Result<Boolean> result = Result.success();
		if(CollectionUtils.isEmpty(dataList)) {
			return result.toFail("数据列表为空！");
		}
		//逐条校验
		int rowNum = 1;
		Map<String,Integer> uniqueKeysRowNumMap = new HashMap<String,Integer>();
		for(WorkGridFlowDirection data : dataList) {
			String rowKey = "第" + rowNum + "行";
			Result<Boolean> result0 = checkAndFillNewData(data,gridData);
			if(!result0.isSuccess()) {
				return result0.toFail(rowKey + result0.getMessage());
			}
			//导入数据防重校验
			String uniqueKeysStr = getUniqueKeysStr(data);
			if(uniqueKeysRowNumMap.containsKey(uniqueKeysStr)) {
				return result0.toFail(rowKey + "和第"+uniqueKeysRowNumMap.get(uniqueKeysStr)+"行数据重复！");
			}
			uniqueKeysRowNumMap.put(uniqueKeysStr, rowNum);
			rowNum ++;
		}
		return result;
	}
	/**
	 * 校验并填充数据
	 * @param data
	 * @return
	 */
	private Result<Boolean> checkAndFillNewData(WorkGridFlowDirection flowData,WorkGrid workGridData){
		Result<Boolean> result = Result.success();
		Integer siteCode = flowData.getFlowSiteCode();
		if(siteCode == null) {
			return result.toFail("流向场地ID为空！");
		}
		BaseStaffSiteOrgDto siteInfo = baseMajorManager.getBaseSiteBySiteId(flowData.getFlowSiteCode());
		if(siteInfo == null) {
			return result.toFail("流向ID中站点无效！【"+flowData.getFlowSiteCode()+"】");
		}
		if(siteCode.equals(workGridData.getSiteCode())) {
			return result.toFail("流向场地ID不能是网格场地ID【"+siteCode+"】");
		}
		flowData.setSiteCode(workGridData.getSiteCode());
		flowData.setSiteName(workGridData.getSiteName());
		flowData.setOrgCode(workGridData.getOrgCode());
		flowData.setOrgName(workGridData.getOrgName());
		flowData.setFlowOrgCode(siteInfo.getOrgId());
		String orgName = AreaEnum.getAreaNameByCode(siteInfo.getOrgId());
		if(orgName == null) {
			orgName = siteInfo.getOrgName();
		}
		flowData.setFlowOrgName(orgName);
		flowData.setFlowSiteName(siteInfo.getSiteName());
		return result;
	}

	private String getUniqueKeysStr(WorkGridFlowDirection data) {
		if(data != null ) {
			return data.getRefWorkGridKey().toString()
					.concat(DmsConstants.KEYS_SPLIT)
					.concat(data.getFlowSiteCode().toString())
					;
		}
		return null;
	}
	@Override
	public int batchInsert(List<WorkGridFlowDirection> flowList) {
		if(CollectionUtils.isEmpty(flowList)) {
			return 0;
		}
		return workGridFlowDirectionDao.batchInsert(flowList);
	}
	@Override
	public List<Integer> queryExistFlowSiteCodeList(WorkGridFlowDirectionQuery query) {
		return workGridFlowDirectionDao.queryExistFlowSiteCodeList(query);
	}
	@Override
	public Result<PageDto<WorkGridFlowDirectionVo>> queryFlowDataForSelect(WorkGridFlowDirectionQuery query) {
		Result<PageDto<WorkGridFlowDirectionVo>> result = Result.success();
		if(query.getPageSize() == null || query.getPageSize() <= 0) {
			query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
		};
		query.setOffset(0);
		query.setLimit(query.getPageSize());
		if(query.getPageSize() == null || query.getPageNumber() > 0) {
			query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
		};
		if(query.getDt() == null) {
        	query.setDt(DateFormatUtils.format(DateHelper.addDays(new Date(), -1), DateHelper.DATE_FORMAT_YYYY_MM_DD));
        }
		int pageSize = query.getPageSize();
        PageDto<WorkGridFlowDirectionVo> pageData = new PageDto<>(query.getPageNumber(), query.getPageSize());
        Long totalCount = 0L;
        Long totalCount1 =  workGridFlowDirectionDao.queryFlowDataForSelectCount1(query);
        Long totalCount2 =  workGridFlowDirectionDao.queryFlowDataForSelectCount2(query);
        Long totalCount3 =  workGridFlowDirectionDao.queryFlowDataForSelectCount3(query);
        totalCount = totalCount1 + totalCount2 + totalCount3;
        int startRow = query.getOffset();
        int endRow = startRow + pageSize;
        List<WorkGridFlowDirectionVo> dataList = new ArrayList<>();
        if(totalCount != null && totalCount > 0){
            int queryRows = restPageInfo(query,dataList,startRow,endRow,pageSize,0,totalCount1);
            if(queryRows > 0) {
            	dataList.addAll(workGridFlowDirectionDao.queryFlowDataForSelect1(query));
            }
            queryRows = restPageInfo(query,dataList,startRow,endRow,pageSize,totalCount1,totalCount2);
            if(queryRows > 0) {
            	dataList.addAll(workGridFlowDirectionDao.queryFlowDataForSelect2(query));
            }
            queryRows = restPageInfo(query,dataList,startRow,endRow,pageSize,totalCount1 + totalCount2,totalCount3);
            if(queryRows > 0) {
            	dataList.addAll(workGridFlowDirectionDao.queryFlowDataForSelect3(query));
            }
            
            for(WorkGridFlowDirectionVo item: dataList) {
            	item.setConfigFlowStatusName(ConfigFlowStatusEnum.getNameByCode(item.getConfigFlowStatus()));
            	item.setFlowSiteUseStatusName(FlowSiteUseStatusEnum.getNameByCode(item.getFlowSiteUseStatus()));
            }
            pageData.setResult(dataList);
            pageData.setTotalRow(totalCount.intValue());
        }else {
            pageData.setResult(dataList);
            pageData.setTotalRow(0);
        }
        result.setData(pageData);		
		return result;
	}
	/**
	 * 
	 * @param dataList 已查询的数据列表
	 * @param startRow 开始行号
	 * @param endRow 结束行
	 * @param totalCount 能查到的总数
	 */
	private int restPageInfo(WorkGridFlowDirectionQuery query,List<WorkGridFlowDirectionVo> dataList, int startRow, int endRow,int pageSize,long dataStartRow, long totalCount) {
		int hasReadNum = dataList.size();
		int needReadNum = pageSize - hasReadNum;
		int startReadRow = startRow + hasReadNum;
		int endReadRow = startReadRow + needReadNum;
		int dataEndRow = (int)(dataStartRow + totalCount);
		//需要读取条数
		if(needReadNum == 0) {
			return 0;
		}
		if(totalCount == 0) {
			return 0;
		}
		if(startReadRow > dataEndRow) {
			return 0;
		}
		int realStartRow = (int)dataStartRow;
		int realEndRow = endReadRow;
		if(startReadRow > realStartRow) {
			realStartRow = startReadRow;
		}
		if(realEndRow > dataEndRow) {
			realEndRow = dataEndRow;
		}
		query.setPageNumber(0);
		query.setOffset((int)(realStartRow - dataStartRow));
		query.setLimit(realEndRow - realStartRow);
		log.info("Offset:{},Limit:{}",query.getOffset(),query.getLimit());
		return realEndRow - realStartRow;
	}
	@Override
	public Result<List<WorkGridFlowDirection>> queryListForExport(WorkGridFlowDirectionQuery query) {
		Result<List<WorkGridFlowDirection>> result = Result.success();
		result.setData(workGridFlowDirectionDao.queryList(query));
		return result;
	}
	@Override
	public Result<Long> queryCount(WorkGridFlowDirectionQuery query) {
		Result<Long> result = Result.success();
		result.setData(workGridFlowDirectionDao.queryCount(query));
		return result;
	}
}
