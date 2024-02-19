package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class JyUserQueryDto {

    private Long id;

    private String userErp;

    private String sex;
    /**
     * 入职时间
     */
    private String entryDate;
    /**
     * 集团部门编码
     */
    private String organizationCode;
    /**
     * 员工状态 1-在职 2-离职
     */
    private Integer userStatus;
    /**
     * 场地编码
     */
    private Integer siteCode;
    /**
     * 场地类型
     */
    private Integer siteType;
    /**
     * 离职日志
     */
    private String quitActionDate;

    /**
     * 工种
     * @see com.jdl.basic.api.enums.JyJobTypeEnum
     */
    private Integer jobType;
    /**
     * 岗位编码
     */
    private String positionCode;
    /**
     * 岗位名称
     */
    private String positionName;
    /**
     * 是否已分配 0-未分配 1-已分配
     */
    private Boolean gridDistributeFlag;

    private Date createTime;

    private Date updateTime;
    
    private List<String> positionNames;

    /**
     * 用户唯一编码（身份证、erp之类的）
     */
    private String userUniqueCode;
    /**
     * 三方人员有效日期
     */
    private Date scheduleDate;
}
