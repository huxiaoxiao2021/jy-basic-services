package com.jdl.basic.provider.core.manager;

import com.jd.dms.comp.api.log.dto.OperateLogData;
import com.jd.dms.comp.api.log.dto.OperateLogQueryData;
import com.jd.dms.comp.base.ApiRequest;
import com.jd.dms.comp.base.ApiResult;
import com.jd.dms.comp.base.Pager;

import java.util.List;

public interface OperateLogManager {
    ApiResult saveOperateLog(ApiRequest<OperateLogData> request);

    ApiResult batchSaveOperateLog(ApiRequest<List<OperateLogData>> request);

    ApiResult<Pager<OperateLogData>> listOperateLog(ApiRequest<OperateLogQueryData> request);
}
