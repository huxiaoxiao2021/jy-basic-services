package com.jdl.basic.api.domain.workStation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class SiteWaveScheduleVo {
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
     * 白班时间表
     */
    private Map<String, String> dayShift;

    /**
     * 中班时间表
     */
    private Map<String, String> midShift;

    /**
     * 夜班时间表
     */
    private Map<String, String> nightShift;

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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
