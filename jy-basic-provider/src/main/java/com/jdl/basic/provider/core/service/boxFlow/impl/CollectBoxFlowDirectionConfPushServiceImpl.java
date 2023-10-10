package com.jdl.basic.provider.core.service.boxFlow.impl;

import com.alibaba.fastjson.JSONObject;

import com.jd.jim.cli.Cluster;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.util.DateUtil;
import com.jd.ql.basic.ws.BasicPrimaryWS;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionPushConfDto;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowInfo;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowNoticDto;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.enums.CollectBoxFlowInfoOperateTypeEnum;
import com.jdl.basic.common.enums.CollectBoxFlowInfoStatusEnum;
import com.jdl.basic.common.enums.CollectBoxFlowNoticTypeEnum;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.core.dao.boxFlow.CollectBoxFlowInfoDao;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionConfPushService;
import com.jdl.basic.common.enums.OrgEnum;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.boxFlow.CollectBoxFlowDirectionConfDao;
import com.jdl.basic.provider.mq.producer.DefaultJMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.*;

import static com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionPushConfDto.COLLECT_CLAIM_FINISHED_BOX;
import static com.jdl.basic.common.enums.CollectBoxFlowInfoOperateTypeEnum.ACTIVATE;
import static com.jdl.basic.common.enums.CollectBoxFlowInfoOperateTypeEnum.ADD;
import static com.jdl.basic.common.enums.CollectBoxFlowInfoStatusEnum.HISTORY;
import static com.jdl.basic.common.enums.CollectBoxFlowInfoStatusEnum.UNACTIVATED;

