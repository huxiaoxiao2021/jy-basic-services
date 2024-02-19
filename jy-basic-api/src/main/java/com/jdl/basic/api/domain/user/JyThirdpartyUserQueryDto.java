package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.io.Serializable;
@Data
public class JyThirdpartyUserQueryDto implements Serializable {
    private static final long serialVersionUID = -7610406923957301296L;

    private String taskBizId;
    private String taskDetailBizId;
    private Integer pageNo;
    private Integer pageSize;
    private String userCode;
}
