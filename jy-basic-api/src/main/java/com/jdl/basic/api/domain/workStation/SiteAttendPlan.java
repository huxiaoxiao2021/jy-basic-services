package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SiteAttendPlan
 * @Description: 场地纬度人员出勤计划表-实体类
 * @author laoqingchang1
 * @date 2022年11月26日 17:26:53
 *
 */
@Data
public class SiteAttendPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 计划出勤时间
     */
    private Date planAttendTime;

    /**
     * 区域编码
     */
    private Integer orgCode;

    /**
     * 区域名称
     */
    private String orgName;

    /**
     * 场地编码
     */
    private Integer siteCode;

    /**
     * 场地名称
     */
    private String siteName;

    /**
     * 业务条线编码
     */
    private String businessLineCode;

    /**
     * 业务条线名称
     */
    private String businessLineName;

    /**
     * 班次:1-白班 2-中班 3-晚班
     */
    private Integer waveCode;

    /**
     * 工种:1-正式工 2-派遣工 3-外包工 4-临时工5-小时工
     */
    private Integer jobType;

    /**
     * 计划出勤人数
     */
    private Integer planAttendNum;

    /**
     * 状态:0-未确认 1-已确认
     */
    private Integer status;

    /**
     * 创建人erp
     */
    private String createUser;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 确认人erp
     */
    private String confirmUser;

    /**
     * 确认人姓名
     */
    private String confirmUserName;

    /**
     * 确认时间
     */
    private Date confirmTime;

    /**
     * 修改人erp
     */
    private String updateUser;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 版本号
     */
    private Integer versionNum;

    /**
     * 逻辑删除标志
     */
    private Integer yn;

    /**
     * 数据库时间
     */
    private Date ts;
}
