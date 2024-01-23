package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class JyUserQueryDto {

    private Long id;

    private String userErp;

    private String sex;

    private String entryDate;

    private String organizationCode;

    private Integer userStatus;

    private Integer siteCode;

    private Integer siteType;

    private String quitActionDate;

    /**
     * @see com.jdl.basic.api.enums.JyJobTypeEnum
     */
    private Integer jobType;

    private String positionCode;

    private String positionName;
    
    private Boolean gridDistributeFlag;

    private Date createTime;

    private Date updateTime;
    
    private List<String> positionNames;

    private String userCode;
}
