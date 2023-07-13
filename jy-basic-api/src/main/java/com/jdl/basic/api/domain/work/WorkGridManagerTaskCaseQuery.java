package com.jdl.basic.api.domain.work;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;
import com.jdl.basic.api.domain.BasePagerCondition;

/**
 * 作业区巡检场景表-查询条件实体类
 * 
 * @author wuyoude
 * @date 2023年06月08日 10:54:02
 *
 */
@Setter
@Getter
public class WorkGridManagerTaskCaseQuery extends BasePagerCondition implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 任务编码
	 */
	private String taskCode;

	/**
	 * 场景编码
	 */
	private String caseCode;

	/**
	 * 排序值，1-100
	 */
	private Integer orderNum;

   /**
	* 分页-pageSize
	*/
	private Integer pageSize;

}
