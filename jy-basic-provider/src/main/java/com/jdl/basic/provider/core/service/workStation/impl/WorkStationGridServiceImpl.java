package com.jdl.basic.provider.core.service.workStation.impl;


import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.machine.Machine;
import com.jdl.basic.api.domain.machine.WorkStationGridMachine;
import com.jdl.basic.api.domain.position.PositionRecord;
import com.jdl.basic.api.domain.tenant.JyConfigDictTenant;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.api.enums.DictCodeEnum;
import com.jdl.basic.api.enums.WorkSiteTypeEnum;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.enums.AreaEnum;
import com.jdl.basic.common.utils.CheckHelper;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringHelper;
import com.jdl.basic.provider.common.Jimdb.CacheService;
import com.jdl.basic.provider.core.components.IGenerateObjectId;
import com.jdl.basic.provider.core.dao.workStation.WorkStationGridDao;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.machine.WorkStationGridMachineService;
import com.jdl.basic.provider.core.service.position.PositionRecordService;
import com.jdl.basic.provider.core.service.tenant.JyConfigDictTenantService;
import com.jdl.basic.provider.core.service.workStation.WorkAbnormalGridBindingService;
import com.jdl.basic.provider.core.service.workStation.WorkGridService;
import com.jdl.basic.provider.core.service.workStation.WorkStationGridService;
import com.jdl.basic.provider.core.service.workStation.WorkStationService;
import com.jdl.sorting.tech.tenant.core.context.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 工序岗位网格表--Service接口实现
 *
 * @author wuyoude
 * @date 2021年12月30日 14:30:43
 *
 */
@Slf4j
@Service("workStationGridService")
public class WorkStationGridServiceImpl implements WorkStationGridService {

	@Autowired
	@Qualifier("workStationGridDao")
	private WorkStationGridDao workStationGridDao;

	@Autowired
	private WorkAbnormalGridBindingService workAbnormalGridBindingService;

	@Autowired
	@Qualifier("workStationService")
	WorkStationService workStationService;

	@Autowired
	private BaseMajorManager baseMajorManager;
	@Autowired
	private IGenerateObjectId genObjectId;

	@Autowired
	private PositionRecordService positionRecordService;

	@Autowired
	private WorkStationGridMachineService machineService;
	
	@Autowired
	@Qualifier("workGridService")
	WorkGridService workGridService;

	@Resource
	@Qualifier("JimdbCacheService")
	private CacheService cacheService;
	
	private static boolean initDataFlag = false;

