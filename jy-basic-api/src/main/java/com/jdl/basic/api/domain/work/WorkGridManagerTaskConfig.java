package com.jdl.basic.api.domain.work;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;

/**
 * 作业区巡检任务配置表-实体类
 * 
 * @author wuyoude
 * @date 2023年06月08日 10:54:02
 *
 */
@Setter
@Getter
public class WorkGridManagerTaskConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 任务配置编码
	 */
	private String taskConfigCode;

	/**
	 * 任务配置名称
	 */
	private String taskConfigName;

	/**
	 * 任务编码
	 */
	private String taskCode;

	/**
	 * 负责任务处理的职位编码
	 */
	private String handlerUserPositionCode;

	/**
	 * 负责任务处理的职位名称
	 */
	private String handlerUserPositionName;

	/**
	 * 执行周期 D-每天 W-每周 M-每月 
	 */
	private String frequencyType;

	/**
	 * 执行天，多个值，用逗号隔开如：1,2,3代表每周1、2、3或者每月1、2、3
	 */
	private String frequencyDays;

	/**
	 * 执行时间-小时，24小时值0-23
	 */
	private Integer frequencyHour;

	/**
	 * 执行时间-分钟，0-59
	 */
	private Integer frequencyMinute;

	/**
	 * 执行周期 1-一天内完成 2-一周内完成
	 */
	private Integer finishType;

	/**
	 * 每次检查网格数量，1-100
	 */
	private Integer perGridNum;

	/**
	 * 网格轮训规则 1-随机推送 
	 */
	private Integer gridTurnType;

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
