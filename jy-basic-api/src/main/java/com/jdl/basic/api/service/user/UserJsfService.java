package com.jdl.basic.api.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUser;

import java.util.List;

public interface UserJsfService {
    Result<List<JyUser>> searchUserBySiteCode(Integer siteCode);

    Result<List<JyUser>> queryByUserId(List<Long> userIds);

    Result<List<JyUser>> getUnDistributedStaff(Integer siteCode, Integer jobType);

}
