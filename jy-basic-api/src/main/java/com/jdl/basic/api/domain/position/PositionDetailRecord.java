package com.jdl.basic.api.domain.position;

import lombok.Data;

/**
 * 类的描述
 *
 * @author hujiping
 * @date 2022/2/26 8:28 PM
 */
@Data
public class PositionDetailRecord extends PositionRecord {

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
     * 作业区编码
     */
    private String areaCode;

    /**
     * 作业区名称
     */
    private String areaName;

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
     * 工序编码
     */
    private String workCode;

    /**
     * 工序名称
     */
    private String workName;

    /**
     * 工序主键
     */
    private String refStationKey;
	/**
	 * 业务条线编码
	 */
	private String businessLineCode;

	/**
	 * 业务条线名称
	 */
	private String businessLineName;    
	/**
	 * 月台编号
	 */
	private String dockCode;

	/**
	 * 供应商编码
	 */
	private String supplierCode;

	/**
	 * 供应商名称
	 */
	private String supplierName;
	
	/**
	 * 关联场地网格key:work_grid
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
     * 枢纽编码
     */
    private String areaHubCode;
    /**
     * 枢纽名称
     */
    private String areaHubName;
    /**
     * 编制人数
     */
    private Integer standardNum;
    /**
     * 负责人erp
     */
    private String ownerUserErp;
}
