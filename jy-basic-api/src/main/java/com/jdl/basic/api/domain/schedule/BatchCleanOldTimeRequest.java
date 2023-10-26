package com.jdl.basic.api.domain.schedule;

import lombok.Data;

import java.util.List;

@Data
public class BatchCleanOldTimeRequest extends WorkGridScheduleRequest{
    private static final long serialVersionUID = -2225429161713091684L;

    private List<String> workGridKeys;
}
