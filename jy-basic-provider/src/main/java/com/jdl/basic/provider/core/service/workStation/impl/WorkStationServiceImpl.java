package com.jdl.basic.provider.core.service.workStation.impl;


import com.google.common.collect.Lists;
import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.tenant.JyConfigDictTenant;
import com.jdl.basic.api.domain.workStation.*;
import com.jdl.basic.api.enums.BusinessLineTypeEnum;
import com.jdl.basic.api.enums.DictCodeEnum;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.contants.DmsConstants;
import com.jdl.basic.common.utils.CheckHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringHelper;
import com.jdl.basic.provider.core.components.IGenerateObjectId;
import com.jdl.basic.provider.core.dao.workStation.WorkStationDao;
import com.jdl.basic.provider.core.dao.workStation.WorkStationGridDao;
import com.jdl.basic.provider.core.dao.workStation.WorkStationJobTypeDao;
import com.jdl.basic.provider.core.po.WorkStationJobTypePO;
import com.jdl.basic.provider.core.service.tenant.JyConfigDictTenantService;
import com.jdl.basic.provider.core.service.workStation.WorkAreaService;
import com.jdl.basic.provider.core.service.workStation.WorkStationGridService;
import com.jdl.basic.provider.core.service.workStation.WorkStationService;
import com.jdl.sorting.tech.tenant.core.context.TenantContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: WorkStationServiceImpl
 * @Description: 网格工序信息表--Service接口实现
 * @author wuyoude
 * @date 2022年02月23日 11:01:53
 *
 */
@Service("workStationService")
public class WorkStationServiceImpl implements WorkStationService {

	private static final Logger logger = LoggerFactory.getLogger(WorkStationServiceImpl.class);

	@Autowired
	@Qualifier("workStationDao")
	private WorkStationDao workStationDao;

	@Autowired
	private IGenerateObjectId genObjectId;
	@Autowired
	@Qualifier("workStationGridService")
	private WorkStationGridService workStationGridService;
	@Autowired
	private WorkStationJobTypeDao workStationJobTypeDao;
	
	@Autowired
	@Qualifier("workAreaService")
	private WorkAreaService workAreaService;

	@Resource
	private JyConfigDictTenantService jyConfigDictTenantService;

