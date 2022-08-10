package com.jdl.basic.api.domain.workMapFunc;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 拣运APP功能和工序映射表
 * 
 * @author liuduo8
 * @email liuduo3@jd.com
 * @date 2022-04-02 17:50:10
 */
@Data
public class JyWorkMapFuncConfigEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * ref：work_station业务主键
	 */
	private String refWorkKey;
	/**
	 * 功能编码
	 */
	private String funcCode;
	/**
	 * 创建人ERP
	 */
	private String createUser;
	/**
	 * 更新人ERP
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
