package com.jdl.basic.api.domain.workStation;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;

/**
 * 场地网格流向表-实体类
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Setter
@Getter
public class WorkGridFlowDirection implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Long id;

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
	 * 创建人ERP
	 */
	private String createUser;

	/**
	 * 修改人ERP
	 */
	private String updateUser;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 逻辑删除标志,0-删除,1-正常
	 */
	private Integer yn;

	/**
	 * 数据库时间
	 */
	private Date ts;


}
