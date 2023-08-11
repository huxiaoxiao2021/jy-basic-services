package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserWorkGridDto extends UserWorkGrid implements Serializable {
    private static final long serialVersionUID = 2421326503264362550L;
    private String userErp;
    private String userName;
}
