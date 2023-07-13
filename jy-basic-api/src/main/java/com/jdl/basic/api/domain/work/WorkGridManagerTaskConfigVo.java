package com.jdl.basic.api.domain.work;

import java.util.List;

import com.jdl.basic.api.domain.workStation.WorkArea;

import lombok.Getter;
import lombok.Setter;

/**
 * 作业区巡检任务配置表-实体类
 * 
 * @author wuyoude
 * @date 2023年06月08日 10:54:02
 *
 */
@Setter
@Getter
public class WorkGridManagerTaskConfigVo extends WorkGridManagerTaskConfig{

	private static final long serialVersionUID = 1L;

	/**
	 * 推送周期-名称
	 */
	private String frequencyTypeName;
	
	/**
	 * 执行天，多个值，用逗号隔开如：1,2,3代表每周1、2、3或者每月1、2、3
	 */
	private String frequencyDaysName;
	/**
	 * 执行天列表
	 */
	private List<Integer> frequencyDayList;
	
	/**
	 * 推送时间 10:00
	 */
	private String frequencyTimeStr;
	
	/**
	 * 推送时间 10:00
	 */
	private String finishTypeName;
	/**
	 * 网格轮训规则 1-随机推送 
	 */
	private String gridTurnTypeName;
	/**
	 * 作业区列表
	 */
	private List<WorkArea> workAreaList;
	/**
	 * 作业区编码列表
	 */
	private List<String> workAreaCodeList;
}
