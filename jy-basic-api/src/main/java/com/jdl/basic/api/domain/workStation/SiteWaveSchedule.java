package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: SiteWaveSchedule
 * @Description: 场地班次时间表-实体类
 * @author laoqingchang1
 * @date 2022年11月26日 17:26:53
 *
 */
@Data
public class SiteWaveSchedule {
    /**
     * 主键ID
     */
    private Long id;

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
     * 班次时间段
     */
    private Integer wavePeriod;

    /**
     * 班次开始时间--只取时分秒
     */
    private Date startTime;

    /**
     * 班次结束时间--只取时分秒
     */
    private Date endTime;

    /**
     * 创建人erp
     */
    private String createUser;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人erp
     */
    private String updateUser;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 版本号
     */
    private Integer versionNum;

    /**
     * 逻辑删除标志,0-删除,1-正常
     */
    private Integer yn;

    /**
     * 数据库时间
     */
    private Date ts;

}