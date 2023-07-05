package com.jdl.basic.provider.core.service.boxFlow.impl;


import com.jd.fastjson.JSONObject;
import com.jd.jdl.aidata.isc.outer.api.common.model.JdMetaResult;
import com.jd.jdl.aidata.isc.outer.api.sort.interfaces.SortWorkbenchBusiness;
import com.jd.jdl.aidata.isc.outer.api.sort.model.BoxFlowParam;
import com.jd.jdl.aidata.isc.outer.api.sort.model.BoxFlowResult;
import com.jd.jim.cli.Cluster;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowInfo;
import com.jdl.basic.api.domain.boxFlow.dto.*;
import com.jdl.basic.common.enums.CollectBoxFlowInfoStatusEnum;
import com.jdl.basic.common.enums.CollectClaimEnum;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.core.dao.boxFlow.CollectBoxFlowInfoDao;
import com.jdl.basic.provider.core.dao.boxFlow.query.CollectBoxFlowDirectionConfQuery;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.boxFlow.CollectBoxFlowDirectionConfDao;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionConfService;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionVerifyService;
import com.jdl.basic.provider.mq.producer.DefaultJMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.jdl.basic.common.enums.CollectBoxFlowNoticTypeEnum.ROUTER_WARNING;


@Service("collectBoxFlowDirectionVerifyService")
@Slf4j
public class CollectBoxFlowDirectionVerifyServiceImpl implements ICollectBoxFlowDirectionVerifyService {
    private static String  CHECK_ROUTE_NOTIC_ERP_CACHE_PREFIX = "route-check-";
    
    private static String BDP_PUSH_ERP = "大数据推送规则";
    private static String BDP_PUSH_ERP2 = "大数据更新规则";
    @Resource
    private CollectBoxFlowDirectionConfDao collectBoxFlowDirectionConfMapper;
    @Autowired
    private ICollectBoxFlowDirectionConfService collectBoxFlowDirectionConfService;
    @Resource
    private CollectBoxFlowInfoDao collectBoxFlowInfoDao;
    @Autowired
    private SortWorkbenchBusiness jsfSortWorkbenchBusiness;
    /**
     * 路由离线校验，每批休眠时间避免调用大数据接口qps超过15
     */
    @Value("${collect.box.check.route.sleep.millisecond:10}")
    private Integer checkRouteSleepMillis;
    @Autowired
    Cluster cluster;
    @Value("${collect.box.check.route.notice.erp.cache.day:2}")
    private Long noticeErpCacheDay;

    @Value("${collect.box.check.route.notice.msg:集包规则路由错误，可能会到导致错分，请到分拣工作台-[集包规则配置]修改：始发分拣:{0},目的地分拣:{1},建包流向:{2} 路由错误信息:[{3}]}")
    private String noticeMsg;

    @Autowired(required = false)
    @Qualifier("collectBoxFlowNoticeMQ")
    private DefaultJMQProducer collectBoxFlowNoticeMQ;
    
