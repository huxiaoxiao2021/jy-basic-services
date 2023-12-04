package com.jdl.basic.provider.core.service.user;

import com.jdl.basic.api.domain.user.JyThirdpartyUser;

import java.util.List;

public interface ThirdpartyUseService {
    int batchInsert(List<JyThirdpartyUser> jyThirdpartyUserList);

    List<JyThirdpartyUser> queryJyThirdpartyUserByDetailBizId(String taskDetailBizId);

    List<JyThirdpartyUser> queryJyThirdpartyUserByTaskBizId(String taskBizId);
}
