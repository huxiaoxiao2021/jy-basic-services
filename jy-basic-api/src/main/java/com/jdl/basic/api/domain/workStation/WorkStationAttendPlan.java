package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: WorkStationAttendPlan
 * @Description: 场地人员出勤计划表-实体类
 * @author wuyoude
 * @date 2022年02月23日 11:01:53
 *
 */
@Data
public class WorkStationAttendPlan implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 业务主键：ref_grid_key、wave_code
	 */
	private String businessKey;

	/**
	 * 机构编码
	 */
	private Integer orgCode;

	/**
	 * 场地编码
	 */
	private Integer siteCode;

	/**
	 * 班次:1-白班 2-中班 3-晚班
	 */
	private Integer waveCode;

	/**
	 * 方案名称
	 */
	private String planName;

	/**
	 * 计划出勤人数
	 */
	private Integer planAttendNum;

	/**
	 * ref：work_station_grid业务主键
	 */
	private String refGridKey;

	/**
	 * 关联：work_station业务主键
	 */
	private String refStationKey;

	/**
	 * 版本号
	 */
	private Integer versionNum;

	/**
	 * 生效时间
	 */
	private Date enableTime;

	/**
	 * 失效时间
	 */
	private Date disableTime;

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
	 * 机构名称
	 */
	private String orgName;

	/**
	 * 场地名称
	 */
	private String siteName;

	/**
	 * 楼层
	 */
	private Integer floor;

	/**
	 * 网格号:01~99
	 */
	private String gridNo;

	/**
	 * 网格ID
	 */
	private String gridCode;

	/**
	 * 网格名称
	 */
	private String gridName;

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
	 * 班次名称
	 */
	private String waveName;

	/**
	 * 省区编码
	 */
	private String provinceAgencyCode;
	/**
	 * 省区名称
	 */
	private String provinceAgencyName;
	/**
	 * 枢纽编码
	 */
	private String areaHubCode;
	/**
	 * 枢纽名称
	 */
	private String areaHubName;

}
