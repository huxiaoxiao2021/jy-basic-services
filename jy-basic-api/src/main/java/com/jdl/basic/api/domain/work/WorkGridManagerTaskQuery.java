package com.jdl.basic.api.domain.work;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;
import com.jdl.basic.api.domain.BasePagerCondition;

/**
 * 作业区巡检任务表-查询条件实体类
 * 
 * @author wuyoude
 * @date 2023年06月08日 10:54:02
 *
 */
@Setter
@Getter
public class WorkGridManagerTaskQuery extends BasePagerCondition implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
	* 分页-pageSize
	*/
	private Integer pageSize;

}
