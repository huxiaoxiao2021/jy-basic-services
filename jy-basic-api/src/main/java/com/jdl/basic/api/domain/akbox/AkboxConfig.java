package com.jdl.basic.api.domain.akbox;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AkboxConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private String provinceAgencyCode;
    private String provinceAgencyName;

    private String areaHubCode;
    private String areaHubName;

    private Long siteCode;
    private String siteName;

    private Integer smallStock;
    private Integer largeStock;

    private Date createTime;
    private String createUserErp;
    private String createUser;

    private Date updateTime;
    private String updateUserErp;
    private String updateUser;

    private Boolean yn;

    private Date ts;


}
