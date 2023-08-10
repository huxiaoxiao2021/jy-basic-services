package com.jdl.basic.provider.core.manager;

import com.jd.dms.comp.api.log.OperateLogService;
import com.jd.dms.comp.api.log.dto.OperateLogData;
import com.jd.dms.comp.api.log.dto.OperateLogQueryData;
import com.jd.dms.comp.base.ApiRequest;
import com.jd.dms.comp.base.ApiResult;
import com.jd.dms.comp.base.Pager;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.common.contants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OperateLogManagerImpl implements OperateLogManager{

    @Autowired
    private OperateLogService operateLogService;

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".OperateLogManagerImpl.saveOperateLog", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public ApiResult saveOperateLog(ApiRequest<OperateLogData> request) {
        return operateLogService.saveOperateLog(request);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".OperateLogManagerImpl.listOperateLog", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public ApiResult<Pager<OperateLogData>> listOperateLog(ApiRequest<OperateLogQueryData> request) {
        return operateLogService.listOperateLog(request);
    }
}
