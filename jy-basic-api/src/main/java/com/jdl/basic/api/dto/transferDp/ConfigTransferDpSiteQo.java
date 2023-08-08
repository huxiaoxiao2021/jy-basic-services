package com.jdl.basic.api.dto.transferDp;

import com.jd.dms.java.utils.sdk.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description: <br>
 * Copyright: Copyright (c) 2020<br>
 * Company: jd.com 京东物流JDL<br>
 * 
 * @author fanggang7
 * @time 2022-11-23 17:29:43 周三
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigTransferDpSiteQo extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    private Long id;

    private Integer handoverOrgId;

    private String handoverOrgName;

    private Integer handoverSiteCode;

    private String handoverSiteName;

    private Integer preSortSiteCode;

    private String preSortSiteName;

    private Integer configStatus;

    private String effectiveStartTimeStr;
    private Date effectiveStartTime;

    private String effectiveStopTimeStr;
    private Date effectiveStopTime;

    private Integer yn;

    List<Long> ids;

    /**
     * 交接省区编码
     */
    private String handoverProvinceAgencyCode;
    /**
     * 交接枢纽编码
     */
    private String handoverAreaHubCode;
}