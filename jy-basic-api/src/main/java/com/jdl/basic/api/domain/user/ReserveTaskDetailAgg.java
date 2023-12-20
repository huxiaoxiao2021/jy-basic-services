package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.io.Serializable;
@Data
public class ReserveTaskDetailAgg implements Serializable {
    private static final long serialVersionUID = 8749686933056686696L;
    private String taskDetailBizId;
    private String nature;
    private Integer count;
}
