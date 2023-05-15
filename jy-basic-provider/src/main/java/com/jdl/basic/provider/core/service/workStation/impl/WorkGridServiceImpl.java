package com.jdl.basic.provider.core.service.workStation.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jd.jsf.gd.util.StringUtils;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.machine.Machine;
import com.jdl.basic.api.domain.workStation.DeleteRequest;
import com.jdl.basic.api.domain.workStation.WorkArea;
import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirection;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionQuery;
import com.jdl.basic.api.domain.workStation.WorkGridImport;
import com.jdl.basic.api.domain.workStation.WorkGridQuery;
import com.jdl.basic.api.domain.workStation.WorkGridVo;
import com.jdl.basic.api.domain.workStation.WorkGridVo.FlowInfoItem;
import com.jdl.basic.api.domain.workStation.WorkGridVo.WorkDataInfo;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.api.domain.workStation.WorkStationGridQuery;
import com.jdl.basic.api.enums.ConfigFlowStatusEnum;
import com.jdl.basic.api.enums.GridFlowLineTypeEnum;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.enums.AreaEnum;
import com.jdl.basic.common.utils.CheckHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringHelper;
import com.jdl.basic.provider.core.components.IGenerateObjectId;
import com.jdl.basic.provider.core.dao.workStation.WorkGridDao;
import com.jdl.basic.provider.core.service.machine.WorkStationGridMachineService;
import com.jdl.basic.provider.core.service.workStation.WorkAreaService;
import com.jdl.basic.provider.core.service.workStation.WorkGridFlowDirectionService;
import com.jdl.basic.provider.core.service.workStation.WorkGridService;
import com.jdl.basic.provider.core.service.workStation.WorkStationGridService;
import com.jdl.basic.rpc.Rpc.BaseMajorRpc;

import lombok.extern.slf4j.Slf4j;

