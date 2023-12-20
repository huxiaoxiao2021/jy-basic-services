package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ReserveTaskDetailAggQuery implements Serializable {
    private static final long serialVersionUID = -8987904418117484261L;

    private List<String> taskDetailBizIdList;
}