    @Value("${collect.box.check.route.sleep.millisecond:50}")
    private Integer sleepMillisecond;

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
        //分拣计划配置时的版本号
        if(StringUtils.isBlank(confToBeVerifyed.getVersion())){
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

    @Override
    public void checkAllMixableRoute() {
        CollectBoxFlowInfo collectBoxFlowInfo = collectBoxFlowInfoDao.selectByCreateTimeAndStatus(null, null,
                CollectBoxFlowInfoStatusEnum.CURRENT.getCode());
        if(collectBoxFlowInfo == null){
            log.error("离线校验路由，未查到已激活版本");
            return;
        }
        Long id = 1L;
        List<CollectBoxFlowDirectionConf> confs = null;
        do{
            try {
                Thread.sleep(sleepMillisecond);
            } catch (InterruptedException e) {
                log.error("调大数据路由校验休眠异常", e);
            }
            confs = collectBoxFlowDirectionConfMapper.selectPageById(id, CollectClaimEnum.MIXABLE.getCode(), 
                    collectBoxFlowInfo.getVersion());
            if(CollectionUtils.isNotEmpty(confs)){
                id = confs.get(confs.size() -1).getId();
                for(CollectBoxFlowDirectionConf conf : confs){
                    //设置 场地对应操作人员缓存，以便推送信息
                    setNoticeErpCache(conf.getCreateUserErp(), conf.getUpdateUserErp(), conf.getStartSiteId());
                    
                    BoxFlowParam boxFlowParam = new BoxFlowParam();
                    Integer boxReceiveId = conf.getBoxReceiveId();
                    String boxReceiveName = conf.getBoxReceiveName();
                    Integer startSiteId = conf.getStartSiteId();
                    String startSiteName = conf.getStartSiteName();
                    Integer endSiteId = conf.getEndSiteId();
                    String endSiteName = conf.getEndSiteName();
                    
                    boxFlowParam.setBoxReceiveId(boxReceiveId);
                    boxFlowParam.setBoxReceiveName(boxReceiveName);
                    boxFlowParam.setStartSiteId(startSiteId);
                    boxFlowParam.setStartSiteName(startSiteName);
                    boxFlowParam.setEndSiteId(endSiteId);
                    boxFlowParam.setEndSiteName(endSiteName);
                    JdMetaResult<BoxFlowResult> jdMetaResult = null;
                    CallerInfo callerInfo = Profiler.registerInfo(Constants.UMP_APP_NAME +".CollectBoxFlowDirectionVerifyServiceImpl.getBoxFlowDiagnosticResult",
                            Constants.UMP_APP_NAME,false,true);
                    try {
                        jdMetaResult = jsfSortWorkbenchBusiness.getBoxFlowDiagnosticResult(boxFlowParam);
                    }catch (Exception e){
                        log.error("离线校验路由规则失败，请求参数：conf.id:{}, boxReceiveId:{},boxReceiveName:{},startSiteId:{}," +
                                        "startSiteName:{},endSiteId:{},endSiteName:{}, result:{}", conf.getId(), boxReceiveId, boxReceiveName,
                                startSiteId, startSiteName, endSiteId, endSiteName, JsonHelper.toJSONString(jdMetaResult));
                        Profiler.functionError(callerInfo);
                    }finally {
                        Profiler.registerInfoEnd(callerInfo);
                    }
                    
                    if(jdMetaResult == null || !jdMetaResult.success()){
                        log.error("离线校验路由规则失败，请求参数：conf.id:{}, boxReceiveId:{},boxReceiveName:{},startSiteId:{}," +
                                        "startSiteName:{},endSiteId:{},endSiteName:{}, result:{}", conf.getId(), boxReceiveId, boxReceiveName,
                                startSiteId, startSiteName, endSiteId, endSiteName, JsonHelper.toJSONString(jdMetaResult));
                       continue;
                    }
                    BoxFlowResult boxFlowResult = jdMetaResult.getData();
                    if(boxFlowResult == null){
                        log.info("离线校验路由规则boxFlowResult为空，默认校验通过，请求参数：conf.id:{},boxReceiveId:{},boxReceiveName:{},startSiteId:{}," +
                                        "startSiteName:{},endSiteId:{},endSiteName:{}, result:{}", conf.getId(), boxReceiveId, boxReceiveName,
                                startSiteId, startSiteName, endSiteId, endSiteName, JsonHelper.toJSONString(jdMetaResult));
                        continue;
                    }
                    if(boxFlowResult.getProblemType() != null){
                        String errorMsg = MessageFormat.format("离线校验路由规则路由错误：始发分拣:{0},目的地分拣:{1},建包流向:{2} 路由错误:[{3}],conf.id:{4}",
                                startSiteName, endSiteName, boxReceiveName, boxFlowResult.getProblemTypeDesc(), conf.getId());
                        log.error(errorMsg);
                        conf.setRouteErrorType(boxFlowResult.getProblemType());
                        collectBoxFlowDirectionConfMapper.updateByPrimaryKey(conf);
                        routeErrorNotice(startSiteId, startSiteName, endSiteId, endSiteName, boxReceiveId, boxReceiveName, boxFlowResult.getProblemTypeDesc());
                    }
                }
            }
        }while (CollectionUtils.isNotEmpty(confs));
        
    }
    
    private void routeErrorNotice(Integer startSiteId, String startSiteName, Integer endSiteId, String endSiteName, 
                                  Integer boxReceiveId, String boxReceiveName, String problemTypeDesc){
        String erps = cluster.get(CHECK_ROUTE_NOTIC_ERP_CACHE_PREFIX + startSiteId);
        if(StringUtils.isBlank(erps)){
            log.error("离线校验路由错误erps为空 无法发送咚咚消息，默认校验通过，请求参数：startSiteId:{}," +
                            "startSiteName:{},endSiteId:{},endSiteName:{}，boxReceiveId:{},boxReceiveName:{},problemTypeDesc:{}", 
                    startSiteId, startSiteName, endSiteId, endSiteName, boxReceiveId, boxReceiveName, problemTypeDesc);
            return;
        }
        String message = MessageFormat.format(noticeMsg,
                startSiteName, endSiteName, boxReceiveName, problemTypeDesc);
        CollectBoxFlowNoticDto dto = new CollectBoxFlowNoticDto();
        dto.setReceiveErps(erps);
        dto.setOperateType(ROUTER_WARNING.getCode());
        dto.setMessage(message);

        try {
            collectBoxFlowNoticeMQ.send(startSiteId + "|" + endSiteId + "|" + boxReceiveId, JsonHelper.toJSONString(dto));
        } catch (Exception ex) {
            log.error("箱号规则路由校验，MQ发送通知失败:{}",startSiteId + "|" + endSiteId + "|" + boxReceiveId, ex);
        }
    }
    
    private void setNoticeErpCache(String createErp, String updateErp, Integer createSiteCode){
        if((StringUtils.isBlank(createErp) || BDP_PUSH_ERP.equals(createErp) || BDP_PUSH_ERP2.equals(createErp)) 
                && StringUtils.isBlank(updateErp) || BDP_PUSH_ERP.equals(updateErp) || BDP_PUSH_ERP2.equals(updateErp)){
            return;
        }
        
        String key = CHECK_ROUTE_NOTIC_ERP_CACHE_PREFIX + createSiteCode;
        String erps = cluster.get(key);
        List<String> erpList = StringUtils.isBlank(erps) ? new ArrayList<>() : new ArrayList(Arrays.asList(erps.split(",")));
        // createErp是有效的 而且 缓存中不存在
        if(StringUtils.isNotBlank(createErp) && !BDP_PUSH_ERP.equals(createErp) 
                && !erpList.contains(createErp) && !BDP_PUSH_ERP2.equals(createErp)){
            erpList.add(createErp);
        }
        // updateErp是有效的 而且 缓存中不存在
        if(StringUtils.isNotBlank(updateErp) && !BDP_PUSH_ERP.equals(updateErp)
                && !erpList.contains(createErp) && !BDP_PUSH_ERP2.equals(updateErp)){
            erpList.add(updateErp);
        }
        
        if(CollectionUtils.isEmpty(erpList)){
            return;
        }
        cluster.pSetEx(key, String.join(",", erpList), noticeErpCacheDay, TimeUnit.DAYS);
    }
    
}
