package com.jdl.basic.api.domain.schedule;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ValidWorkGridScheduleRequest extends WorkGridSchedule implements Serializable {
    private static final long serialVersionUID = -4493394893933878796L;
    private String requestId = UUID.randomUUID().toString().replace("-","");
}
