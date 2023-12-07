package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class JyJobType implements Serializable {
    private static final long serialVersionUID = 3826628843916684977L;

    private Integer code;

    private String name;
}
