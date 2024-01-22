package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.io.Serializable;
@Data
public class RoleQueryDto implements Serializable {
    private static final long serialVersionUID = 7950983508827456923L;

    private Integer siteCode;
    private String positionCode;
}
