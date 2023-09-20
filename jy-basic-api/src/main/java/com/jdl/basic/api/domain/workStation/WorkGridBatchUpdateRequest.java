package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class WorkGridBatchUpdateRequest implements Serializable {
    private static final long serialVersionUID = -4331234672104482287L;

    private List<Long> ids;

    /**
     * 省区编码
     */
    private String provinceAgencyCode;

    /**
     * 省区名称
     */
    private String provinceAgencyName;

    private Integer siteCode;

    private String siteName;

    private String updateUser;

    private String updateUserName;

    private Date updateTime;
}
