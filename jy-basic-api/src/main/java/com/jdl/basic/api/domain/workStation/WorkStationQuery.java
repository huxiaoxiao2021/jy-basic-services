package com.jdl.basic.api.domain.workStation;


import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

/**
 * 工序岗位信息表-查询条件实体类
 * 
 * @author wuyoude
 * @date 2021年12月30日 14:30:43
 *
 */
@Data
public class WorkStationQuery extends BasePagerCondition {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 作业区编码
	 */
	private String areaCode;

	/**
	 * 作业区名称
	 */
	private String areaName;

	/**
	 * 工序编码
	 */
	private String workCode;

	/**
	 * 工序名称
	 */
	private String workName;
	/**
	 * 分页-pageSize
	 */
	private Integer pageSize;

	/**
	 * 是否维护工种 0:未维护  1:已维护
	 */
	private Integer haveJobType;

}
