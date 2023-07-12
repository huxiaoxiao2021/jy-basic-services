package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserWorkGridBatchRequest extends UserWorkGridRequest implements Serializable {

    private static final long serialVersionUID = 8642408974869081632L;

    List<UserWorkGrid> userWorkGrids;
}
