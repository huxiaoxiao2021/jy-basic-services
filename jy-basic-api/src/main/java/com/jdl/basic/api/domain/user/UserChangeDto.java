package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserChangeDto implements Serializable {
    private static final long serialVersionUID = -3004075258777641208L;

    int totalNew = 0;
    int totalResign = 0;
}
