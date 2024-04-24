package com.jdl.basic.api.domain.schedule;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScheduleValidTimeDto extends WorkGridSchedule implements Serializable {
    private static final long serialVersionUID = 8350456659412574251L;

    /**
     * 查询时间内的有效开始时间
     */
    private String validStartTime;
    /**
     * 查询时间内的有效结束时间
     */
    private String validEndTime;
}
