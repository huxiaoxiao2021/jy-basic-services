package com.jdl.basic.api.domain.workStation;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;

/**
 * 场地网格-负责人表-实体类
 * 
 * @author wuyoude
 * @date 2023年09月18日 22:43:58
 *
 */
@Setter
@Getter
public class WorkGridOwnerUser implements Serializable {

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
	 * 场地编码
	 */
	private Integer siteCode;

	/**
	 * 班次:1-白班 2-中班 3-晚班
	 */
	private Integer waveCode;

	/**
	 * 负责人erp
	 */
	private String ownerUserErp;

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
