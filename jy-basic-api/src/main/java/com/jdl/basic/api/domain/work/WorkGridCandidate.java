package com.jdl.basic.api.domain.work;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class WorkGridCandidate implements Serializable {

    private static final long serialVersionUID = 12934201311L;

    private Integer id;
    private Integer siteCode;
    private String siteName;
    private Integer siteType;
    private String erp;
    private Date createTime;
    private Integer yn;
    private Date ts;

    private String siteTypeName;
}
