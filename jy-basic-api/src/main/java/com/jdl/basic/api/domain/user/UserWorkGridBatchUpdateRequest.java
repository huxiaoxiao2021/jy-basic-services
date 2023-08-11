package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserWorkGridBatchUpdateRequest extends UserWorkGridRequest implements Serializable {
    private static final long serialVersionUID = -4063402044031117706L;

    private Integer siteCode;

    private List<UserWorkGridDto> addUserWorkGrids;

    private List<UserWorkGridDto> deleteUserWorkGrids;
}
