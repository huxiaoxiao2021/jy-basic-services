package com.jdl.basic.api.domain.schedule;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WorkGridScheduleBatchUpdateRequest extends WorkGridScheduleRequest implements Serializable {
    private static final long serialVersionUID = -2676389489666885127L;

    private List<WorkGridSchedule> addWorkGridSchedule;

    private List<WorkGridSchedule> deleteWorkGridSchedule;
}
