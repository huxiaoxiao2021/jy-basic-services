package com.jdl.basic.api.domain.schedule;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WorkGridScheduleBatchRequest extends WorkGridScheduleRequest implements Serializable {
    private static final long serialVersionUID = -1304125571917018871L;

    private List<WorkGridSchedule> workGridSchedules;
}