@Service("collectBoxFlowDirectionConfPushService")
@Slf4j
public class CollectBoxFlowDirectionConfPushServiceImpl  implements ICollectBoxFlowDirectionConfPushService {
    private ExecutorService deleteHistoryExecutorService= new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1));


    private static Integer DELETE_COUNT = 2000;
    
    @Resource
    private CollectBoxFlowDirectionConfDao collectBoxFlowDirectionConfMapper;

    @Autowired
    CollectBoxFlowDirectionConfServiceImpl confService;

    @Autowired
    private CollectBoxFlowDirectionVerifyServiceImpl verifyService;
    
    @Autowired
    private CollectBoxFlowInfoDao collectBoxFlowInfoDao;

    @Autowired
    Cluster cluster;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    BasicPrimaryWS basicPrimaryWS;
    @Autowired(required = false)
    @Qualifier("collectBoxFlowNoticeMQ")
    private DefaultJMQProducer collectBoxFlowNoticeMQ;


    public static String COLLECT_BOX_FLOW_DIRECTION_LASTEST_CONF_TIME = "COLLECT_BOX_FLOW_DIRECTION_LASTEST_CONF_TIME";
    
    public static String COLLECT_BOX_FLOW_DIRECTION_VERSION_UPDATE = "COLLECT_BOX_FLOW_DIRECTION_VERSION_UPDATE-";
    public static String COLLECT_BOX_FLOW_ADD_AND_DELETE_HISTORY = "COLLECT_BOX_FLOW_ADD_AND_DELETE_HISTORY-";
    


    @Override
    @Transactional
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionConfPushServiceImpl.updateOrNewConfig", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Void> updateOrNewConfig(CollectBoxFlowDirectionPushConfDto dto) {
        Result<Void> result = new Result<>();
        log.info("大数据更新集包规则:{}", JSONObject.toJSONString(dto));
        try {
            cluster.set(COLLECT_BOX_FLOW_DIRECTION_LASTEST_CONF_TIME, DateHelper.getDateOfyyMMddHHmmss(new Date()));
            // 校验参数
            if (dto.getStartSiteId() == null ||
                    dto.getEndSiteId() == null ||
                    StringUtils.isEmpty(dto.getStartSiteName()) ||
                    StringUtils.isEmpty(dto.getEndSiteName()) ||
                    StringUtils.isEmpty(dto.getBoxReceiveName()) ||
                    dto.getBoxReceiveId() == null ||
                    StringUtils.isEmpty(dto.getBoxPkgName()) ||
                    dto.getTransportType() == null ||
                    dto.getFlowType() == null ||
                    dto.getCollectClaim() == null ||
                    StringUtils.isEmpty(dto.getUpdateDate()) ||
                    dto.getSupportDeputyReceiveSite() == null) {
                result.setCode(2);
                result.setMessage("参数不能为空");
                return result;
            }
            if (Objects.equals(dto.getSupportDeputyReceiveSite(), 1)) {
                if (dto.getDeputyBoxReceiveId() == null) {
                    result.setCode(2);
                    result.setMessage("支持副流向时，副流向id不能为空");
                    return result;
                }
                if (StringUtils.isEmpty(dto.getDeputyBoxPkgName())) {
                    result.setCode(2);
                    result.setMessage("支持副流向时，副流向包牌名称不能为空");
                    return result;
                }
                if (Objects.equals(dto.getCollectClaim(), COLLECT_CLAIM_FINISHED_BOX)) {
                    result.setCode(2);
                    result.setMessage("参数错误，成品包不支持副流向");
                    return result;
                }
            }
            if (dto.getStartOrgId() == null) {
                BaseStaffSiteOrgDto baseSiteBySiteId = basicPrimaryWS.getBaseSiteBySiteId(dto.getStartSiteId());
                if (baseSiteBySiteId != null) {
                    dto.setStartOrgId(baseSiteBySiteId.getOrgId());
                    dto.setStartProvinceAgencyCode(baseSiteBySiteId.getProvinceAgencyCode());
                    dto.setStartProvinceAgencyName(baseSiteBySiteId.getProvinceAgencyName());
                    dto.setStartAreaHubCode(baseSiteBySiteId.getAreaCode());
                    dto.setStartAreaHubName(baseSiteBySiteId.getAreaName());
                }
            }
            if (dto.getEndOrgId() == null) {
                BaseStaffSiteOrgDto baseSiteBySiteId = basicPrimaryWS.getBaseSiteBySiteId(dto.getEndSiteId());
                if (baseSiteBySiteId != null) {
                    dto.setEndOrgId(baseSiteBySiteId.getOrgId());
                    dto.setEndProvinceAgencyCode(baseSiteBySiteId.getProvinceAgencyCode());
                    dto.setEndProvinceAgencyName(baseSiteBySiteId.getProvinceAgencyName());
                    dto.setEndAreaHubCode(baseSiteBySiteId.getAreaCode());
                    dto.setEndAreaHubName(baseSiteBySiteId.getAreaName());
                }
            }
            if (StringUtils.isEmpty(dto.getDeputyBoxReceiveName())) {
                BaseStaffSiteOrgDto deputyBoxReceive = basicPrimaryWS.getBaseSiteBySiteId(dto.getDeputyBoxReceiveId());
                if (deputyBoxReceive != null) {
                    dto.setDeputyBoxReceiveName(deputyBoxReceive.getSiteName());
                }
            }
            //删除老版本数据
            addAndDeleteHistory(dto.getUpdateDate());
            
            
            CollectBoxFlowDirectionConf conf = new CollectBoxFlowDirectionConf();
            BeanUtils.copyProperties(dto, conf);
            conf.setUpdateTime(new Date());

            conf.setUpdateUserErp("大数据更新规则");
            conf.setVersion(dto.getUpdateDate());
            OrgEnum startOrgEnum = OrgEnum.getOrgEnum(dto.getStartOrgId());
            OrgEnum endOrgEnum = OrgEnum.getOrgEnum(dto.getEndOrgId());

            conf.setStartOrgName(startOrgEnum == null ? "" : startOrgEnum.getOrgName());
            conf.setEndOrgName(endOrgEnum == null ? "" : endOrgEnum.getOrgName());

            Result<CollectBoxFlowDirectionConf> verifyResult = verifyService.verifyBoxFlowDirectionConf(conf);
            //  1.看是否更新了可混包和成品包 指定可混包
            Integer originCollectClaim = verifyResult.getData() == null ? null : verifyResult.getData().getCollectClaim();
            Integer collectClaim = conf.getCollectClaim();
            //未更新
            if (Objects.equals(originCollectClaim, collectClaim)) {
                conf.setIfChangeSinceLastUpdate(0);
            } else {
                conf.setIfChangeSinceLastUpdate(conf.getCollectClaim());
            }
            conf.setCreateUserErp(verifyResult.getData() == null ? "大数据推送规则" : verifyResult.getData().getCreateUserErp());
            conf.setYn(true);


            Result<Boolean> booleanBaseEntity = confService.updateOrNewConfig(conf);
            boolean success = booleanBaseEntity.isSuccess();
            if (success) {
                result.setCode(0);
                result.setMessage("ok");
            } else {
                result.setCode(1);
                result.setMessage(booleanBaseEntity.getMessage());
            }
        }catch (Exception e){
            log.error("大数据更新集包规则异常",e);
            result.toError("系统异常");
        }
        
        return result;
    }
    
    private void addAndDeleteHistory(String version){
        String key = COLLECT_BOX_FLOW_ADD_AND_DELETE_HISTORY + version;
        try {
            if(cluster.set(key, "1", 10, TimeUnit.MINUTES, false)){
                boolean isNew = addCollectBoxFlowInfo(version);
                if(isNew){
                    deleteHistory();
                }
            }
        }catch (Exception e){
            log.error("大数据推送小件集包新版本，新增版本信息删除历史时异常", e);
            cluster.del(key);
        }
    }

    private void deleteHistory() {
        Runnable runnable = () -> {
            CollectBoxFlowInfo history = collectBoxFlowInfoDao.selectByCreateTimeAndStatus(null, null, HISTORY.getCode());
            if (history == null) {
                log.info("大数据推送小件集包新版本，未查到历史版本数据");
                return;
            }
            int count = 0;
            int sum = 0;
            do {
                count = confService.deleteByVersion(history.getVersion(), DELETE_COUNT);
                sum += count;
            } while (count > 0);
            collectBoxFlowInfoDao.deleteByPrimaryKey(history.getId());
            log.info("大数据推送小件集包新版本,删除历史版本数据version:{},sum:{}", history.getVersion(), sum);
        };

        try {
            deleteHistoryExecutorService.execute(runnable);
        } catch (RejectedExecutionException e) {
            log.warn("删除历史版本数据正在运行，拒绝新加");
        }
    }
    //新增集包规则主表信息
    private boolean addCollectBoxFlowInfo(String version){
        
        CollectBoxFlowInfo collectBoxFlowInfo = collectBoxFlowInfoDao.selectByVersion(version);
        if(collectBoxFlowInfo != null){
            log.error("大数据推送小件集包新版本:{}", version);
            return false;
        }

        //发jmq center消费推送消息
        CollectBoxFlowNoticDto noticDto = initCollectBoxFlowNoticDto(version);
        try {
            collectBoxFlowNoticeMQ.send(version, JsonHelper.toJSONString(noticDto));
        } catch (Exception ex) {
            log.error("大数据推送新箱号规则，MQ发送通知失败:{}",version, ex);
        }
        // 新增版本
        CollectBoxFlowInfo entity = initCollectBoxFlowInfo(version);
        collectBoxFlowInfoDao.insert(entity);
        return true;
    }
    
    
    private CollectBoxFlowInfo initCollectBoxFlowInfo(String version){
        CollectBoxFlowInfo entity = new CollectBoxFlowInfo();
        entity.setVersion(version);
        entity.setStatus(UNACTIVATED.getCode());
        entity.setOperateType(ADD.getCode());
        return entity;
    }

    private CollectBoxFlowNoticDto initCollectBoxFlowNoticDto(String version){
        CollectBoxFlowNoticDto dto = new CollectBoxFlowNoticDto();
        dto.setVersion(version);
        Date todayFistTime = DateHelper.transTimeMinOfDate(new Date());
        Date after3Day = DateHelper.addDays(todayFistTime, 3);
        String after3DayStr = DateHelper.getDateOfyyMMdd2(after3Day);
        dto.setEffectTime(after3DayStr + " 10:00");
        dto.setOperateType(CollectBoxFlowNoticTypeEnum.ADD.getCode());
        return dto;
    }
    
}
