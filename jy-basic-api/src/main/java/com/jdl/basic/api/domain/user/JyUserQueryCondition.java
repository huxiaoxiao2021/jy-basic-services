package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class JyUserQueryCondition implements Serializable {
    private static final long serialVersionUID = 8166694994693359482L;

    private Long id;

    private String userErp;

    private String userName;

    private String sex;

    private String entryDate;

    private String organizationCode;

    private String organizationName;

    private String organizationFullName;

    private Integer userStatus;

    private Integer siteCode;

    private String siteName;

    private Integer siteType;

    private String quitActionDate;

    private String nature;

    private String natureDesc;

    private String positionCode;

    private String positionName;

    private Boolean gridDistributeFlag;

    private Date createTime;

    private Date updateTime;
}