	@Resource
	private JyConfigDictTenantService jyConfigDictTenantService;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	@Transactional
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.insert", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> insert(WorkStationGrid insertData){
		Result<Boolean> result = checkAndFillBeforeAdd(insertData);
		if(!result.isSuccess()) {
			return result;
		}
		insertData.setBusinessKey(generalBusinessKey());
		//保存场地网格信息，设置关联关系字段
		WorkGrid workGrid = saveWorkGird(insertData,new HashMap<>());
		setStationDataFromGrid(insertData,workGrid);
		result.setData(workStationGridDao.insert(insertData) == 1);
		if(result.getData()) {
			// 添加岗位记录
			addPosition(insertData);
		}
		return result;
	 }
	
	private void setStationDataFromGrid(WorkStationGrid stationData, WorkGrid workGrid) {
		stationData.setRefWorkGridKey(workGrid.getBusinessKey());
		if(StringUtils.isNotBlank(workGrid.getDockCode())) {
			stationData.setDockCode(workGrid.getDockCode());
		}else {
			stationData.setDockCode("");
		}
		if(StringUtils.isNotBlank(workGrid.getOwnerUserErp())) {
			stationData.setOwnerUserErp(workGrid.getOwnerUserErp());
		}else {
			stationData.setOwnerUserErp("");
		}
	}

	private WorkGrid saveWorkGird(WorkStationGrid workStationGrid,Map<String,WorkGrid> savedWorkGridMap) {
		WorkGrid workGrid = new WorkGrid();
		workGrid.setOrgCode(workStationGrid.getOrgCode());
		workGrid.setOrgName(workStationGrid.getOrgName());
		workGrid.setSiteCode(workStationGrid.getSiteCode());
		workGrid.setSiteName(workStationGrid.getSiteName());
		workGrid.setFloor(workStationGrid.getFloor());
		workGrid.setGridNo(workStationGrid.getGridNo());
		workGrid.setGridCode(workStationGrid.getGridCode());
		workGrid.setGridName(workStationGrid.getGridName());
		workGrid.setAreaCode(workStationGrid.getAreaCode());
		workGrid.setAreaName(workStationGrid.getAreaName());
		workGrid.setOwnerUserErp(workStationGrid.getOwnerUserErp());
		workGrid.setSupplierCode(workStationGrid.getSupplierCode());
		workGrid.setSupplierName(workStationGrid.getSupplierName());
		workGrid.setCreateUser(workStationGrid.getCreateUser());
		workGrid.setCreateUserName(workStationGrid.getCreateUserName());
		workGrid.setUpdateUser(workStationGrid.getUpdateUser());
		workGrid.setUpdateUserName(workStationGrid.getUpdateUserName());
		workGrid.setCreateTime(workStationGrid.getCreateTime());
		workGrid.setUpdateTime(workStationGrid.getUpdateTime());
		String uniqueGridKeysStr = workGridService.getUniqueKeysStr(workGrid);
		//判断是否已保存过
		if(savedWorkGridMap != null && savedWorkGridMap.containsKey(uniqueGridKeysStr)) {
			return savedWorkGridMap.get(uniqueGridKeysStr);
		}
		//加载站点类型信息
		WorkSiteTypeEnum siteType = null;
		BaseStaffSiteOrgDto siteInfo = this.baseMajorManager.getBaseSiteBySiteId(workStationGrid.getSiteCode());
		if(siteInfo != null) {
			siteType = WorkSiteTypeEnum.getWorkingSiteTypeByThird(siteInfo.getSortType(),siteInfo.getSortSubType(),siteInfo.getSortThirdType());
		}
		if(siteType == null) {
			siteType = WorkSiteTypeEnum.OTHER;
		}
		// fill base info
		fillSiteInfo(workGrid, siteInfo);
		workGrid.setSiteType(siteType.getCode());
		workGrid.setSiteTypeName(siteType.getName());
		Result<WorkGrid> saveResult= workGridService.saveData(workGrid);
		if(saveResult != null) {
			savedWorkGridMap.put(uniqueGridKeysStr, saveResult.getData());
			return saveResult.getData();
		}
		return null;
	}
	private void fillSiteInfo(WorkStationGrid workGrid, BaseStaffSiteOrgDto siteInfo) {
		workGrid.setSiteName(siteInfo.getSiteName());
		workGrid.setOrgCode(siteInfo == null ? -1 : siteInfo.getOrgId());
		String orgName = AreaEnum.getAreaNameByCode(siteInfo.getOrgId());
		if(orgName == null) {
			orgName = siteInfo.getOrgName();
		}
		workGrid.setOrgName(orgName);
		workGrid.setProvinceAgencyCode(siteInfo == null ? Constants.EMPTY_FILL : siteInfo.getProvinceAgencyCode());
		workGrid.setProvinceAgencyName(siteInfo == null ? Constants.EMPTY_FILL : siteInfo.getProvinceAgencyName());
		workGrid.setAreaHubCode(siteInfo == null ? Constants.EMPTY_FILL : siteInfo.getAreaCode());
		workGrid.setAreaHubName(siteInfo == null ? Constants.EMPTY_FILL : siteInfo.getAreaName());
	}
	private void fillSiteInfo(WorkGrid workGrid, BaseStaffSiteOrgDto siteInfo) {
		workGrid.setSiteName(siteInfo.getSiteName());
		workGrid.setOrgCode(siteInfo == null ? -1 : siteInfo.getOrgId());
		String orgName = AreaEnum.getAreaNameByCode(siteInfo.getOrgId());
		if(orgName == null) {
			orgName = siteInfo.getOrgName();
		}
		workGrid.setOrgName(orgName);
		workGrid.setProvinceAgencyCode(siteInfo == null ? Constants.EMPTY_FILL : siteInfo.getProvinceAgencyCode());
		workGrid.setProvinceAgencyName(siteInfo == null ? Constants.EMPTY_FILL : siteInfo.getProvinceAgencyName());
		workGrid.setAreaHubCode(siteInfo == null ? Constants.EMPTY_FILL : siteInfo.getAreaCode());
		workGrid.setAreaHubName(siteInfo == null ? Constants.EMPTY_FILL : siteInfo.getAreaName());
	}

	private String generalBusinessKey() {
		return DmsConstants.CODE_PREFIX_WORK_STATION_GRID.concat(StringHelper.padZero(this.genObjectId.getObjectId(WorkStationGrid.class.getName()),11));
	}

	private void addPosition(WorkStationGrid insertData) {
		PositionRecord record = new PositionRecord();
		record.setSiteCode(insertData.getSiteCode());
		record.setRefGridKey(insertData.getBusinessKey());
		record.setCreateUser(insertData.getCreateUser());
		record.setUpdateUser(insertData.getUpdateUser());
		Result<Integer> result = positionRecordService.insertPosition(record);
		if(result == null || !Objects.equals(result.getData(), Constants.YN_YES)){
			throw new RuntimeException("添加岗位数据失败，岗位对应的业务主键是:" + insertData.getBusinessKey());
		}
	}

	/**
	 * 校验并填充数据add
	 * @param insertData
	 * @return
	 */
	private Result<Boolean> checkAndFillBeforeAdd(WorkStationGrid insertData){
		Result<Boolean> result = checkAndFillNewData(insertData);
		if(!result.isSuccess()) {
			return result;
		}
		if(this.isExist(insertData)) {
			return result.toFail("该场地网格已存在，请修改！");
		}
		return result;
	}
	/**
	 * 校验并填充数据
	 * @param data
	 * @return
	 */
	private Result<Boolean> checkAndFillNewData(WorkStationGrid data){
		Result<Boolean> result = Result.success();
		Integer siteCode = data.getSiteCode();
		Integer floor = data.getFloor();
		String gridNo = data.getGridNo();
		String workCode = data.getWorkCode();
		String areaCode = data.getAreaCode();
		String ownerUserErp = data.getOwnerUserErp();
		Integer standardNum = data.getStandardNum();
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
		if(!CheckHelper.checkStr("工序ID", workCode, 50, result).isSuccess()) {
			return result;
		}
		if(!CheckHelper.checkInteger("编制人数", standardNum, 1,1000000, result).isSuccess()) {
			return result;
		}
		BaseStaffSiteOrgDto siteInfo = baseMajorManager.getBaseSiteBySiteId(siteCode);
		if(siteInfo == null) {
			return result.toFail("青龙ID在基础资料中不存在！");
		}
		//todo 数据隔离区分场地类型，租户目前是入参获取的
		WorkSiteTypeEnum currWorkSiteTypeEnum = WorkSiteTypeEnum.getWorkingSiteTypeByThird(siteInfo.getSortType(),siteInfo.getSortSubType(),siteInfo.getSortThirdType());
		if(currWorkSiteTypeEnum != null){
			JyConfigDictTenant dataBaseTenant = jyConfigDictTenantService.getTenantByDictCodeAndValue(DictCodeEnum.TENANT_SITE_TYPE.getCode(),String.valueOf(currWorkSiteTypeEnum.getCode()));
			if(dataBaseTenant == null || !Objects.equals(TenantContext.getTenantCode(),dataBaseTenant.getBelongTenantCode())){
				return result.toFail("当前用户没有" + siteInfo.getSiteName() + "的操作权限！");
			}
		}
		fillSiteInfo(data, siteInfo);

		WorkStation workStationCheckQuery = new WorkStation();
		workStationCheckQuery.setWorkCode(workCode);
		workStationCheckQuery.setAreaCode(areaCode);
		Result<WorkStation> workStationData = workStationService.queryByBusinessKey(workStationCheckQuery);
		if(workStationData == null
				|| workStationData.getData() == null) {
			return result.toFail("作业区工序信息不存在，请先维护作业区及工序信息！");
		}
		WorkStation workStation = workStationData.getData();
		data.setAreaName(workStation.getAreaName());
		data.setGridCode(workStation.getAreaCode().concat("-").concat(data.getGridNo()));
		data.setGridName(workStation.getAreaName().concat("-").concat(data.getGridNo()));
		data.setRefStationKey(workStation.getBusinessKey());
		return result;
	}
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	@Transactional
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.updateById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> updateById(WorkStationGrid updateData){
		Result<Boolean> result = Result.success();
		String ownerUserErp = updateData.getOwnerUserErp();
		Integer standardNum = updateData.getStandardNum();
		if(!CheckHelper.checkInteger("编制人数", standardNum, 1,1000000, result).isSuccess()) {
			return result;
		}
		if(!CheckHelper.checkStr("负责人ERP", ownerUserErp, 50, result).isSuccess()) {
			return result;
		}
		WorkStationGrid oldData = workStationGridDao.queryById(updateData.getId());
		if(oldData == null) {
			return result.toFail("该网格数据已变更，请重新查询后修改！");
		}
		BaseStaffSiteOrgDto siteInfo = baseMajorManager.getBaseSiteBySiteId(updateData.getSiteCode());
		if(siteInfo == null) {
			return result.toFail("网格所属站点在基础资料中不存在！");
		}
		fillSiteInfo(updateData, siteInfo);
		WorkGrid workGrid = this.saveWorkGird(updateData,new HashMap<>());
		if(workGrid == null) {
			return result.toFail("网格数据修改失败！");
		}
		setStationDataFromGrid(updateData,workGrid);
		workStationGridDao.deleteById(updateData);
		updateData.setId(null);
		result.setData(workStationGridDao.insert(updateData) == 1);

		// 清除网格工序缓存
		invalidateWorkStationGridCache(oldData.getBusinessKey());

		return result;
	}

	private void invalidateWorkStationGridCache(String businessKey) {
		String cacheKey = "WorkStationGridDao.queryWorkStationGridBybusinessKeyWithCache" + businessKey;
		cacheService.del(cacheKey);
	}

	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	@Transactional
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.deleteById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> deleteById(WorkStationGrid deleteData){
		Result<Boolean> result = Result.success();
		Result<WorkStationGrid> queryResult = queryById(deleteData.getId());
		if(queryResult.getData() == null || StringUtils.isEmpty(queryResult.getData().getBusinessKey())){
			throw new RuntimeException("根据id:" + deleteData.getId() + "未查询到数据!");
		}
		result.setData(workStationGridDao.deleteById(deleteData) == 1);

		// 清除网格工序缓存
		invalidateWorkStationGridCache(queryResult.getData().getBusinessKey());

		// 同步删除异常网格绑定数据
		deleteWorkAbnormalGridBinding(queryResult.getData(),deleteData);

		//删除对应的网格数据
		WorkGrid deleteGrid = new WorkGrid();
		deleteGrid.setBusinessKey(queryResult.getData().getRefWorkGridKey());
		deleteGrid.setUpdateUser(deleteData.getUpdateUser());
		deleteGrid.setUpdateUserName(deleteData.getUpdateUserName());
		deleteGrid.setUpdateTime(deleteData.getUpdateTime());
		workGridService.deleteByWorkGridKey(deleteGrid);
		// 同步删除岗位记录
		String businessKey = queryResult.getData().getBusinessKey();
		PositionRecord positionRecord = new PositionRecord();
		positionRecord.setRefGridKey(businessKey);
		positionRecord.setUpdateUser(deleteData.getUpdateUser());
		Result<Boolean> deletePositionResult = positionRecordService.deleteByBusinessKey(positionRecord);
		if(Objects.equals(deletePositionResult.getData(), false)){
			throw new RuntimeException("根据businessKey:" + businessKey + "删除岗位数据失败!");
		}
		return result;
	 }

	private void deleteWorkAbnormalGridBinding(WorkStationGrid data, WorkStationGrid deleteData) {
		WorkStationBinding delete = new WorkStationBinding();
		delete.setGridCode(data.getGridCode());
		delete.setFloor(data.getFloor());
		delete.setSiteCode(data.getSiteCode());
		delete.setUpdateUser(deleteData.getUpdateUser());
		delete.setUpdateUserName(deleteData.getUpdateUserName());
		delete.setUpdateTime(new Date());
		workAbnormalGridBindingService.deleteByGrid(delete);
	}


	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<WorkStationGrid> queryById(Long id){
		Result<WorkStationGrid> result = Result.success();
		result.setData(workStationGridDao.queryById(id));
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryPageList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<PageDto<WorkStationGrid>> queryPageList(WorkStationGridQuery query){
		Result<PageDto<WorkStationGrid>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		PageDto<WorkStationGrid> pageData = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workStationGridDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
			List<WorkStationGrid> grids = workStationGridDao.queryList(query);
			// 查询关联的自动化设备
			HashMap<String, List<Machine>> machineMap = machineService.getMachineListByRefGridKey(grids);
			for (WorkStationGrid grid : grids) {
				grid.setMachine(machineMap.get(grid.getBusinessKey()));
			}
			pageData.setResult(grids);
			pageData.setTotalRow(totalCount.intValue());
		}else {
			pageData.setResult(new ArrayList<WorkStationGrid>());
			pageData.setTotalRow(0);
		}
		result.setData(pageData);
		return result;
	 }
	/**
	 * 查询参数校验
	 * @param query
	 * @return
	 */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.checkParamForQueryPageList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> checkParamForQueryPageList(WorkStationGridQuery query){
		Result<Boolean> result = Result.success();
		if(query.getPageSize() == null
				|| query.getPageSize() <= 0) {
			query.setPageSize(DmsConstants.PAGE_SIZE_DEFAULT);
		}
		query.setOffset(0);
		query.setLimit(query.getPageSize());
		if(query.getPageNumber() > 0) {
			query.setOffset((query.getPageNumber() - 1) * query.getPageSize());
		}

		return result;
	 }

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryAllGridBySiteCode", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<List<WorkStationGrid>> queryAllGridBySiteCode(WorkStationGridQuery query) {
		Result<List<WorkStationGrid>> result = Result.success();
		result.setData(workStationGridDao.queryAllGridBySiteCode(query));
		return result;
	}

	@Transactional
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.importDatas", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> importDatas(List<WorkStationGrid> dataList) {
		Result<Boolean> result = checkAndFillImportDatas(dataList);
		if(!result.isSuccess()) {
			return result;
		}
		//存储已保存的WorkGrid数据
		Map<String,WorkGrid> savedWorkGridMap = new HashMap<>();
		//先删除后插入新记录
		for(WorkStationGrid data : dataList) {
			WorkStationGrid oldData = workStationGridDao.queryByBusinessKey(data);
			if(oldData != null) {
				oldData.setUpdateUser(data.getCreateUser());
				oldData.setUpdateUserName(data.getCreateUserName());
				oldData.setUpdateTime(data.getCreateTime());
				if(!Objects.equals(workStationGridDao.deleteById(oldData), Constants.YN_YES)){
					throw  new RuntimeException("根据id:" + oldData.getId() + "删除数据失败!");
				}
				data.setBusinessKey(oldData.getBusinessKey());
			}else {
				data.setBusinessKey(generalBusinessKey());
			}
			WorkGrid workGrid = this.saveWorkGird(data,savedWorkGridMap);
			if(workGrid == null) {
				throw  new RuntimeException("网格数据导入失败！");
			}
			setStationDataFromGrid(data,workGrid);
			if(!Objects.equals(workStationGridDao.insert(data), Constants.YN_YES)){
				throw  new RuntimeException("新增businessKey为:" + data.getBusinessKey() + "的数据失败");
			}
			// 同步处理岗位
			syncDealPosition(oldData, data);
		}
		return result;
	}

	private void syncDealPosition(WorkStationGrid oldData, WorkStationGrid newData) {
		if(oldData == null){
			addPosition(newData);
		}
	}

	/**
	 * 校验并填充导入数据
	 * @param dataList
	 * @return
	 */
	private Result<Boolean> checkAndFillImportDatas(List<WorkStationGrid> dataList){
		Result<Boolean> result = Result.success();
		if(CollectionUtils.isEmpty(dataList)) {
			return result.toFail("导入数据为空！");
		}
		//逐条校验
		int rowNum = 1;
		Map<String,Integer> uniqueKeysRowNumMap = new HashMap<String,Integer>();
		for(WorkStationGrid data : dataList) {
			String rowKey = "第" + rowNum + "行";
			Result<Boolean> result0 = checkAndFillNewData(data);
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
	private String getUniqueKeysStr(WorkStationGrid data) {
		if(data != null ) {
			return data.getSiteCode().toString()
					.concat(DmsConstants.KEYS_SPLIT)
					.concat(data.getFloor().toString())
					.concat(DmsConstants.KEYS_SPLIT)
					.concat(data.getGridNo())
					.concat(DmsConstants.KEYS_SPLIT)
					.concat(data.getRefStationKey());
		}
		return null;
	}
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryByBusinessKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<WorkStationGrid> queryByBusinessKey(WorkStationGrid data) {
		Result<WorkStationGrid> result = Result.success();
		result.setData(workStationGridDao.queryByBusinessKey(data));
		return result;
	}
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryByBusinessKeyWithCache", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	@Cache(key = "WorkStationGridService.queryByBusinessKeyWithCache@args0", memoryEnable = true, memoryExpiredTime = 2 * 60 * 1000
	,redisEnable = true, redisExpiredTime = 2 * 60 * 1000)
	public WorkStationGrid queryByBusinessKeyWithCache(WorkStationGrid data) {
		return workStationGridDao.queryByBusinessKey(data);
	}
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.isExist", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public boolean isExist(WorkStationGrid data) {
		return workStationGridDao.queryByBusinessKey(data) != null;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryGridWorkDictList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<List<WorkStationGrid>> queryGridWorkDictList(WorkStationGridQuery query) {
		Result<List<WorkStationGrid>> result = Result.success();
		result.setData(workStationGridDao.queryGridWorkDictList(query));
		return result;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryGridAreaDictList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<List<WorkStationGrid>> queryGridAreaDictList(WorkStationGridQuery query) {
		Result<List<WorkStationGrid>> result = Result.success();
		result.setData(workStationGridDao.queryGridAreaDictList(query));
		return result;
	}
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryGridFloorDictList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<List<WorkStationGrid>> queryGridNoDictList(WorkStationGridQuery query) {
		Result<List<WorkStationGrid>> result = Result.success();
		result.setData(workStationGridDao.queryGridNoDictList(query));
		return result;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryGridFloorDictList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<List<WorkStationGrid>> queryGridFloorDictList(WorkStationGridQuery query) {
		Result<List<WorkStationGrid>> result = Result.success();
		result.setData(workStationGridDao.queryGridFloorDictList(query));
		return result;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryPageCount", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<WorkStationGridCountVo> queryPageCount(WorkStationGridQuery query) {
		Result<WorkStationGridCountVo> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		result.setData(workStationGridDao.queryPageCount(query));
		return result;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.hasGridData", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public boolean hasGridData(String stationKey) {
		return workStationGridDao.queryCountByRefStationKey(stationKey) > 0;
	}
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.deleteByIds", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	@Transactional
	public Result<Boolean> deleteByIds(DeleteRequest<WorkStationGrid> deleteRequest) {
		Result<Boolean> result = Result.success();
		if(deleteRequest == null
				|| CollectionUtils.isEmpty(deleteRequest.getDataList())) {
			return result.toFail("参数错误，删除列表不能为空！");
		}
		List<WorkStationGrid> oldDataList = workStationGridDao.queryByIds(deleteRequest);
		if(CollectionUtils.isEmpty(oldDataList)
				|| oldDataList.size() < deleteRequest.getDataList().size()) {
			return result.toFail("参数错误，数据已变更请刷新列表后重新选择！");
		}
		for(WorkStationGrid oldData : oldDataList) {
			if(!ObjectUtils.equals(oldData.getSiteCode(), deleteRequest.getOperateSiteCode())) {
				return result.toFail("网格【"+oldData.getGridName()+ "】非本人所在场地数据，无法删除！");
			}
		}
		result.setData(workStationGridDao.deleteByIds(deleteRequest) > 0);
		List<String> refGridKeyList = new ArrayList<>();
		for(WorkStationGrid oldData : oldDataList) {
			// 同步删除岗位记录
			String businessKey = oldData.getBusinessKey();
			PositionRecord positionRecord = new PositionRecord();
			positionRecord.setRefGridKey(businessKey);
			positionRecord.setUpdateUser(deleteRequest.getOperateUserCode());
			Result<Boolean> deletePositionResult = positionRecordService.deleteByBusinessKey(positionRecord);
			if(Objects.equals(deletePositionResult.getData(), false)){
				throw new RuntimeException("根据businessKey:" + businessKey + "删除岗位数据失败!");
			}
			refGridKeyList.add(oldData.getRefWorkGridKey());
		}
		//删除对应的网格数据
		for(String workGridKey : refGridKeyList) {
			WorkGrid deleteGrid = new WorkGrid();
			deleteGrid.setBusinessKey(workGridKey);
			deleteGrid.setUpdateUser(deleteRequest.getOperateUserCode());
			deleteGrid.setUpdateUserName(deleteRequest.getOperateUserName());
			deleteGrid.setUpdateTime(deleteRequest.getOperateTime());
			workGridService.deleteByWorkGridKey(deleteGrid);
		}
		return result;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryCount", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Long> queryCount(WorkStationGridQuery query) {
		Result<Long> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		result.setData(workStationGridDao.queryCount(query));
		return result;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryListForExport", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<List<WorkStationGrid>> queryListForExport(WorkStationGridQuery query) {
		Result<List<WorkStationGrid>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkStationGrid> grids = workStationGridDao.queryListForExport(query);
		// 查询关联的自动化设备
		HashMap<String, List<Machine>> machineMap = machineService.getMachineListByRefGridKey(grids);
		for (WorkStationGrid grid : grids) {
			grid.setMachine(machineMap.get(grid.getBusinessKey()));
		}
		result.setData(grids);
		return result;
	}
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.querySiteListByOrgCode", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public List<WorkStationGrid> querySiteListByOrgCode(WorkStationGridQuery query) {
		return workStationGridDao.querySiteListByOrgCode(query);
	}
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryOwnerUserErpListBySiteCode", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public List<String> queryOwnerUserErpListBySiteCode(WorkStationGridQuery query) {
		return workStationGridDao.queryOwnerUserErpListBySiteCode(query);
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryByGridKeyWithCache", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	@Cache(key = "WorkStationGridService.queryByGridKeyWithCache@args0", memoryEnable = true, memoryExpiredTime = 2 * 60 * 1000
	,redisEnable = true, redisExpiredTime = 2 * 60 * 1000)
	public WorkStationGrid queryByGridKeyWithCache(WorkStationGridQuery workStationGridQuery) {
		return workStationGridDao.queryByGridKey(workStationGridQuery);
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryWorkStationGridBybusinessKeyWithCache", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<WorkStationGrid> queryWorkStationGridBybusinessKeyWithCache(String businessKey) {
		Result<WorkStationGrid> result = Result.success();
		result.setData(workStationGridDao.queryWorkStationGridBybusinessKeyWithCache(businessKey));
		return result;
	}

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryAllWorkGridList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<PageDto<WorkStationGrid>> queryAllWorkGridList(WorkStationGridQuery query) {
        Result<PageDto<WorkStationGrid>> result = Result.success();
        Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
        if(!checkResult.isSuccess()){
            return Result.fail(checkResult.getMessage());
        }
        PageDto<WorkStationGrid> pageData = new PageDto<>(query.getPageNumber(), query.getPageSize());
        Long totalCount = workStationGridDao.queryCount(query);
        if(totalCount != null && totalCount > 0){
            pageData.setResult(workStationGridDao.queryList(query));
            pageData.setTotalRow(totalCount.intValue());
        }else {
            pageData.setResult(new ArrayList<WorkStationGrid>());
            pageData.setTotalRow(0);
        }
        result.setData(pageData);
        return result;
    }
	@Override
	public void stopInit() {
		initDataFlag = false;
	}
	@Override
	public void initAllWorkGrid() {
		initDataFlag = true;
		int pageNum = 1;
		WorkStationGridQuery query = new WorkStationGridQuery();
		List<WorkStationGrid> dataList = null;
		do {
			query.setPageNumber(pageNum);
			query.setPageSize(50);
			dataList = workStationGridDao.queryList(query);
			if(dataList != null) {
				if((!CollectionUtils.isEmpty(dataList))) {
					for(WorkStationGrid data : dataList) {
						if(StringUtils.isBlank(data.getAreaCode())) {
							log.warn("网格数据无效areaCode值为空！"+data.getId());
							continue;
						}
						if(StringUtils.isNotBlank(data.getRefWorkGridKey())) {
							log.warn("网格数据无效refWorkGridKey值已存在，不处理！"+data.getId());
							continue;
						}
						if(initDataFlag) {
							log.warn("initWorkGrid："+data.getId());
							initWorkGrid(data);
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}else {
							log.warn("initAllWorkGrid-stop！"+data.getId());
							break;
						}
					}
				}
			}
			pageNum++;
		}while(initDataFlag && !CollectionUtils.isEmpty(dataList));	
		initDataFlag = false;
	}

	@Override
	public void initWorkGrid(Long id) {
		this.initWorkGrid(workStationGridDao.queryById(id));
	}
    private void initWorkGrid(WorkStationGrid data) {
    	WorkGrid workGrid = this.saveWorkGird(data,new HashMap<>());
		if(workGrid != null) {
			setStationDataFromGrid(data,workGrid);
			this.workStationGridDao.updateById(data);
		}else {
			log.warn("initAllWorkGrid-失败！"+data.getId());
			log.warn("initAllWorkGrid-失败！"+JsonHelper.toJSONString(data));
		}
    }

	@Override
	public List<WorkStationGrid> queryListForWorkGridVo(WorkStationGridQuery query) {
		return workStationGridDao.queryListForWorkGridVo(query);
	}

	@Override
	public List<Integer> querySiteListForManagerScan(WorkStationGridQuery query) {
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
		return workStationGridDao.querySiteListForManagerScan(query);
	}

	@Override
	public List<WorkStationGrid> queryListForManagerSiteScan(WorkStationGridQuery query) {
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
		return workStationGridDao.queryListForManagerSiteScan(query);
	}
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.queryCountByRefGridKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public int queryCountByRefGridKey(String refGridKey) {
		return workStationGridDao.queryCountByRefGridKey(refGridKey);
	}

	/**
	 * 获取月台号
	 *
	 * @param workGridFlowDirectionQuery
	 */
	@Override
	public List<String> queryDockCodeByFlowDirection(WorkGridFlowDirectionQuery workGridFlowDirectionQuery) {
		return workStationGridDao.queryDockCodeByFlowDirection(workGridFlowDirectionQuery);
	}

	/**
	 * 获取网格信息
	 *
	 * @param workGridFlowDirectionQuery
	 */
	@Override
	public List<WorkStationGrid> queryPhoneByDockCodeForTms(WorkGridFlowDirectionQuery workGridFlowDirectionQuery) {
		return workStationGridDao.queryPhoneByDockCodeForTms(workGridFlowDirectionQuery);
	}
	/**
	 * 更新网格下工序数据
	 * @param updateData
	 * @return
	 */
	@Transactional
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridServiceImpl.syncWorkGridInfo", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public int syncWorkGridInfo(WorkGrid gridData){
		WorkStationGrid updateStationData = new WorkStationGrid();
		//设置需要更新工序的字段
		if(StringUtils.isNotBlank(gridData.getDockCode())) {
			updateStationData.setDockCode(gridData.getDockCode());
		}else {
			updateStationData.setDockCode("");
		}
		if(StringUtils.isNotBlank(gridData.getOwnerUserErp())) {
			updateStationData.setOwnerUserErp(gridData.getOwnerUserErp());
		}else {
			updateStationData.setOwnerUserErp("");
		}		
		updateStationData.setRefWorkGridKey(gridData.getBusinessKey());
		updateStationData.setUpdateTime(new Date());
		updateStationData.setUpdateUser(gridData.getUpdateUser());
		updateStationData.setUpdateUserName(gridData.getUpdateUserName());
		return workStationGridDao.syncWorkGridInfo(updateStationData);
	}
}
