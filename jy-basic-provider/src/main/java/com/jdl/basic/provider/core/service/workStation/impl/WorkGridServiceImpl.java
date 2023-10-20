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

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jd.jsf.gd.util.StringUtils;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.machine.Machine;
import com.jdl.basic.api.domain.workStation.WorkGridVo.FlowInfoItem;
import com.jdl.basic.api.domain.workStation.WorkGridVo.WorkDataInfo;
import com.jdl.basic.api.enums.ConfigFlowStatusEnum;
import com.jdl.basic.api.enums.EditTypeEnum;
import com.jdl.basic.api.enums.GridFlowLineTypeEnum;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.enums.AreaEnum;
import com.jdl.basic.common.utils.CheckHelper;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringHelper;
import com.jdl.basic.provider.config.ducc.DuccPropertyConfiguration;
import com.jdl.basic.provider.core.components.IGenerateObjectId;
import com.jdl.basic.provider.core.dao.workStation.WorkGridDao;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.manager.DeviceConfigInfoJsfServiceManager;
import com.jdl.basic.provider.core.service.machine.WorkStationGridMachineService;
import com.jdl.basic.provider.core.service.workStation.WorkAreaService;
import com.jdl.basic.provider.core.service.workStation.WorkGridFlowDirectionService;
import com.jdl.basic.provider.core.service.workStation.WorkGridService;
import com.jdl.basic.provider.core.service.workStation.WorkStationGridService;
import com.jdl.basic.provider.mq.producer.DefaultJMQProducer;

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
	private BaseMajorManager baseMajorManager;	
	
	@Autowired
	@Qualifier("workAreaService")
	private WorkAreaService workAreaService;
	
	/**
	 * 导入总数据限制
	 */
	@Value("${beans.workGridService.importDatasLimit:100}")
	private int importDatasLimit;
	/**
	 * 导入流向数限制
	 */
	@Value("${beans.workGridService.importDatasPerFlowLimit:100}")
	private int importDatasPerFlowLimit;
	
	@Autowired
	@Qualifier("workGridModifyMq")
	DefaultJMQProducer workGridModifyMq;
	
	@Autowired
	@Qualifier("deviceConfigInfoJsfServiceManager")
	DeviceConfigInfoJsfServiceManager deviceConfigInfoJsfServiceManager;
	
	@Autowired
	private DuccPropertyConfiguration duccPropertyConfiguration;
	
	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	@Transactional
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
	@Transactional
	public Result<Boolean> updateById(WorkGridEditVo updateData){
		Result<Boolean> result = Result.success();
		//更新网格下所有工序信息
		this.workStationGridService.syncWorkGridInfo(updateData);
		result.setData(workGridDao.updateById(updateData) == 1);
		return result;
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	@Transactional
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
	@Transactional
	public Result<Boolean> deleteById(WorkGrid deleteData){
		Result<Boolean> result = Result.success();
		if(deleteData == null
				|| deleteData.getId() == null) {
			return result.toFail("删除数据及id不能为空！");
		}
		WorkGrid oldData = workGridDao.queryById(deleteData.getId());
		return checkAndDeleteData(deleteData,oldData);
	 }
	@Transactional
	@Override
	public Result<Boolean> deleteByWorkGridKey(WorkGrid deleteData) {
		Result<Boolean> result = Result.success();
		if(deleteData == null
				|| StringUtils.isBlank(deleteData.getBusinessKey())) {
			return result.toFail("删除数据及businessKey不能为空！");
		}
		WorkGrid oldData = workGridDao.queryByWorkGridKey(deleteData.getBusinessKey());
		return checkAndDeleteData(deleteData,oldData);
	}
	/**
	 * 执行校验及删除操作
	 * @param deleteData
	 * @param oldData
	 * @return
	 */
	private Result<Boolean> checkAndDeleteData(WorkGrid deleteData, WorkGrid oldData) {
		Result<Boolean> result = Result.success();
		if(oldData == null) {
			return result.toFail("删除的网格数据不存在！");
		}
		
		//校验网格工序数据
		int stationGridCount = this.workStationGridService.queryCountByRefGridKey(oldData.getBusinessKey());
		if(stationGridCount > 0) {
			return result.toFail("操作失败！网格下存在"+stationGridCount+"个未删除的网格工序，不可删除！");
		}
		deleteData(deleteData,oldData);
		return result;
	}
	private void deleteData(WorkGrid deleteData, WorkGrid oldData) {
		String refWorkGridKey = oldData.getBusinessKey();
		deleteData.setId(oldData.getId());
		//删除网格
		workGridDao.deleteById(deleteData);
		
		WorkGridFlowDirection deleteGridFlow = new WorkGridFlowDirection();
		deleteGridFlow.setRefWorkGridKey(refWorkGridKey);
		deleteGridFlow.setUpdateUser(deleteData.getUpdateUser());
		deleteGridFlow.setUpdateTime(deleteData.getUpdateTime());
		this.workGridFlowDirectionService.deleteByRefGridKey(deleteGridFlow);
		//发送网格删除mq
		WorkGridModifyMqData mqData = new WorkGridModifyMqData();
		mqData.setGridData(oldData);
		mqData.setEditType(EditTypeEnum.DELETE.getCode());
		mqData.setOperateTime(deleteData.getUpdateTime());
		mqData.setOperateUserCode(deleteData.getUpdateUser());
		mqData.setOperateUserName(deleteData.getUpdateUserName());
		workGridModifyMq.sendOnFailPersistent(refWorkGridKey, JsonHelper.toJSONString(mqData));
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
		loadWorkInfo(voData);
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
		this.loadDeviceInfo(voDataList);
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
	public Result<Boolean> checkParamForQueryMachineList(WorkGridQuery query){
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
		WorkArea workArea = this.workAreaService.queryByAreaCode(voData.getAreaCode());
		if(workArea != null) {
			voData.setFlowDirectionType(workArea.getFlowDirectionType());
		}
		voData.setFlowInfo(queryFlowInfoByWorkGridKey(voData));
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
	private Map<String,FlowInfoItem> queryFlowInfoByWorkGridKey(WorkGridVo voData){
		Map<String,FlowInfoItem> flowInfo = new HashMap<>();
		WorkGridFlowDirectionQuery query = new WorkGridFlowDirectionQuery();
		query.setRefWorkGridKey(voData.getBusinessKey());
		query.setFlowDirectionType(voData.getFlowDirectionType());
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
	@Transactional
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
			updateData.setSupplierCode(workGrid.getSupplierCode());
			updateData.setSupplierName(workGrid.getSupplierName());
			updateData.setUpdateUser(workGrid.getUpdateUser());
			updateData.setUpdateUserName(workGrid.getUpdateUserName());
			updateData.setUpdateTime(workGrid.getUpdateTime());
			updateData.setProvinceAgencyCode(workGrid.getProvinceAgencyCode());
			updateData.setProvinceAgencyName(workGrid.getProvinceAgencyName());
			updateData.setAreaHubCode(workGrid.getAreaHubCode());
			updateData.setAreaHubName(workGrid.getAreaHubName());
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
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkGrid> dataList = workGridDao.queryList(query);
	    List<WorkGridVo> voDataList = new ArrayList<WorkGridVo>();
	    for (WorkGrid tmp : dataList) {
	    	voDataList.add(this.toWorkGridVo(tmp));
	    }
	    loadDeviceInfo(voDataList);
		result.setData(voDataList);
		return result;
	}
	/**
	 * 批量加载设备绑定信息
	 * @param dataList
	 */
	private void loadDeviceInfo(List<WorkGridVo> dataList) {
		if(CollectionUtils.isEmpty(dataList)) {
			return;
		}
		List<String> gridKeys = new ArrayList<String>();
		for(WorkGridVo data:dataList) {
			gridKeys.add(data.getBusinessKey());
		}
		Result<List<WorkGridDeviceVo>> deviceResult = deviceConfigInfoJsfServiceManager.findDeviceGridByBusinessKey(null,gridKeys);
		Map<String,List<WorkGridDeviceVo>> tmpMachineListMap = new HashMap<>();
		if(deviceResult != null && CollectionUtils.isNotEmpty(deviceResult.getData())) {
			for(WorkGridDeviceVo device:deviceResult.getData()) {
				List<WorkGridDeviceVo> tmpList = tmpMachineListMap.get(device.getRefWorkGridKey());
				if(tmpList == null) {
					tmpList = new ArrayList<WorkGridDeviceVo>();
					tmpMachineListMap.put(device.getRefWorkGridKey(), tmpList);
				}
				tmpList.add(device);
			}
		}
		for(WorkGridVo vo:dataList) {
			if(tmpMachineListMap.containsKey(vo.getBusinessKey())) {
				vo.setWorkGridDeviceVoList(tmpMachineListMap.get(vo.getBusinessKey()));
			}else {
				vo.setWorkGridDeviceVoList(new ArrayList<>());
			}
		}
	}
	@Transactional
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
			//校验网格是否存在网格工序
			//校验网格工序数据
			int stationGridCount = this.workStationGridService.queryCountByRefGridKey(oldData.getBusinessKey());
			if(stationGridCount > 0) {
				return result.toFail("操作失败！网格【"+oldData.getGridName()+"】存在"+stationGridCount+"个未删除的网格工序，不可删除！");
			}
		}
		
		for(WorkGrid oldData : oldDataList) {
			WorkGrid deleteGrid = new WorkGrid();
			deleteGrid.setUpdateUser(deleteRequest.getOperateUserCode());
			deleteGrid.setUpdateUserName(deleteRequest.getOperateUserName());
			deleteGrid.setUpdateTime(deleteRequest.getOperateTime());
			this.deleteData(deleteGrid, oldData);
		}
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
				if(duccPropertyConfiguration.needAreaCodesForFlowCheck(gridData.getAreaCode())) {
					WorkGridQuery workGridQuery = new WorkGridQuery();
					workGridQuery.setSiteCode(gridData.getSiteCode());
					workGridQuery.setAreaCode(gridData.getAreaCode());
					query.setRefWorkGridKeyList(this.queryGridKeyListBySiteAndArea(workGridQuery));
				}else {
					query.setRefWorkGridKey(data0.getRefWorkGridKey());
				}
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
		if(dataList.size() > importDatasLimit) {
			return result.toFail("导入数据不能超过"+importDatasLimit+"条！");
		}
		//逐条校验
		int rowNum = 1;
		Map<String,Integer> uniqueKeysRowNumMap = new HashMap<String,Integer>();
		List<String> importFlowKeyList = new ArrayList<>();
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
			Result<Boolean> result0 = checkAndFillNewData(data,areaMap,importFlowKeyList);
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
	private Result<Boolean> checkAndFillNewData(WorkGridImport data,Map<String,WorkArea> areaMap,List<String> importFlowKeyList){
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
		Date createTime = new Date();
		for(GridFlowLineTypeEnum lineType : flowSiteCodes.keySet()) {
			List<String> siteCodeStrList = flowSiteCodes.get(lineType);
			if(siteCodeStrList.size() > importDatasLimit) {
				return result.toFail(lineType.getName()+"流向站点不能超过！【"+importDatasPerFlowLimit+"】个");
			}
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
				if(siteCodeInt.equals(workGridData.getSiteCode())) {
					return result.toFail(lineType.getName()+"流向ID不能是网格的站点！【"+siteCodeStr+"】");
				}
				String importFlowKey = getImportFlowKey(workGridData.getBusinessKey(),lineType.getCode(),siteCodeInt);
				//判断是否按作业区校验
				boolean checkByArea = this.duccPropertyConfiguration.needAreaCodesForFlowCheck(areaCode);
				if(checkByArea) {
					importFlowKey = getImportFlowKey(areaCode,lineType.getCode(),siteCodeInt);
				}
				if(importFlowKeyList.contains(importFlowKey)){
					if(checkByArea) {
						return result.toFail(workArea.getAreaName() + "】同一作业区下" +lineType.getName()+" 存在重复流向站点！【"+siteCodeStr+"】");
					}else {
						return result.toFail(lineType.getName()+"存在重复流向站点！【"+siteCodeStr+"】");
					}
				}
				importFlowKeyList.add(importFlowKey);
				WorkGridFlowDirection flowData = new WorkGridFlowDirection();
				flowData.setRefWorkGridKey(workGridData.getBusinessKey());
				flowData.setSiteCode(workGridData.getSiteCode());
				flowData.setSiteName(workGridData.getSiteName());
				flowData.setOrgCode(workGridData.getOrgCode());
				flowData.setOrgName(workGridData.getOrgName());
				flowData.setProvinceAgencyCode(workGridData.getProvinceAgencyCode());
				flowData.setProvinceAgencyName(workGridData.getProvinceAgencyName());
				flowData.setAreaHubCode(workGridData.getAreaHubCode());
				flowData.setAreaHubName(workGridData.getAreaHubName());
				flowData.setCreateUser(data.getConfigFlowUser());
				flowData.setCreateTime(createTime);
				flowData.setLineType(lineType.getCode());
				flowData.setFlowSiteCode(siteCodeInt);
				flowData.setFlowOrgCode(siteInfo.getOrgId());
				String orgName = AreaEnum.getAreaNameByCode(siteInfo.getOrgId());
				if(orgName == null) {
					orgName = siteInfo.getOrgName();
				}
				flowData.setFlowOrgName(orgName);
				flowData.setFlowSiteName(siteInfo.getSiteName());
				flowData.setFlowProvinceAgencyCode(siteInfo.getProvinceAgencyCode());
				flowData.setFlowProvinceAgencyName(siteInfo.getProvinceAgencyName());
				flowData.setFlowAreaHubCode(siteInfo.getAreaCode());
				flowData.setFlowAreaHubName(siteInfo.getAreaName());
				flowData.setFlowDirectionType(workArea.getFlowDirectionType());
				flowDataList.add(flowData);
			}
			flowDataMap.put(lineType.getCode(), flowDataList);
		}
		return result;
	}
	private String getImportFlowKey(String areaCodeOrGridKey,Integer lineType,Integer flowSiteCode) {
		return areaCodeOrGridKey
				.concat(DmsConstants.KEYS_SPLIT)
				.concat(lineType.toString())
				.concat(DmsConstants.KEYS_SPLIT)
				.concat(flowSiteCode.toString())
				;
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
	public List<WorkGrid> queryFloorDictList(WorkGrid queryParams) {
		return workGridDao.queryFloorDictList(queryParams);
	}

	@Override
	public List<WorkGrid> queryAreaDictList(WorkGrid queryParams) {
		return workGridDao.queryAreaDictList(queryParams);
	}

	@Override
	public List<WorkGrid> queryWorkGrid(WorkGrid queryParams) {
		return workGridDao.queryWorkGrid(queryParams);
	}

	@Override
	public List<WorkGrid> batchQueryByWorkGridKey(List<String> workGridKeys) {
		return workGridDao.batchQueryByWorkGridKey(workGridKeys);
	}

	@Override
	public List<WorkGrid> queryAreaWorkGrid(WorkGridQuery query) {
		return workGridDao.queryAreaWorkGrid(query);
	}

	@Override
	public List<WorkGrid> queryAllGridBySiteCode(WorkGridQuery query) {
		return workGridDao.queryAllGridBySiteCode(query);
	}

	@Override
	public WorkGrid queryByWorkGridKey(String workGridKey) {
		return workGridDao.queryByWorkGridKey(workGridKey);
	}
	@Override
	public List<Integer> querySiteListForManagerScan(WorkGridQuery query) {
		if(CollectionUtils.isEmpty(query.getAreaCodeList())) {
			return new ArrayList<>();
		}
		if(query.getPageSize() == null
				|| query.getPageSize() <= 0) {
			query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
		}
		query.setOffset(0);
		query.setLimit(query.getPageSize());
		if(query.getPageNumber() > 0) {
			query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
		}
		return workGridDao.querySiteListForManagerScan(query);
	}
	@Override
	public List<WorkGrid> queryListForManagerSiteScan(WorkGridQuery query) {
		if(query.getSiteCode() == null 
				|| CollectionUtils.isEmpty(query.getAreaCodeList())) {
			return new ArrayList<>();
		}
		if(query.getPageSize() == null
				|| query.getPageSize() <= 0) {
			query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
		}
		query.setOffset(0);
		query.setLimit(query.getPageSize());
		if(query.getPageNumber() > 0) {
			query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
		}		
		return workGridDao.queryListForManagerSiteScan(query);
	}

	/**
	 * 根据场地获取网格信息
	 *
	 * @param siteCodes
	 * @return
	 */
	@Override
	public List<WorkGrid> getGridInfoBySiteCodes(List<String> siteCodes) {
		return workGridDao.getGridInfoBySiteCodes(siteCodes);
	}

	/**
	 * 根据唯一码查询网格信息
	 *
	 * @param query
	 * @return
	 */
	@Override
	public List<WorkGrid> queryWorkGridByBizKey(WorkGrid query) {
		return workGridDao.queryWorkGridByBizKey(query);
	}


	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridServiceImpl.updateBySiteCode", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> updateBySiteCode(WorkGrid workGrid) {
		Result<Boolean> result = Result.success();
		if (workGrid.getSiteCode() == null) {
			result.toFail("场地编码不能为空");
			return result;
		}
		result.setData(workGridDao.updateBySiteCode(workGrid));
		return result;
	}

	@Override
	public List<Integer> selectDistinctSiteCode() {
		return workGridDao.selectDistinctSiteCode();
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkGridServiceImpl.batchUpdateByIds", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public int batchUpdateByIds(WorkGridBatchUpdateRequest request) {
		return workGridDao.batchUpdateByIds(request);
	}
	@Override
	public Result<PageDto<WorkGridDeviceVo>> queryMachineListData(WorkGridQuery query) {
		Result<PageDto<WorkGridDeviceVo>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryMachineList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkGridDeviceVo> voDataList = new ArrayList<>();
		PageDto<WorkGridDeviceVo> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Result<List<WorkGridDeviceVo>> deviceResult = deviceConfigInfoJsfServiceManager.findDeviceGridByBusinessKey(query.getBusinessKey(),query.getBusinessKeyList());
		Long totalCount = 0L;
		if(deviceResult != null && !CollectionUtils.isEmpty(deviceResult.getData())){
		    voDataList = deviceResult.getData();
		    totalCount = (long) deviceResult.getData().size();
		}
		pageDto.setResult(voDataList);
		pageDto.setTotalRow(totalCount.intValue());
		result.setData(pageDto);
		return result;
	}
	@Override
	public List<String> queryGridKeyListBySiteAndArea(WorkGridQuery workGridQuery) {
		return workGridDao.queryGridKeyListBySiteAndArea(workGridQuery);
	}

	@Override
	public List<WorkGrid> batchQueryAreaWorkGrid(BatchAreaWorkGridQuery query) {
		return workGridDao.batchQueryAreaWorkGrid(query);
	}
}
