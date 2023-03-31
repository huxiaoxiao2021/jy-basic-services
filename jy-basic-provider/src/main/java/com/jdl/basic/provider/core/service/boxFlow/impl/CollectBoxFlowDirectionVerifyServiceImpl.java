package com.jdl.basic.provider.core.service.boxFlow.impl;


import com.jd.fastjson.JSONObject;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowDirectionConfReq;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowDirectionConfResp;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowFinishBoxReq;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowFinishBoxResp;
import com.jdl.basic.provider.core.dao.boxFlow.query.CollectBoxFlowDirectionConfQuery;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.boxFlow.CollectBoxFlowDirectionConfDao;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionConfService;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service("collectBoxFlowDirectionVerifyService")
@Slf4j
public class CollectBoxFlowDirectionVerifyServiceImpl implements ICollectBoxFlowDirectionVerifyService {

    @Resource
    private CollectBoxFlowDirectionConfDao collectBoxFlowDirectionConfMapper;
    @Autowired
    private ICollectBoxFlowDirectionConfService collectBoxFlowDirectionConfService;

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionVerifyServiceImpl.verifyBoxFlowDirectionConf", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
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
        //大数据推送时调用此方法 传的版本号
        String version = confToBeVerifyed.getVersion();
        if(StringUtils.isBlank(confToBeVerifyed.getVersion())){
            //分拣计划配置时的版本号
            version = collectBoxFlowDirectionConfService.getCurrentVersion();
        }
        if(StringUtils.isBlank(version)){
            checkPageResult.setCode(CollectBoxFlowDirectionConf.SUCCESS);
            checkPageResult.setMessage("未查到激活的集包规则版本");
            return checkPageResult;
        }
        CollectBoxFlowDirectionConf collectBoxFlowDirectionConf = new CollectBoxFlowDirectionConf();
        collectBoxFlowDirectionConf.setStartSiteId(startSiteId);
        collectBoxFlowDirectionConf.setEndSiteId(endSiteId);
        collectBoxFlowDirectionConf.setTransportType(transportType);
        collectBoxFlowDirectionConf.setFlowType(flowType);

        collectBoxFlowDirectionConf.setVersion(version);
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

    @Override
    public Result<CollectBoxFlowDirectionConfResp> verifyBoxFlowDirectionConfs(CollectBoxFlowDirectionConfReq req) {

        if (req.getBoxReceiveId() == null || req.getFlowType() == null || req.getTransportType() == null || req.getStartSiteId() == null) {
            log.warn("参数错误,{}", JSONObject.toJSONString(req));
            return Result.fail("参数不能为空");
        }

        String version = collectBoxFlowDirectionConfService.getCurrentVersion();
        if(StringUtils.isBlank(version)){
            return Result.success("无激活版本");
        }
        //防止全查出来
        if (CollectionUtils.isEmpty(req.getEndSiteId())) {
            req.setEndSiteId(Arrays.asList(-1));
        }

        Result<CollectBoxFlowDirectionConfResp> result = new Result<>();
        CollectBoxFlowDirectionConfResp resp = new CollectBoxFlowDirectionConfResp();

        CollectBoxFlowDirectionConfQuery query = new CollectBoxFlowDirectionConfQuery();
        query.setStartSiteId(req.getStartSiteId());
        query.setEndSiteIds(req.getEndSiteId());

        query.setTransportType(req.getTransportType());
        query.setFlowType(req.getFlowType());
        query.setVersion(version);
        try {

            List<CollectBoxFlowDirectionConf> select = collectBoxFlowDirectionConfMapper.selectByStartSiteIdAndEndSiteIds(query);
            //错误的配置list
            List<CollectBoxFlowDirectionConf> wrongList = select.stream().filter(s -> {
                Integer boxReceiveId = s.getBoxReceiveId();
                if (!Objects.equals(boxReceiveId, req.getBoxReceiveId())) {
                    return true;
                }
                return false;
            }).collect(Collectors.toList());

            //如果列表不是空，说明有错误的配置
            if (CollectionUtils.isNotEmpty(wrongList)) {
                resp.setResult(CollectBoxFlowDirectionConf.WRONG_CONF);
                resp.setWrongConfs(wrongList);
            } else {
                resp.setResult(CollectBoxFlowDirectionConf.SUCCESS);
            }

            result.setData(resp);
            result.toSuccess("ok");
        } catch (Exception e) {
            log.error("流向校验异常,入参：" + JSONObject.toJSONString(req), e);
            result.toFail("系统异常" + e.getMessage());
        }
        return result;

    }

    @Override
    public Result<CollectBoxFlowFinishBoxResp> ifBoxesWasFinishBox(CollectBoxFlowFinishBoxReq req) {
        if (req.getFlowType() == null || req.getTransportType() == null || req.getStartSiteId() == null) {
            log.warn("参数错误,{}", JSONObject.toJSONString(req));
            return Result.fail("参数不能为空");
        }
        String version = collectBoxFlowDirectionConfService.getCurrentVersion();
        if(StringUtils.isBlank(version)){
            return Result.success("无激活版本");
        }
        //防止全查出来
        if (CollectionUtils.isEmpty(req.getEndSiteId())) {
            req.setEndSiteId(new ArrayList<Integer>() {{
                add(-1);
            }});
        }

        Result<CollectBoxFlowFinishBoxResp> result = new Result<>();
        CollectBoxFlowDirectionConfQuery query = new CollectBoxFlowDirectionConfQuery();
        CollectBoxFlowFinishBoxResp resp = new CollectBoxFlowFinishBoxResp();

        query.setStartSiteId(req.getStartSiteId());
        query.setEndSiteIds(req.getEndSiteId());

        query.setTransportType(req.getTransportType());
        query.setFlowType(req.getFlowType());
        query.setVersion(version);
        try {

            List<CollectBoxFlowDirectionConf> select = collectBoxFlowDirectionConfMapper.selectByStartSiteIdAndEndSiteIds(query);

            //按照集包要求分组
            Map<Integer, List<CollectBoxFlowDirectionConf>> groupByCollectClaim = select.stream()
                    .collect(Collectors.groupingBy(CollectBoxFlowDirectionConf::getCollectClaim));

            ArrayList<CollectBoxFlowDirectionConf> mixs = (ArrayList<CollectBoxFlowDirectionConf>) groupByCollectClaim.get(CollectBoxFlowDirectionConf.COLLECT_CLAIM_MIX);
            ArrayList<CollectBoxFlowDirectionConf> finishs = (ArrayList<CollectBoxFlowDirectionConf>) groupByCollectClaim.get(CollectBoxFlowDirectionConf.COLLECT_CLAIM_FINISH);
            ArrayList<CollectBoxFlowDirectionConf> specifyMixs = (ArrayList<CollectBoxFlowDirectionConf>) groupByCollectClaim.get(CollectBoxFlowDirectionConf.COLLECT_CLAIM_SPECIFY_MIX);
            
            resp.setMixSites(mixs);
            resp.setFinishSites(finishs);
            resp.setSpecifyMixs(specifyMixs);
            if (CollectionUtils.isNotEmpty(mixs) || CollectionUtils.isNotEmpty(specifyMixs)) {
                resp.setResult(false);
            } else {
                resp.setResult(true);
            }
            result.setData(resp);
            result.toSuccess();
        } catch (Exception e) {

            log.error("校验成品包异常,入参：" + JSONObject.toJSONString(req), e);
            result.toFail("系统异常" + e.getMessage());
        }
        return result;
    }

}
