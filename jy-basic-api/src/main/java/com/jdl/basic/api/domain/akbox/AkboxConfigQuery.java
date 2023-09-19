package com.jdl.basic.api.domain.akbox;

import lombok.Data;

import java.util.Date;

@Data
public class AkboxConfigQuery {

    private String provinceAgencyCode;

    private String areaHubCode;

    private Integer siteCode;

    private Integer pageSize;

    private Integer pageNo;


}
