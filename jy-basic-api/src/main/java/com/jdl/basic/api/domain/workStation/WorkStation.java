package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: WorkStation
 * @Description: 网格工序信息表-实体类
 * @author wuyoude
 * @date 2022年02月23日 11:01:53
 *
 */
@Data
public class WorkStation implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 业务主键：area_code、work_code
	 */
	private String businessKey;

	/**
	 * 业务条线编码
	 */
	private String businessLineCode;

	/**
	 * 业务条线名称
	 */
	private String businessLineName;

	/**
	 * 作业区编码
	 */
	private String areaCode;

	/**
	 * 作业区名称
	 */
	private String areaName;

	/**
	 * 工序编码
	 */
	private String workCode;

	/**
	 * 工序名称
	 */
	private String workName;

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
	 * 作业区类型
	 */
	private Integer areaType;

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
	 * 是否维护工种 0:未维护  1:已维护
	 */
	private Integer haveJobType;

	/**
	 * 工种类型集合
	 */
	private List<Integer> jobTypes;
	/**
	 * 编制人数
	 */
	private Integer standardNum;
}
