package com.jdl.basic.api.domain.schedule;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WorkGridScheduleBatchUpdateRequest extends WorkGridScheduleRequest implements Serializable {
    private static final long serialVersionUID = -2676389489666885127L;

    /**
     * 需要添加的班次
     */
    private List<WorkGridSchedule> addWorkGridSchedule;
    /**
     * 需要删除的班次
     */
    private List<WorkGridSchedule> deleteWorkGridSchedule;
    /**
     * 当前班次
     */
    private List<WorkGridSchedule> currentWorkGridSchedule;
}
