package com.jdl.basic.api.domain.workStation;

import java.io.Serializable;
import java.util.List;

import com.jdl.basic.api.domain.BasePagerCondition;

import lombok.Getter;
import lombok.Setter;

/**
 * 场地网格流向表-查询条件实体类
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Setter
@Getter
public class WorkGridFlowDirectionQuery extends BasePagerCondition implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 关联场地网格key:work_grid
	 */
	private String refWorkGridKey;

	/**
	 * 线路类型 1-干线 2-支线 3-传 4-摆 0-其他
	 */
	private Integer lineType;

	/**
	 * 流向类型 1-进 2-出 0-其他
	 */
	private Integer flowDirectionType;

	/**
	 * 场地编码
	 */
	private Integer siteCode;

	/**
	 * 场地名称
	 */
	private String siteName;

	/**
	 * 机构编码
	 */
	private Integer orgCode;

	/**
	 * 机构名称
	 */
	private String orgName;

	/**
	 * 流入或流出-场地编码
	 */
	private Integer flowSiteCode;
	/**
	 * 流入或流出-场地编码
	 */
	private List<Integer> flowSiteCodeList;	

	/**
	 * 流入或流出-场地名称
	 */
	private String flowSiteName;

	/**
	 * 流入或流出-机构编码
	 */
	private Integer flowOrgCode;

	/**
	 * 流入或流出-机构名称
	 */
	private String flowOrgName;
	
	/**
	 * 日期
	 */
	private String dt;

   /**
	* 分页-pageSize
	*/
	private Integer pageSize;

	/**
	 * 关联网格工序信息表key:ref_work_key
	 */
	private List<String> refWorkKeyList;

	/**
	 * 关联场地网格key:work_grid
	 */
	private List<String> refWorkGridKeyList;

	/**
	 * 月台号
	 */
	private Integer dockCode;

}
