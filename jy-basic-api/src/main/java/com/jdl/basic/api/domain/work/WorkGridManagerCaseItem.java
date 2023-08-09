package com.jdl.basic.api.domain.work;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;

/**
 * 作业区巡检-项目明细表-实体类
 * 
 * @author wuyoude
 * @date 2023年06月08日 10:54:02
 *
 */
@Setter
@Getter
public class WorkGridManagerCaseItem implements Serializable {

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
	 * 检查项编码
	 */
	private String caseItemCode;

	/**
	 * 检查项描述内容
	 */
	private String caseItemText;

	/**
	 * 是否可选,0-否,1-是
	 */
	private Integer canSelect;

	/**
	 * 选择标志,0-未选中,1-选中
	 */
	private Integer selectFlag;

	/**
	 * 排序值，1-100
	 */
	private Integer orderNum;

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
