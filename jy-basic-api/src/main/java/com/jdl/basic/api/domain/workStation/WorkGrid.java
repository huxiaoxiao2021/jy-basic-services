package com.jdl.basic.api.domain.workStation;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;

/**
 * 场地网格表-实体类
 * 
 * @author wuyoude
 * @date 2023年04月27日 16:16:10
 *
 */
@Setter
@Getter
public class WorkGrid implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 业务主键：site_code、floor、grid_no、area_code
	 */
	private String businessKey;

	/**
	 * 机构编码
	 */
	private Integer orgCode;

	/**
	 * 机构名称
	 */
	private String orgName;

	/**
	 * 场地类型
	 */
	private Integer siteType;

	/**
	 * 场地类型名称
	 */
	private String siteTypeName;

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
	 * 编制人数
	 */
	private Integer standardNum;

	/**
	 * 负责人erp
	 */
	private String ownerUserErp;

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
	 * 流向维护人
	 */
	private String configFlowUser;

	/**
	 * 流向维护时间
	 */
	private Date configFlowTime;

	/**
	 * 流向维护状态,0-未配置,1-已配置
	 */
	private Integer configFlowStatus;

	/**
	 * 创建人ERP
	 */
	private String createUser;

	/**
	 * 创建人姓名
	 */
	private String createUserName;

	/**
	 * 修改人ERP
	 */
	private String updateUser;

	/**
	 * 更新人姓名
	 */
	private String updateUserName;

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
	 * 数据状态,1-生效中，2-删除审批中、3-审批通过，4-审批驳回  默认生效中1
	 */
	private Integer status;
	/**
	 * 删除失败原因
	 */
	private String deleteFailMessage;
}
