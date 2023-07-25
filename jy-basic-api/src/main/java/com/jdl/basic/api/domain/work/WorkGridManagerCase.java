package com.jdl.basic.api.domain.work;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;

/**
 * 作业区巡检场景表-实体类
 * 
 * @author wuyoude
 * @date 2023年06月08日 10:54:02
 *
 */
@Setter
@Getter
public class WorkGridManagerCase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 场景编码
	 */
	private String caseCode;

	/**
	 * 场景名称
	 */
	private String caseName;

	/**
	 * 场景标题
	 */
	private String caseTitle;

	/**
	 * 场景内容
	 */
	private String caseContent;

	/**
	 * 检查结果,0-未选择,1-符合 2-不符合
	 */
	private Integer checkResult;

	/**
	 * 需要拍照,0-否,1-是
	 */
	private Integer needUploadPhoto;

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
