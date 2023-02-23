package com.jdl.basic.provider.core.service.boxFlow.impl;

import com.alibaba.fastjson.JSONObject;

import com.jd.jim.cli.Cluster;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.ws.BasicPrimaryWS;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionPushConfDto;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionConfPushService;
import com.jdl.basic.common.enums.OrgEnum;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.boxFlow.CollectBoxFlowDirectionConfDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service("collectBoxFlowDirectionConfPushService")
@Slf4j
public class CollectBoxFlowDirectionConfPushServiceImpl  implements ICollectBoxFlowDirectionConfPushService {

    @Resource
    private CollectBoxFlowDirectionConfDao collectBoxFlowDirectionConfMapper;

    @Autowired
    CollectBoxFlowDirectionConfServiceImpl confService;

    @Autowired
    private CollectBoxFlowDirectionVerifyServiceImpl verifyService;

    @Autowired
    Cluster cluster;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    BasicPrimaryWS basicPrimaryWS;

    public static String COLLECT_BOX_FLOW_DIRECTION_LASTEST_CONF_TIME = "COLLECT_BOX_FLOW_DIRECTION_LASTEST_CONF_TIME";
    
    public static String COLLECT_BOX_FLOW_DIRECTION_VERSION_UPDATE = "COLLECT_BOX_FLOW_DIRECTION_VERSION_UPDATE-";


    @Override
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
            //删除老版本数据
            deleteAllOldVersion(dto.getUpdateDate());

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
        if(cluster.set(key, "1", 15, TimeUnit.DAYS, false)){
            int count = confService.deleteOldVersion(version);
            log.info("删除历史版本数据key:{},isUpdate:{},count:{}", key, isUpdate, count);
        }
    }

}
