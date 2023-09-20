package com.jdl.basic.provider.core.service.workStation.impl;

import java.util.List;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.contants.DmsConstants;
import com.jd.jsf.gd.util.StringUtils;
import com.jdl.basic.api.domain.workStation.WorkGridOwnerUser;
import com.jdl.basic.api.domain.workStation.WorkGridOwnerUserQuery;
import com.jdl.basic.provider.core.dao.workStation.WorkGridOwnerUserDao;
import com.jdl.basic.provider.core.service.workStation.WorkGridOwnerUserService;

/**
 * 场地网格-负责人表--Service接口实现
 * 
 * @author wuyoude
 * @date 2023年09月18日 22:43:58
 *
 */
@Slf4j
@Service("workGridOwnerUserService")
public class WorkGridOwnerUserServiceImpl implements WorkGridOwnerUserService {

	@Autowired
	@Qualifier("workGridOwnerUserDao")
	private WorkGridOwnerUserDao workGridOwnerUserDao;

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	public Result<Boolean> insert(WorkGridOwnerUser insertData){
		Result<Boolean> result = Result.success();
		if(insertData == null) {
			return result.toFail("传入数据为空！");
		}
		result.setData(workGridOwnerUserDao.insert(insertData) == 1);
		return result;
	 }
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	public Result<Boolean> updateById(WorkGridOwnerUser updateData){
		Result<Boolean> result = Result.success();
		result.setData(workGridOwnerUserDao.updateById(updateData) == 1);
		return result;
	 }
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	public int deleteByGridKey(WorkGridOwnerUser deleteData){
		return workGridOwnerUserDao.deleteByGridKey(deleteData);
	 }
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result<WorkGridOwnerUser> queryById(Long id){
		Result<WorkGridOwnerUser> result = Result.success();
		result.setData(fillWorkGridOwnerUser(workGridOwnerUserDao.queryById(id)));
		return result;
	 }
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	public Result<PageDto<WorkGridOwnerUser>> queryPageList(WorkGridOwnerUserQuery query){
		Result<PageDto<WorkGridOwnerUser>> result = Result.success();
		Result<Boolean> checkResult = this.checkParamForQueryPageList(query);
		if(!checkResult.isSuccess()){
		    return Result.fail(checkResult.getMessage());
		}
		List<WorkGridOwnerUser> voDataList = new ArrayList<WorkGridOwnerUser>();
		PageDto<WorkGridOwnerUser> pageDto = new PageDto<>(query.getPageNumber(), query.getPageSize());
		Long totalCount = workGridOwnerUserDao.queryCount(query);
		if(totalCount != null && totalCount > 0){
		    List<WorkGridOwnerUser> dataList = workGridOwnerUserDao.queryList(query);
		    for (WorkGridOwnerUser tmp : dataList) {
		    	voDataList.add(this.fillWorkGridOwnerUser(tmp));
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
	public Result<Boolean> checkParamForQueryPageList(WorkGridOwnerUserQuery query){
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
	public WorkGridOwnerUser fillWorkGridOwnerUser(WorkGridOwnerUser data){
		if(data == null) {
			return null;
		}
		//特殊字段设置
		return data;
	 }
	@Override
	public List<WorkGridOwnerUser> queryListByGridKey(String businessKey) {
		if(StringUtils.isBlank(businessKey)) {
			return new ArrayList<>();
		}
		return workGridOwnerUserDao.queryListByGridKey(businessKey);
	}

}
