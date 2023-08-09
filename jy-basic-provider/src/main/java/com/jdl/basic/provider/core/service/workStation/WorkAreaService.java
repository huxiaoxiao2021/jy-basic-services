package com.jdl.basic.provider.core.service.workStation;

import java.util.List;
import java.util.Map;

import com.jdl.basic.api.domain.workStation.WorkArea;
import com.jdl.basic.api.domain.workStation.WorkAreaQuery;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;

/**
 * 作业区信息表--Service接口
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
public interface WorkAreaService {

	/**
	 * 插入一条数据
	 * @param insertData
	 * @return
	 */
	Result<Boolean> insert(WorkArea insertData);
	/**
	 * 根据id更新数据
	 * @param updateData
	 * @return
	 */
	Result<Boolean> updateById(WorkArea updateData);
	/**
	 * 根据id删除数据
	 * @param deleteData
	 * @return
	 */
	Result<Boolean> deleteById(WorkArea deleteData);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Result<WorkArea> queryById(Long id);
	/**
	 * 按条件分页查询
	 * @param query
	 * @return
	 */
	Result<PageDto<WorkArea>> queryPageList(WorkAreaQuery query);
	/**
	 * 
	 * @param workArea
	 */
	Result<Boolean> saveData(WorkArea workArea);
	/**
	 * 根据areaCode查询
	 * @param areaCode
	 * @return
	 */	
	WorkArea queryByAreaCode(String areaCode);
	/**
	 * 根据areaCode加载数据，传入cacheMap优先从map中加载，不存在返回null
	 * @param areaCode
	 * @param cacheMap
	 * @return
	 */
	WorkArea loadByAreaCodeInMap(String areaCode,Map<String,WorkArea> cacheMap);
	/**
	 * 根据areaCode列表查询，将返回结果以areaCode为key返回map
	 * @param areaCode
	 * @return
	 */	
	Map<String,WorkArea> queryByAreaCodeListToMap(List<String> areaCodeList);
	/**
	 * 根据areaCode列表查询
	 * @param areaCode
	 * @return
	 */	
	List<WorkArea> queryListByAreaCodeList(List<String> areaCodeList);
}
