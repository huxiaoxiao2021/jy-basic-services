package com.jdl.basic.api.domain.workStation;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;

/**
 * 作业区信息表-实体类
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Setter
@Getter
public class WorkArea implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 作业区编码
	 */
	private String areaCode;

	/**
	 * 作业区名称
	 */
	private String areaName;

	/**
	 * 业务条线编码
	 */
	private String businessLineCode;

	/**
	 * 业务条线名称
	 */
	private String businessLineName;

	/**
	 * 作业区类型
	 */
	private Integer areaType;

	/**
	 * 流向类型 1-进 2-出 0-其他
	 */
	private Integer flowDirectionType;

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


}
