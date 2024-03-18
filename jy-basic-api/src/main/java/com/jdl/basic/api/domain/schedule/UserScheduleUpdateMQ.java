package com.jdl.basic.api.domain.schedule;

import lombok.Data;

@Data
public class UserScheduleUpdateMQ {

    /**
     * 出勤日
     */
    private String scheduleDate;

    /**
     * 网格班次唯一标识
     */
    private String scheduleKey;

    /**
     * 班次开始时间
     */
    private String startTime;

    /**
     * 班次结束时间
     */
    private String endTime;

    private String updateUserErp;

    private String updateUserName;

    /**
     * 是否删除排班计划标识
     */
    private Boolean deleteFlag;
}
