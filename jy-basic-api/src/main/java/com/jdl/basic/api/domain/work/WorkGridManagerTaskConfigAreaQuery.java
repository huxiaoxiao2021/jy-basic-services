package com.jdl.basic.api.domain.work;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;
import com.jdl.basic.api.domain.BasePagerCondition;

/**
 * 作业区巡检任务配置-作业区表-查询条件实体类
 * 
 * @author wuyoude
 * @date 2023年06月13日 15:17:42
 *
 */
@Setter
@Getter
public class WorkGridManagerTaskConfigAreaQuery extends BasePagerCondition implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 任务配置编码
	 */
	private String taskConfigCode;

	/**
	 * 作业区编码
	 */
	private String areaCode;

   /**
	* 分页-pageSize
	*/
	private Integer pageSize;

}
