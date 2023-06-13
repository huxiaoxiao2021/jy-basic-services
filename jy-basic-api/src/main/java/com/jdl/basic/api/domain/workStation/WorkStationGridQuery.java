package com.jdl.basic.api.domain.workStation;


import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

/**
 * 工序岗位网格表-查询条件实体类
 * 
 * @author wuyoude
 * @date 2021年12月30日 14:30:43
 *
 */
@Data
public class WorkStationGridQuery extends BasePagerCondition {

	private static final long serialVersionUID = 1L;
	/**
	 * 机构编码
	 */
	private Integer orgCode;

	/**
	 * 机构名称
	 */
	private String orgName;

	/**
	 * 场地编码
	 */
	private Integer siteCode;

	/**
	 * 场地名称
	 */
	private String siteName;

	/**
	 * 楼层
	 */
	private Integer floor;

	/**
	 * 网格号:01~99
	 */
	private String gridNo;

	/**
	 * 网格ID
	 */
	private String gridCode;

	/**
	 * 网格名称
	 */
	private String gridName;
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
	 * 唯一业务编码
	 */
	private String businessKey;

	/**
	 * 月台号
	 */
	private String dockCode;

	/**
	 * 供应商编码
	 */
	private String supplierCode;
	/**
	 * 关联场地网格key:work_grid
	 */
	private String refWorkGridKey;

	/**
	 * 省区编码
	 */
	private String provinceAgencyCode;
	/**
	 * 枢纽编码
	 */
	private String areaHubCode;
}
