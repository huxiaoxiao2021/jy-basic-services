package com.jdl.basic.api.domain.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class JyThirdpartyUserSaveDto implements Serializable {
    private static final long serialVersionUID = 4564094680036664279L;

    List<JyThirdpartyUser> jyThirdpartyUserList;
}
