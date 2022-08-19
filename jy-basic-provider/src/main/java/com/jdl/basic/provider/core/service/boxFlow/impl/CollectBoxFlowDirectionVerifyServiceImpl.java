package com.jdl.basic.provider.core.service.boxFlow.impl;


import com.jd.fastjson.JSONObject;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.boxFlow.CollectBoxFlowDirectionConfDao;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service("collectBoxFlowDirectionVerifyService")
@Slf4j
public class CollectBoxFlowDirectionVerifyServiceImpl implements ICollectBoxFlowDirectionVerifyService {

    @Resource
    private CollectBoxFlowDirectionConfDao collectBoxFlowDirectionConfMapper;

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionVerifyServiceImpl.verifyBoxFlowDirectionConf", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<CollectBoxFlowDirectionConf> verifyBoxFlowDirectionConf(CollectBoxFlowDirectionConf confToBeVerifyed) {
        Integer startSiteId = confToBeVerifyed.getStartSiteId();
        Integer endSiteId = confToBeVerifyed.getEndSiteId();
        Integer boxReceiveId = confToBeVerifyed.getBoxReceiveId();
        Integer transportType = confToBeVerifyed.getTransportType();
        Integer flowType = confToBeVerifyed.getFlowType();

        Result<CollectBoxFlowDirectionConf> checkPageResult = new Result();

        if (startSiteId == null || endSiteId == null || boxReceiveId == null || transportType == null || flowType == null) {

            checkPageResult.setCode(CollectBoxFlowDirectionConf.PARAM_ERR);
            checkPageResult.setMessage("参数不能为空");
            return checkPageResult;
        }

        CollectBoxFlowDirectionConf collectBoxFlowDirectionConf = new CollectBoxFlowDirectionConf();
        collectBoxFlowDirectionConf.setStartSiteId(startSiteId);
        collectBoxFlowDirectionConf.setEndSiteId(endSiteId);
        collectBoxFlowDirectionConf.setTransportType(transportType);
        collectBoxFlowDirectionConf.setFlowType(flowType);
        List<CollectBoxFlowDirectionConf> lists = collectBoxFlowDirectionConfMapper.selectConfiged(collectBoxFlowDirectionConf);
        //没配置
        if (CollectionUtils.isEmpty(lists)) {
            checkPageResult.setCode(CollectBoxFlowDirectionConf.UN_CONFIG);
            checkPageResult.setMessage("未配置集包流向，请在工作台中配置");
            return checkPageResult;
        }
        //配置记录中由多个
        if (lists.size() > 1) {
            log.info("集包流向配置出现多条记录，请检查,参数:{}", JSONObject.toJSONString(collectBoxFlowDirectionConf));
        }
        lists.sort((o1, o2) -> o1.getTs().getTime() - o2.getTs().getTime() > 0 ? 1 : -1);
        CollectBoxFlowDirectionConf conf = lists.get(0);
        Integer rightBoxReceiveId = conf.getBoxReceiveId();
        String boxPkgName = conf.getBoxPkgName();
        //没配置
        if (rightBoxReceiveId == null) {
            checkPageResult.setCode(CollectBoxFlowDirectionConf.UN_CONFIG);
            checkPageResult.setMessage("未配置集包流向，请在工作台中配置");
            return checkPageResult;

        }
        //配置错误
        if (!Objects.equals(boxReceiveId, rightBoxReceiveId)) {
            checkPageResult.setCode(CollectBoxFlowDirectionConf.WRONG_CONF);
            checkPageResult.setMessage("集包流向配置错误，正确的箱号目的地:" + conf.getBoxReceiveName() + "(" + conf.getBoxReceiveId() + ")");
            checkPageResult.setData(conf);
            return checkPageResult;
        }
        checkPageResult.toSuccess();
        checkPageResult.setData(conf);

        return checkPageResult;
    }

}
