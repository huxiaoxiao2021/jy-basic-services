package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: WorkGridFlowDetailOffline
 * @Description: 网格流向明细离线统计表-实体
 *
 */
@Data
public class WorkGridFlowDetailOffline implements Serializable {

	private static final long serialVersionUID = 1212129435304L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 业务key：ref_work_grid_key
	 */
	private String refWorkGridKey;

	/**
	 * 省区编码
	 */
	private String provinceAgencyCode;

	/**
	 * 省区名称
	 */
	private String provinceAgencyName;

	/**
	 * 机构编码
	 */
	private String orgCode;

	/**
	 * 机构编码
	 */
	private String orgName;

	/**
	 * 场地编码
	 */
	private String siteCode;

	/**
	 * 场地名称
	 */
	private String siteName;

	/**
	 * 省区编码
	 */
	private String flowProvinceAgencyCode;

	/**
	 * 省区名称
	 */
	private String flowProvinceAgencyName;

	/**
	 * 流向机构编码
	 */
	private String flowOrgCode;

	/**
	 * 流向机构名称
	 */
	private String flowOrgName;

	/**
	 * 流向站点编号
	 */
	private String flowSiteCode;

	/**
	 * 流向站点名称
	 */
	private String flowSiteName;

	/**
	 * 流向类型 1：进 2：出
	 */
	private String flowDirectionType;

	/**
	 * 线路类型
	 */
	private String lineType;

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
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 日期
	 */
	private String dt;

	/**
	 * 时间戳
	 */
	private Date ts;

}