/**
 * 场地网格表--Service接口实现
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Slf4j
@Service("workGridService")
public class WorkGridServiceImpl implements WorkGridService {

	@Autowired
	@Qualifier("workGridDao")
	private WorkGridDao workGridDao;
	@Autowired
	private IGenerateObjectId genObjectId;
	@Autowired
	@Qualifier("workGridFlowDirectionService")
	private WorkGridFlowDirectionService workGridFlowDirectionService;
	@Autowired
	@Qualifier("workStationGridService")
	private WorkStationGridService workStationGridService;
	@Autowired
	private WorkStationGridMachineService machineService;
	@Autowired
	private BaseMajorRpc baseMajorManager;	
	
	@Autowired
	@Qualifier("workAreaService")
	private WorkAreaService workAreaService;	
	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGrid insertData){
		Result<Boolean> result = Result.success();
		insertData.setBusinessKey(generalBusinessKey());
		result.setData(workGridDao.insert(insertData) == 1);
		return result;
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGrid updateData){
		Result<Boolean> result = Result.success();
		result.setData(workGridDao.updateById(updateData) == 1);
		return result;
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public Result<Boolean> deleteById(WorkGrid deleteData){
		Result<Boolean> result = Result.success();
		result.setData(workGridDao.deleteById(deleteData) == 1);
		return result;
	 }
	@Override
	public Result<WorkGrid> queryById(Long id) {
		Result<WorkGrid> result = Result.success();
		result.setData(workGridDao.queryById(id));
		return result;
	}	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridVo> queryByIdForConfigFlow(Long id){
		Result<WorkGridVo> result = Result.success();
		result.setData(toWorkGridVoForConfigFlow(workGridDao.queryById(id)));
		return result;
	 }	
	private WorkGridVo toWorkGridVoForConfigFlow(WorkGrid data) {
		if(data == null) {
			return null;
		}
		WorkGridVo voData = new WorkGridVo();
		BeanUtils.copyProperties(data, voData);
		voData.setConfigFlowStatusName(ConfigFlowStatusEnum.getNameByCode(voData.getConfigFlowStatus()));
		//特殊字段设置
		loadFlowInfo(voData);
		WorkArea workArea = this.workAreaService.queryByAreaCode(voData.getAreaCode());
		if(workArea != null) {
			voData.setFlowDirectionType(workArea.getFlowDirectionType());
		}
		return voData;
	}
	/**
	 * 根据业务主键查询
	 * @param workGrid
	 * @return
	 */
	public Result<WorkGrid> queryByBusinessKeys(WorkGrid workGrid){
		Result<WorkGrid> result = Result.success();
		result.setData(workGridDao.queryByBusinessKeys(workGrid));
		return result;
	 }	
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridVo>> queryPageList(WorkGridQuery query){
		Result<PageDto<WorkGridVo>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkGridVo> voDataList = new ArrayList<WorkGridVo>();
		PageDto<WorkGridVo> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workGridDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
		    List<WorkGrid> dataList = workGridDao.queryList(query);
		    for (WorkGrid tmp : dataList) {
		    	voDataList.add(this.toWorkGridVo(tmp));
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
	public Result<Boolean> checkParamForQueryPageList(WorkGridQuery query){
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
	public WorkGridVo toWorkGridVo(WorkGrid data){
		if(data == null) {
			return null;
		}
		WorkGridVo voData = new WorkGridVo();
		BeanUtils.copyProperties(data, voData);
		voData.setConfigFlowStatusName(ConfigFlowStatusEnum.getNameByCode(voData.getConfigFlowStatus()));
		//特殊字段设置
		loadFlowInfo(voData);
		loadWorkInfo(voData);
		return voData;
	 }
	private void loadFlowInfo(WorkGridVo voData) {
		if(voData == null) {
			return;
		}
		voData.setFlowInfo(queryFlowInfoByWorkGridKey(voData.getBusinessKey()));
	}	
	private void loadWorkInfo(WorkGridVo voData) {
		if(voData == null) {
			return;
		}
		WorkDataInfo workDataInfo = new WorkDataInfo();
		voData.setWorkDataInfo(workDataInfo);
		WorkStationGridQuery query = new WorkStationGridQuery();
		query.setRefWorkGridKey(voData.getBusinessKey());
		List<WorkStationGrid> workStationGridList = workStationGridService.queryListForWorkGridVo(query);
		if(CollectionUtils.isEmpty(workStationGridList)) {
			return;
		}
		workDataInfo.setWorkStationGridList(workStationGridList);
		workDataInfo.setWorkStationGridNum(workStationGridList.size());
		Collections.sort(workStationGridList,new Comparator<WorkStationGrid>() {
                    @Override
                    public int compare(WorkStationGrid o1, WorkStationGrid o2) {
                        Date o1Ts = o1.getTs();
                        Date o2Ts = o2.getTs();
                        if(o1Ts != null && o1Ts != null) {
                        	return o2Ts.compareTo(o1Ts);
                        }
                        return 0;
                    }
                });
		Integer standardNum = 0;
		List<Machine> machineList = new ArrayList<>();
		Set<String> machineCodes = new HashSet<>();
		for(WorkStationGrid workGrid : workStationGridList) {
			if(workGrid.getStandardNum() != null) {
				standardNum += workGrid.getStandardNum();
			}
		}
		HashMap<String,List<Machine>> machineDatas = machineService.getMachineListByRefGridKey(workStationGridList);
		for(String k: machineDatas.keySet()) {
			for(Machine machine: machineDatas.get(k)) {
				if(!machineCodes.contains(machine.getMachineCode())) {
					machineList.add(machine);
					machineCodes.add(machine.getMachineCode());
				}
			}
		}
		WorkStationGrid data = workStationGridList.get(0);
		workDataInfo.setDockCode(data.getDockCode());
		workDataInfo.setSupplierCode(data.getSupplierCode());
		workDataInfo.setSupplierName(data.getSupplierName());
		workDataInfo.setOwnerUserErp(data.getOwnerUserErp());
		workDataInfo.setStandardNum(standardNum);
		workDataInfo.setMachineList(machineList);
	}
	/**
	 * 查询流向配置信息，按线路类型分类返回map形式
	 * @param workGridKey
	 * @return
	 */
	private Map<String,FlowInfoItem> queryFlowInfoByWorkGridKey(String workGridKey){
		Map<String,FlowInfoItem> flowInfo = new HashMap<>();
		WorkGridFlowDirectionQuery query = new WorkGridFlowDirectionQuery();
		query.setRefWorkGridKey(workGridKey);
		List<WorkGridFlowDirection> flowList = workGridFlowDirectionService.queryListForWorkGridVo(query);
		if(CollectionUtils.isEmpty(flowList)) {
			return flowInfo;
		}
		for(WorkGridFlowDirection item: flowList) {
			String lineType = GridFlowLineTypeEnum.OTHER.getCode().toString();
			if(item.getLineType() != null) {
				GridFlowLineTypeEnum lineTypeEnum = GridFlowLineTypeEnum.getEnumByCode(item.getLineType());
				if(lineTypeEnum != null) {
					lineType = lineTypeEnum.getCode().toString();
				}
			}
			FlowInfoItem flowInfoItem= flowInfo.get(lineType);
			if(flowInfoItem == null) {
				flowInfoItem = new FlowInfoItem();
				flowInfoItem.setFlowList(new ArrayList<>());
				flowInfoItem.setFlowNum(0);
				flowInfo.put(lineType, flowInfoItem);
			}
			flowInfoItem.getFlowList().add(item);
			flowInfoItem.setFlowNum(flowInfoItem.getFlowList().size());
		}
		return flowInfo;
	}
	@Override
	public Result<WorkGrid> saveData(WorkGrid workGrid) {
		Result<WorkGrid> result = Result.success();
		WorkGrid oldData = workGridDao.queryByBusinessKeys(workGrid);
		if(oldData != null) {
			WorkGrid updateData = new WorkGrid();
			updateData.setId(oldData.getId());
			updateData.setOrgCode(workGrid.getOrgCode());
			updateData.setOrgName(workGrid.getOrgName());
			updateData.setSiteType(workGrid.getSiteType());
			updateData.setSiteTypeName(workGrid.getSiteTypeName());
			updateData.setSiteName(workGrid.getSiteName());
			updateData.setGridCode(workGrid.getGridCode());
			updateData.setGridName(workGrid.getGridName());
			updateData.setAreaName(workGrid.getAreaName());
			updateData.setStandardNum(workGrid.getStandardNum());
			updateData.setOwnerUserErp(workGrid.getOwnerUserErp());
			updateData.setDockCode(workGrid.getDockCode());
			updateData.setSupplierCode(workGrid.getSupplierCode());
			updateData.setSupplierName(workGrid.getSupplierName());
			updateData.setUpdateUser(workGrid.getUpdateUser());
			updateData.setUpdateUserName(workGrid.getUpdateUserName());
			updateData.setUpdateTime(workGrid.getUpdateTime());
			this.updateById(updateData);
			result.setData(workGridDao.queryById(oldData.getId()));
		}else {
			this.insert(workGrid);
			result.setData(workGrid);
		}
		return result;
	}
	private String generalBusinessKey() {
		return DmsConstants.CODE_PREFIX_WORK_GRID.concat(StringHelper.padZero(this.genObjectId.getObjectId(WorkGrid.class.getName()),11));
	}
	@Override
	public Result<Long> queryCount(WorkGridQuery query) {
		Result<Long> result = Result.success();
		result.setData(workGridDao.queryCount(query));
		return result;
	}
	@Override
	public Result<List<WorkGridVo>> queryListForExport(WorkGridQuery query) {
		Result<List<WorkGridVo>> result = Result.success();
		List<WorkGrid> dataList = workGridDao.queryList(query);
	    List<WorkGridVo> voDataList = new ArrayList<WorkGridVo>();
	    for (WorkGrid tmp : dataList) {
	    	voDataList.add(this.toWorkGridVo(tmp));
	    }
		result.setData(voDataList);
		return result;	    
	}
	@Override
	public Result<Boolean> deleteByIds(DeleteRequest<WorkGrid> deleteRequest) {
		Result<Boolean> result = Result.success();
		if(deleteRequest == null
				|| CollectionUtils.isEmpty(deleteRequest.getDataList())) {
			return result.toFail("参数错误，删除列表不能为空！");
		}
		List<WorkGrid> oldDataList = workGridDao.queryByIds(deleteRequest);
		if(CollectionUtils.isEmpty(oldDataList)
				|| oldDataList.size() < deleteRequest.getDataList().size()) {
			return result.toFail("参数错误，数据已变更请刷新列表后重新选择！");
		}
		for(WorkGrid oldData : oldDataList) {
			if(!ObjectUtils.equals(oldData.getSiteCode(), deleteRequest.getOperateSiteCode())) {
				return result.toFail("网格【"+oldData.getGridName()+ "】非本人所在场地数据，无法删除！");
			}
		}
		result.setData(workGridDao.deleteByIds(deleteRequest) > 0);
		return result;
	}
	@Transactional
	@Override
	public Result<Boolean> importDatas(List<WorkGridImport> dataList) {
		Result<Boolean> result = checkAndFillImportDatas(dataList);
		if(!result.isSuccess()) {
			return result;
		}
		for(WorkGridImport gridData: dataList) {
			//更新-配置状态
			WorkGrid updateData = new WorkGrid();
			updateData.setId(gridData.getId());
			updateData.setConfigFlowStatus(ConfigFlowStatusEnum.CONFIG.getCode());
			updateData.setConfigFlowUser(gridData.getConfigFlowUser());
			updateData.setConfigFlowTime(new Date());
			workGridDao.updateById(updateData);
			//插入流向配置信息
			Map<Integer,List<WorkGridFlowDirection>> flowDataMap = gridData.getFlowDataMap();
			//按线路类型-分别处理
			for(Integer lineType : flowDataMap.keySet()) {
				List<WorkGridFlowDirection> flowList = flowDataMap.get(lineType);
				List<WorkGridFlowDirection> flowListForAdd = new ArrayList<>();
				List<Integer> flowSiteCodes = new ArrayList<>();
				for(WorkGridFlowDirection flowData: flowList) {
					flowSiteCodes.add(flowData.getFlowSiteCode());
				}
				WorkGridFlowDirection data0 = flowList.get(0);
				WorkGridFlowDirectionQuery query = new WorkGridFlowDirectionQuery();
				query.setFlowDirectionType(data0.getFlowDirectionType());
				query.setRefWorkGridKey(data0.getRefWorkGridKey());
				query.setFlowSiteCodeList(flowSiteCodes);
				query.setLineType(lineType);
				List<Integer> flowSiteCodesExist = this.workGridFlowDirectionService.queryExistFlowSiteCodeList(query);
				for(WorkGridFlowDirection flowData: flowList) {
					if(!flowSiteCodesExist.contains(flowData.getFlowSiteCode())) {
						flowListForAdd.add(flowData);
					}
				}
				workGridFlowDirectionService.batchInsert(flowListForAdd);
			}
		}
		return result;
	}
	/**
	 * 校验并填充导入数据
	 * @param dataList
	 * @return
	 */
	private Result<Boolean> checkAndFillImportDatas(List<WorkGridImport> dataList){
		Result<Boolean> result = Result.success();
		if(CollectionUtils.isEmpty(dataList)) {
			return result.toFail("导入数据为空！");
		}
		//逐条校验
		int rowNum = 1;
		Map<String,Integer> uniqueKeysRowNumMap = new HashMap<String,Integer>();
		List<String> areaCodeList = new ArrayList<>();
		for(WorkGridImport data : dataList) {
			String rowKey = "第" + rowNum + "行";
			if(StringUtils.isNotBlank(data.getAreaCode())) {
				if(!areaCodeList.contains(data.getAreaCode())) {
					areaCodeList.add(data.getAreaCode());
				}
			}else {
				result.toFail(rowKey+"作业区ID为空！");
			}
			rowNum ++;
		}
		Map<String,WorkArea> areaMap = workAreaService.queryByAreaCodeListToMap(areaCodeList);
		if(areaMap == null || areaMap.isEmpty()) {
			result.toFail("导入的数据作业区无效！");
		}
		rowNum = 1;
		for(WorkGridImport data : dataList) {
			String rowKey = "第" + rowNum + "行";
			Result<Boolean> result0 = checkAndFillNewData(data,areaMap);
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
	private Result<Boolean> checkAndFillNewData(WorkGridImport data,Map<String,WorkArea> areaMap){
		Result<Boolean> result = Result.success();
		Integer siteCode = data.getSiteCode();
		Integer floor = data.getFloor();
		String gridNo = data.getGridNo();
		String areaCode = data.getAreaCode();
		if(siteCode == null) {
			return result.toFail("场地ID为空！");
		}
		if(!CheckHelper.checkInteger("楼层", floor, 1,5, result).isSuccess()) {
			return result;
		}
		//校验gridNo
		if(StringHelper.isEmpty(gridNo)) {
			return result.toFail("网格号为空！");
		}
		if(gridNo.length()<2) {
			gridNo = "0".concat(gridNo);
			data.setGridNo(gridNo);
		}
		if(!CheckHelper.checkGridNo(gridNo, result).isSuccess()) {
			return result;
		}
		if(!CheckHelper.checkStr("作业区ID", areaCode, 50, result).isSuccess()) {
			return result;
		}
		Map<GridFlowLineTypeEnum,List<String>> flowSiteCodes = new HashMap<>();
		if(StringUtils.isNotBlank(data.getFlowSiteCodeStr1())){
			flowSiteCodes.put(GridFlowLineTypeEnum.TRUNK_LINE, StringHelper.parseList(data.getFlowSiteCodeStr1(), StringHelper.FLOW_SITE_CODE_SPLIT));
		}
		if(StringUtils.isNotBlank(data.getFlowSiteCodeStr2())){
			flowSiteCodes.put(GridFlowLineTypeEnum.BRANCH_LINE, StringHelper.parseList(data.getFlowSiteCodeStr2(), StringHelper.FLOW_SITE_CODE_SPLIT));
		}
		if(StringUtils.isNotBlank(data.getFlowSiteCodeStr3())){
			flowSiteCodes.put(GridFlowLineTypeEnum.TRANSFER, StringHelper.parseList(data.getFlowSiteCodeStr3(), StringHelper.FLOW_SITE_CODE_SPLIT));
		}
		if(StringUtils.isNotBlank(data.getFlowSiteCodeStr4())){
			flowSiteCodes.put(GridFlowLineTypeEnum.SHUTTLE, StringHelper.parseList(data.getFlowSiteCodeStr4(), StringHelper.FLOW_SITE_CODE_SPLIT));
		}
		if(flowSiteCodes.isEmpty()) {
			return result.toFail("配置无效，流向ID为空！");
		}
		
		WorkGrid workGridQuery = new WorkGrid();
		workGridQuery.setSiteCode(siteCode);
		workGridQuery.setFloor(floor);
		workGridQuery.setGridNo(gridNo);
		workGridQuery.setAreaCode(areaCode);
		WorkGrid workGridData = workGridDao.queryByBusinessKeys(workGridQuery);
		if(workGridData == null) {
			return result.toFail("网格信息不存在，请先维护场地网格信息！");
		}
		data.setId(workGridData.getId());
		WorkArea workArea = areaMap.get(areaCode);
		if(workArea == null) {
			return result.toFail("作业区不存在，请先维护作业区工序信息！");
		}
		if(workArea.getFlowDirectionType() == null) {
			return result.toFail("作业区【"+areaCode+"】未维护流向类型，无法配置流向信息！");
		}
		Map<Integer,List<WorkGridFlowDirection>> flowDataMap = new HashMap<>();
		data.setFlowDataMap(flowDataMap);
		for(GridFlowLineTypeEnum lineType : flowSiteCodes.keySet()) {
			List<String> siteCodeStrList = flowSiteCodes.get(lineType);
			List<WorkGridFlowDirection> flowDataList = new ArrayList<>();
			for(String siteCodeStr : siteCodeStrList) {
				Integer siteCodeInt = null;
				if(!StringHelper.isNumberic(siteCodeStr)) {
					return result.toFail(lineType.getName()+"流向ID中存在非数字站点！【"+siteCodeStr+"】");
				}
				siteCodeInt = Integer.valueOf(siteCodeStr);
				BaseStaffSiteOrgDto siteInfo = baseMajorManager.getBaseSiteBySiteId(siteCodeInt);
				if(siteInfo == null) {
					return result.toFail(lineType.getName()+"流向ID中站点无效！【"+siteCodeStr+"】");
				}
				WorkGridFlowDirection flowData = new WorkGridFlowDirection();
				flowData.setRefWorkGridKey(workGridData.getBusinessKey());
				flowData.setSiteCode(workGridData.getSiteCode());
				flowData.setSiteName(workGridData.getSiteName());
				flowData.setOrgCode(workGridData.getOrgCode());
				flowData.setOrgName(workGridData.getOrgName());
				flowData.setCreateUser(data.getConfigFlowUser());
				flowData.setLineType(lineType.getCode());
				flowData.setFlowSiteCode(siteCodeInt);
				flowData.setFlowOrgCode(siteInfo.getOrgId());
				String orgName = AreaEnum.getAreaNameByCode(siteInfo.getOrgId());
				if(orgName == null) {
					orgName = siteInfo.getOrgName();
				}
				flowData.setFlowOrgName(orgName);
				flowData.setFlowSiteName(siteInfo.getSiteName());
				flowData.setFlowDirectionType(workArea.getFlowDirectionType());
				flowDataList.add(flowData);
			}
			flowDataMap.put(lineType.getCode(), flowDataList);
		}
		return result;
	}
	@Override
	public String getUniqueKeysStr(WorkGrid data) {
		if(data != null ) {
			return data.getSiteCode().toString()
					.concat(DmsConstants.KEYS_SPLIT)
					.concat(data.getFloor().toString())
					.concat(DmsConstants.KEYS_SPLIT)
					.concat(data.getGridNo())
					.concat(DmsConstants.KEYS_SPLIT)
					.concat(data.getAreaCode());
		}
		return null;
	}
	@Override
	public WorkGrid queryByWorkGridKey(String workGridKey) {
		return workGridDao.queryByWorkGridKey(workGridKey);
	}	
}