	@Autowired
	private WorkStationGridDao workStationGridDao;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	@Override
	@Transactional
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.insert", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> insert(WorkStation insertData){
		Result<Boolean> result = checkAndFillBeforeAdd(insertData);
		if(!result.isSuccess()) {
			return result;
		}
		generateAndSetBusinessKey(insertData);
		//判断有无设置工种类型 并设置
		if(CollectionUtils.isNotEmpty(insertData.getJobTypes())){
			insertData.setHaveJobType(1);
		}
		insertWorkStationJobTypePO(insertData,insertData.getBusinessKey());
		result.setData(workStationDao.insert(insertData) == 1);
		//新增成功，同步保存workArea
		if(result.getData()) {
			saveWorkArea(insertData);
		}
		return result;
	 }

	/**
	 * 组装网格工序工种数据并插入
	 */
	private int insertWorkStationJobTypePO(WorkStation dto ,String businessKey){
		if(CollectionUtils.isEmpty(dto.getJobTypes())){
			return 0;
		}
		List<WorkStationJobTypePO> pos = new ArrayList<>();

		dto.getJobTypes().stream().forEach(item ->{
			if(StringUtils.isBlank(businessKey) || Objects.isNull(item)){
				throw new RuntimeException("添加网格工序工种异常,请检查后重新操作!");
			}
			WorkStationJobTypePO jobTypePO = new WorkStationJobTypePO();
			jobTypePO.setRefBusinessKey(businessKey);
			jobTypePO.setJobCode(item);
			jobTypePO.setCreateUser(dto.getCreateUser());
			jobTypePO.setCreateTime(new Date());
			pos.add(jobTypePO);
		});
		return workStationJobTypeDao.batchInsert(pos);
	 }
	private boolean saveWorkArea(WorkStation workStation) {
		WorkArea workArea = new WorkArea();
		workArea.setAreaCode(workStation.getAreaCode());
		workArea.setAreaName(workStation.getAreaName());
		workArea.setBusinessLineCode(workStation.getBusinessLineCode());
		workArea.setBusinessLineName(workStation.getBusinessLineName());
		workArea.setAreaType(workStation.getAreaType());
		workArea.setCreateUser(workStation.getCreateUser());
		workArea.setCreateUserName(workStation.getCreateUserName());
		workArea.setUpdateUser(workStation.getUpdateUser());
		workArea.setUpdateUserName(workStation.getUpdateUserName());
		workArea.setCreateTime(workStation.getCreateTime());
		workArea.setUpdateTime(workStation.getUpdateTime());
		workAreaService.saveData(workArea);
		return true;
	}
	@Override
	@Transactional
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.importDatas", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> importDatas(List<WorkStation> dataList) {
		Result<Boolean> result = checkImportDatas(dataList);
		if(!result.isSuccess()) {
			return result;
		}
		//先删除后插入新记录
		for(WorkStation data : dataList) {
			WorkStation oldData = workStationDao.queryByBusinessKey(data);
			if(oldData != null) {
				oldData.setUpdateUser(data.getCreateUser());
				oldData.setUpdateUserName(data.getCreateUserName());
				oldData.setUpdateTime(data.getCreateTime());
				workStationDao.deleteById(oldData);
				data.setBusinessKey(oldData.getBusinessKey());
			}else {
				generateAndSetBusinessKey(data);
			}
			//判断有无设置工种类型 并设置
			if(CollectionUtils.isNotEmpty(data.getJobTypes())){
				data.setHaveJobType(1);
			}else {
				data.setHaveJobType(0);
			}
			//删除所有老的与businessKey关联的JobType 然后再插入
			workStationJobTypeDao.deleteByRefBusinessKey(data.getBusinessKey());
			insertWorkStationJobTypePO(data,data.getBusinessKey());
			workStationDao.insert(data);
			saveWorkArea(data);
		}
		return result;
	}
	/**
	 * 校验并填充数据add
	 * @param insertData
	 * @return
	 */
	private Result<Boolean> checkAndFillBeforeAdd(WorkStation insertData){
		Result<Boolean> result = checkAndFillNewData(insertData);
		if(!result.isSuccess()) {
			return result;
		}
		if(this.isExist(insertData)) {
			return result.toFail("该作业区、工序已存在，请修改！");
		}
		return result;
	}
	/**
	 * 校验并填充导入数据
	 * @param dataList
	 * @return
	 */
	private Result<Boolean> checkImportDatas(List<WorkStation> dataList){
		Result<Boolean> result = Result.success();
		if(CollectionUtils.isEmpty(dataList)) {
			return result.toFail("导入数据为空！");
		}
		//逐条校验
		int rowNum = 1;
		Map<String,Integer> uniqueKeysRowNumMap = new HashMap<String,Integer>();
		for(WorkStation data : dataList) {
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
	private String getUniqueKeysStr(WorkStation data) {
		if(data != null ) {
			return data.getAreaCode()
					.concat(DmsConstants.KEYS_SPLIT)
					.concat(data.getWorkCode());
		}
		return null;
	}
	/**
	 * 校验并填充数据
	 * @param data
	 * @return
	 */
	private Result<Boolean> checkAndFillNewData(WorkStation data){
		Result<Boolean> result = Result.success();
		String workCode = data.getWorkCode();
		String workName = data.getWorkName();
		String areaCode = data.getAreaCode();
		String areaName = data.getAreaName();
		Integer standardNum = data.getStandardNum();
		if(!CheckHelper.checkStr("作业区ID", areaCode, 50, result).isSuccess()) {
			return result;
		}
		if(!CheckHelper.checkStr("作业区名称", areaName, 100, result).isSuccess()) {
			return result;
		}
		if(!CheckHelper.checkStr("工序ID", workCode, 50, result).isSuccess()) {
			return result;
		}
		if(!CheckHelper.checkStr("工序名称", workName, 100, result).isSuccess()) {
			return result;
		}
		if(!CheckHelper.checkInteger("编制人数", standardNum, result).isSuccess()) {
			return result;
		}
		//业务条线必填
		String businessLineCode = data.getBusinessLineCode();
		if(StringUtils.isBlank(businessLineCode)){
			result.toFail("业务条线不能为空");
			return result;
		}
		//先校验业务条线枚举存不存在
		BusinessLineTypeEnum currBusiEnum = BusinessLineTypeEnum.getEnum(businessLineCode);
		if(currBusiEnum == null){
			result.toFail("业务条线不合法");
			return result;
		}
		//查询租户配置表，校验业务条线是否属于该租户权限
		if(StringUtils.isNotBlank(TenantContext.getTenantCode())){
			JyConfigDictTenant dataBaseTenant = jyConfigDictTenantService.getTenantByDictCodeAndValue(DictCodeEnum.TENANT_BUSINESS_LINE.getCode(), businessLineCode);
			if(dataBaseTenant == null || !Objects.equals(TenantContext.getTenantCode(),dataBaseTenant.getBelongTenantCode())){
				result.toFail("当前用户没有" + currBusiEnum.getName() + "的操作权限");
				return result;
			}
		}
		data.setBusinessLineName(currBusiEnum.getName());
		return result;
	}
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.updateById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> updateById(WorkStation updateData){
		Result<Boolean> result = checkAndFillNewData(updateData);
		if(!result.isSuccess()) {
			return result;
		}
		WorkStation oldData = workStationDao.queryById(updateData.getId());
		if(oldData == null) {
			return result.toFail("该工序数据已变更，请重新查询后修改！");
		}
		workStationDao.deleteById(updateData);
		updateData.setId(null);
		//判断有无设置工种类型 并设置
		if(CollectionUtils.isNotEmpty(updateData.getJobTypes())){
			updateData.setHaveJobType(1);
		}else {
			updateData.setHaveJobType(0);
		}

		//删除所有老的与businessKey关联的JobType 然后再插入
		workStationJobTypeDao.deleteByRefBusinessKey(updateData.getBusinessKey());
		insertWorkStationJobTypePO(updateData,updateData.getBusinessKey());
		result.setData(workStationDao.insert(updateData) == 1);
		if(result.getData()) {
			saveWorkArea(updateData);
		}
		// 工序的标准编制人数同步到 场地网格工序的编制人数
		List<WorkStationGrid> workStationGrids = workStationGridDao.queryListByRefStationKey(updateData.getBusinessKey());
		if (CollectionUtils.isNotEmpty(workStationGrids)){
			UpdateRequest<WorkStationGrid> updateRequest = getWorkStationGridUpdateRequest(updateData, workStationGrids);
			workStationGridDao.updateStandardNumByIds(updateRequest);
		}
		return result;
	 }

	private UpdateRequest<WorkStationGrid> getWorkStationGridUpdateRequest(WorkStation updateData, List<WorkStationGrid> workStationGrids) {
		UpdateRequest<WorkStationGrid> updateRequest = new UpdateRequest<>();
		updateRequest.setOperateUserCode(updateData.getUpdateUser());
		updateRequest.setOperateUserName(updateData.getUpdateUserName());
		updateRequest.setOperateTime(updateData.getUpdateTime());
		updateRequest.setStandardNum(updateData.getStandardNum());
		ArrayList<WorkStationGrid> list = new ArrayList<>();
		for (WorkStationGrid workStationGrid : workStationGrids) {
			WorkStationGrid wsg = new WorkStationGrid();
			wsg.setId(workStationGrid.getId());
			list.add(wsg);
		}
		updateRequest.setDataList(list);
		return updateRequest;
	}

	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.deleteById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> deleteById(WorkStation deleteData){
		Result<Boolean> result = Result.success();
		if(deleteData == null
				|| deleteData.getId() == null) {
			return result.toFail("参数错误，id不能为空！");
		}
		WorkStation oldData = workStationDao.queryById(deleteData.getId());
		if(oldData == null) {
			return result.toFail("参数错误，无效的数据id！");
		}
		if(workStationGridService.hasGridData(oldData.getBusinessKey())) {
			return result.toFail("该工序存在场地网格记录，无法删除！");
		}
		//删除所有与businessKey关联的JobType
		workStationJobTypeDao.deleteByRefBusinessKey(oldData.getBusinessKey());
		result.setData(workStationDao.deleteById(deleteData) == 1);
		return result;
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.queryById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<WorkStation> queryById(Long id){
		Result<WorkStation> result = Result.success();
		WorkStation workStation = workStationDao.queryById(id);
		//判断是否已经维护工种
		if(Objects.equals(workStation.getHaveJobType(),1)){
			List<WorkStationJobTypePO> jobTypes = workStationJobTypeDao.selectByBusinessKey(workStation.getBusinessKey());
			if(CollectionUtils.isNotEmpty(jobTypes)){
				List<Integer> types = jobTypes.stream().map(WorkStationJobTypePO::getJobCode).collect(Collectors.toList());
				workStation.setJobTypes(types);
			}
		}
		result.setData(workStation);
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.queryPageList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<PageDto<WorkStation>> queryPageList(WorkStationQuery query){
		Result<PageDto<WorkStation>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		PageDto<WorkStation> pageData = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workStationDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
			List<WorkStation> workStations = workStationDao.queryList(query);
			//查询工种信息补充到返回值中
			if(CollectionUtils.isNotEmpty(workStations)){
				workStations.stream().forEach(item ->{
					List<WorkStationJobTypePO> jobTypes = workStationJobTypeDao.selectByBusinessKey(item.getBusinessKey());
					if(CollectionUtils.isNotEmpty(jobTypes)){
						List<Integer> types = jobTypes.stream().map(WorkStationJobTypePO::getJobCode).collect(Collectors.toList());
						item.setJobTypes(types);
					}
				});
			}
			pageData.setResult(workStations);
			pageData.setTotalRow(totalCount.intValue());
		}else {
			pageData.setResult(new ArrayList<WorkStation>());
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
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.checkParamForQueryPageList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> checkParamForQueryPageList(WorkStationQuery query){
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
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.queryWorkDictList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<List<WorkStation>> queryWorkDictList(WorkStationQuery query) {
		Result<List<WorkStation>> result = Result.success();
		result.setData(workStationDao.queryWorkDictList(query));
		return result;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.queryAreaDictList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<List<WorkStation>> queryAreaDictList(WorkStationQuery query) {
		Result<List<WorkStation>> result = Result.success();
		result.setData(workStationDao.queryAreaDictList(query));
		return result;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.queryByBusinessKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<WorkStation> queryByBusinessKey(WorkStation data) {
		Result<WorkStation> result = Result.success();
		result.setData(workStationDao.queryByBusinessKey(data));
		return result;
	}
    /**
     * 根据业务主键查询
     *
     * @param data
     * @return
     */
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.queryByBusinessKeyWithCache", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})	
    @Cache(key = "WorkStationDao.queryByBusinessKeyWithCache@args0", memoryEnable = true, memoryExpiredTime = 2 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 2 * 60 * 1000)
    public WorkStation queryByBusinessKeyWithCache(WorkStation data) {
    	return workStationDao.queryByBusinessKey(data);
    }
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.queryByRealBusinessKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<WorkStation> queryByRealBusinessKey(String businessKey) {
		Result<WorkStation> result = Result.success();
		if(StringUtils.isEmpty(businessKey)){
			result.toFail("业务主键不能为空!");
			return result;
		}
		result.setData(workStationDao.queryByRealBusinessKey(businessKey));
		return result;
	}
	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.queryByRealBusinessKeyWithCache", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    @Cache(key = "WorkStationDao.queryByRealBusinessKeyWithCache@args0", memoryEnable = true, memoryExpiredTime = 2 * 60 * 1000
    ,redisEnable = true, redisExpiredTime = 2 * 60 * 1000)
	public WorkStation queryByRealBusinessKeyWithCache(String businessKey) {
		return workStationDao.queryByRealBusinessKey(businessKey);
	}	

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.isExist", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public boolean isExist(WorkStation data) {
		return workStationDao.queryByBusinessKey(data) != null;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.queryPageCount", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<WorkStationCountVo> queryPageCount(WorkStationQuery query) {
		Result<WorkStationCountVo> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		result.setData(workStationDao.queryPageCount(query));
		return result;
	}
	/**
	 * 生成并设置businessKey
	 * @param data
	 */
	private void generateAndSetBusinessKey(WorkStation data) {
		if(data != null) {

			data.setBusinessKey(DmsConstants.CODE_PREFIX_WORK_STATION.concat(StringHelper.padZero(this.genObjectId.getObjectId(WorkStation.class.getName()),11)));
		}
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.deleteByIds", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Boolean> deleteByIds(DeleteRequest<WorkStation> deleteRequest) {
		Result<Boolean> result = Result.success();
		if(deleteRequest == null
				|| CollectionUtils.isEmpty(deleteRequest.getDataList())) {
			return result.toFail("参数错误，删除列表不能为空！");
		}
		List<WorkStation> oldDataList = workStationDao.queryByIds(deleteRequest);
		if(CollectionUtils.isEmpty(oldDataList)
				|| oldDataList.size() < deleteRequest.getDataList().size()) {
			return result.toFail("参数错误，数据已变更请刷新列表后重新选择！");
		}
		for(WorkStation oldData : oldDataList) {
			if(workStationGridService.hasGridData(oldData.getBusinessKey())) {
				return result.toFail("工序【"+oldData.getWorkName()+ "】存在场地网格记录，无法删除！");
			}
		}
		result.setData(workStationDao.deleteByIds(deleteRequest) > 0);
		return result;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.queryCount", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<Long> queryCount(WorkStationQuery query) {
		Result<Long> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		result.setData(workStationDao.queryCount(query));
		return result;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.queryListForExport", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<List<WorkStation>> queryListForExport(WorkStationQuery query) {
		Result<List<WorkStation>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkStation> list = workStationDao.queryListForExport(query);
		if(CollectionUtils.isNotEmpty(list)){
			List<List<WorkStation>> partition = Lists.partition(list, 50);
			for (int i = 0; i < partition.size(); i++) {
				List<String> stationBusinessKeys = partition.get(i).stream().map(WorkStation::getBusinessKey).collect(Collectors.toList());
				List<WorkStationJobTypePO> workStationJobTypes = workStationJobTypeDao.selectByBusinessKeys(stationBusinessKeys);
				for (WorkStation workStation : partition.get(i)) {
					List<Integer> joyTypes = new ArrayList<>();
					for (int j = 0; j < workStationJobTypes.size(); j++) {
						if(Objects.equals(workStation.getBusinessKey(),workStationJobTypes.get(j).getRefBusinessKey())){
							joyTypes.add(workStationJobTypes.get(j).getJobCode());
						}
					}
					workStation.setJobTypes(joyTypes);
				}
			}
		}
		result.setData(list);
		return result;
	}

	@Override
	@JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationServiceImpl.queryWorkStationBybusinessKeyWithCache", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
	public Result<WorkStation> queryWorkStationBybusinessKeyWithCache(String businessKey) {
		Result<WorkStation> result = Result.success();
		result.setData(workStationDao.queryWorkStationBybusinessKeyWithCache(businessKey));
		return result;
	}

	@Override
	public Result<List<WorkStationJobTypeDto>> queryWorkStationJobTypeBybusinessKey(String businessKey) {
		Result<List<WorkStationJobTypeDto> > result = Result.success();
		List<WorkStationJobTypeDto> list = new ArrayList<>();
		List<WorkStationJobTypePO> pos = workStationJobTypeDao.selectByBusinessKey(businessKey);
		if(CollectionUtils.isNotEmpty(pos)){
			pos.stream().forEach(item->{
				WorkStationJobTypeDto dto = new WorkStationJobTypeDto();
				BeanUtils.copyProperties(item,dto);
				list.add(dto);
			});
		}
		result.setData(list);
		return result;
	}


	
	public void initAllWorkArea() {
		int pageNum = 1;
		WorkStationQuery query = new WorkStationQuery();
		List<WorkStation> dataList = null;
		do {
			query.setPageNumber(pageNum);
			query.setPageSize(100);
			Result<PageDto<WorkStation>>  pageResult = this.queryPageList(query);
			if(pageResult != null 
					&& pageResult.getData() != null) {
				dataList = pageResult.getData().getResult();
				if((!CollectionUtils.isEmpty(dataList))) {
					for(WorkStation data : dataList) {
						this.saveWorkArea(data);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}						
					}
				}
			}
			pageNum++;
		}while(!CollectionUtils.isEmpty(dataList));
	}
	public void initWorkArea(Long id) {
		this.saveWorkArea(workStationDao.queryById(id));
	}
}
