package com.jdl.basic.provider.core.service.boxFlow.impl;

import com.alibaba.fastjson.JSONObject;

import com.jd.jim.cli.Cluster;
import com.jd.jmq.client.producer.MessageProducer;
import com.jd.jmq.common.message.Message;
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
import java.util.concurrent.TimeUnit;

import static com.jdl.basic.common.enums.CollectBoxFlowInfoOperateTypeEnum.ACTIVATE;
import static com.jdl.basic.common.enums.CollectBoxFlowInfoOperateTypeEnum.ADD;
import static com.jdl.basic.common.enums.CollectBoxFlowInfoStatusEnum.UNACTIVATED;

@Service("collectBoxFlowDirectionConfPushService")
@Slf4j
public class CollectBoxFlowDirectionConfPushServiceImpl  implements ICollectBoxFlowDirectionConfPushService {

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
                    StringUtils.isEmpty(dto.getUpdateDate())) {
                result.setCode(2);
                result.setMessage("参数不能为空");
                return result;
            }
            if (dto.getStartOrgId() == null) {
                BaseStaffSiteOrgDto baseSiteBySiteId = basicPrimaryWS.getBaseSiteBySiteId(dto.getStartSiteId());
                if (baseSiteBySiteId != null) {
                    dto.setStartOrgId(baseSiteBySiteId.getOrgId());
                }
            }
            if (dto.getEndOrgId() == null) {
                BaseStaffSiteOrgDto baseSiteBySiteId = basicPrimaryWS.getBaseSiteBySiteId(dto.getEndSiteId());
                if (baseSiteBySiteId != null) {
                    dto.setEndOrgId(baseSiteBySiteId.getOrgId());
                }
            }
            //删除老版本数据
            addCollectBoxFlowInfo(dto.getUpdateDate());
            
            
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
    
    private void deleteAllOldVersion(String version){
        String key = COLLECT_BOX_FLOW_DIRECTION_VERSION_UPDATE + version;
        String isUpdate = cluster.get(key);
        log.info("查询缓存判断历史版本是否已清除key:{},isUpdate:{}", key, isUpdate);
        if(isUpdate != null){
           return; 
        }
        try {
            if(cluster.set(key, "1", 15, TimeUnit.DAYS, false)){
                int count = 0;
                int sum = 0;
                do {
                    count = confService.deleteOldVersion(version, DELETE_COUNT);
                    sum += count;
                }while (count > 0);

                log.info("删除历史版本数据key:{},isUpdate:{},count:{}", key, isUpdate, sum);
            }
        }catch (Exception e){
            cluster.del(key);
            throw e;
        }
    }
    //新增集包规则主表信息
    private void addCollectBoxFlowInfo(String version){
        
        CollectBoxFlowInfo collectBoxFlowInfo = collectBoxFlowInfoDao.selectByVersion(version);

        //发jmq center消费推送消息
        CollectBoxFlowNoticDto noticDto = initCollectBoxFlowNoticDto(version);
        try {
            collectBoxFlowNoticeMQ.send(version, JsonHelper.toJSONString(noticDto));
        } catch (Exception ex) {
            log.error("大数据推送新箱号规则，MQ发送通知失败:{}",version, ex);
            return;
        }
        // 新增版本
        if(collectBoxFlowInfo == null){
            CollectBoxFlowInfo entity = initCollectBoxFlowInfo(version);
            collectBoxFlowInfoDao.insert(entity);
        }
        
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
        dto.setOperateType(ADD.getCode());
        return dto;
    }
    
}
