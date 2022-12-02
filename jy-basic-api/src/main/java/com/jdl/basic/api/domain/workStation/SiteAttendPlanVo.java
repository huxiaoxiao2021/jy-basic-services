package com.jdl.basic.api.domain.workStation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class SiteAttendPlanVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 计划出勤时间
     */
    private String planAttendTime;

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
     * 计划出勤总人数
     */
    private Integer totalEmployeeNum;

    /**
     * 白班计划出勤总人数
     */
    private Integer dayShiftTotalEmployeeNum;

    /**
     * 中班计划出勤总人数
     */
    private Integer midShiftTotalEmployeeNum;

    /**
     * 夜班计划出勤总人数
     */
    private Integer nightShiftTotalEmployeeNum;
    /**
     * 白班计划出勤人数Map
     */
    private Map<String, Integer> dayShift;

    /**
     * 中班计划出勤人数Map
     */
    private Map<String, Integer> midShift;

    /**
     * 夜班计划出勤人数Map
     */
    private Map<String, Integer> nightShift;

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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
