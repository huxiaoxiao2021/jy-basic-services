package com.jdl.basic.api.domain.schedule;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WorkGridScheduleRequest implements Serializable {
    private static final long serialVersionUID = 6311945137681734758L;

    private Long id;

    private Integer siteCode;

    private String areaCode;

    private String workGridKey;

    private Integer scheduleNo;

    private String scheduleKey;

    private String scheduleName;

    private String startTime;

    private String endTime;

    private Integer scheduleType;

    private Date createTime;

    private Date updateTime;

    private String createUserErp;

    private String createUserName;

    private String updateUserErp;

    private String updateUserName;
}
