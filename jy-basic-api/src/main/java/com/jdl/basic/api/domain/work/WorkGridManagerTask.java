package com.jdl.basic.api.domain.work;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;

/**
 * 作业区巡检任务表-实体类
 * 
 * @author wuyoude
 * @date 2023年06月08日 10:54:02
 *
 */
@Setter
@Getter
public class WorkGridManagerTask implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 任务编码
	 */
	private String taskCode;

	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 任务说明
	 */
	private String taskDescription;

	/**
	 * 任务类型：1-例会（标题+内容） 2-例会记录（仅拍照） 3-工作区任务（多页签） 
	 */
	private Integer taskType;

	/**
	 * 需要扫描岗位码标志,0-不扫描,1-扫描
	 */
	private Integer needScanGrid;

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
	 * 任务业务类型：1-日常巡查 2-管理巡视 3-异常及时检查 4-指标周期改善 5-事件治理整改任务
	 */
	private Integer taskBizType;

}
