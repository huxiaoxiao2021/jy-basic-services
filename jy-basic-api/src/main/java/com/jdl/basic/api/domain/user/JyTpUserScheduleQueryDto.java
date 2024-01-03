package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class JyTpUserScheduleQueryDto implements Serializable {
    private static final long serialVersionUID = 7595304427821389910L;

    /**
     * 排班日期
     */
    private Date scheduleDate;

    /**
     * 场地
     */
    private Integer siteCode;

    /**
     * 工种
     */
    private String nature;

    private Integer taskType;
}
