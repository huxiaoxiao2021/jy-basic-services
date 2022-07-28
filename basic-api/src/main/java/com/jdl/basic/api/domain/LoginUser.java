package com.jdl.basic.api.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 当前登录人信息
 */
@Data
public class LoginUser implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 当前登录用户ID
	 */
	private Integer userId;
	/**
	 * 当前登录用户ERP账号
	 */
	private String userErp;
	/**
	 * 当前登录用户名称
	 */
	private String userName;

	/**
	 * 青龙用户编号
	 */
	private Integer staffNo;
	/**
	 * 当前登录用户机构编号
	 */
	private Integer orgId;
	/**
	 * 当前登录用户机构名称
	 */
	private String orgName;
	/**
	 * 当前登录用户站点类型
	 */
	private Integer siteType;
	/**
	 * 当前登录用户站点编号
	 */
	private Integer siteCode;
	/**
	 * 当前登录用户站点名称
	 */
	private String siteName;

    /**
     * 当前登录用户站点7位编码
     */
    private String dmsSiteCode;

}
