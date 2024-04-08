package com.jdl.basic.provider.core.provider.boxFlow;


import com.alibaba.fastjson.JSON;
import com.jd.jim.cli.Cluster;
import com.jd.lsb.task.domain.Request;
import com.jd.lsb.task.domain.Response;
import com.jd.lsb.task.handler.Handler;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.ws.BasicPrimaryWS;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowInfo;
import com.jdl.basic.api.domain.boxFlow.dto.CollectBoxFlowInfoDto;
import com.jdl.basic.api.service.boxFlow.CollectBoxFlowDirectionConfJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.enums.CollectBoxFlowInfoStatusEnum;
import com.jdl.basic.common.utils.DateHelper;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.boxFlow.ICollectBoxFlowDirectionConfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CollectBoxFlowDirectionConfJsfServiceImpl implements CollectBoxFlowDirectionConfJsfService, Handler {

    public static String SWITCH_NEW_VERSION_KEY = "SWITCH-NEW-VERSION-KEY";
    
    @Autowired
    private ICollectBoxFlowDirectionConfService collectBoxFlowDirectionConfService;

    @Autowired
    Cluster cluster;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    BasicPrimaryWS basicPrimaryWS;
    @Override
    public Result<CollectBoxFlowDirectionConf> selectById(Long id) {
        if(log.isInfoEnabled()){
            log.info("集包规则配置 selectById -{}",id);
        }
        return collectBoxFlowDirectionConfService.selectById(id);
    }

    @Override
    public Result<Boolean> newConfig(CollectBoxFlowDirectionConf conf) {
        log.info("集包规则配置 newConfig -{}", JSON.toJSONString(conf));
        return collectBoxFlowDirectionConfService.newConfig(conf);
    }

    @Override
    public Result<Boolean> updateConfig(CollectBoxFlowDirectionConf conf) {
        log.info("集包规则配置 updateConfig -{}",JSON.toJSONString(conf));
        return collectBoxFlowDirectionConfService.updateConfig(conf);
    }

    @Override
    public Result<Pager<CollectBoxFlowDirectionConf>> listByParamAndWhetherConfiged(Pager<CollectBoxFlowDirectionConf> pager, Boolean configed) {
        if(log.isInfoEnabled()){
            log.info("集包规则配置 listByParamAndWhetherConfiged {} ,{}",JSON.toJSONString(pager),configed);
        }
        return collectBoxFlowDirectionConfService.listByParamAndWhetherConfiged(pager,configed);
    }

    @Override
    public Result<Boolean> rollbackVersion() {
        Result<Boolean> result = new Result<>();
        result.toSuccess();
        try {
            if(cluster.set(SWITCH_NEW_VERSION_KEY, "1", 20, TimeUnit.MINUTES, false)){
                result = collectBoxFlowDirectionConfService.rollbackVersion();
            }else {
                result.toFail("版本正在切换，不能频繁操作，请稍后");
            }
        }catch (Exception e){
            log.error("定时切换集包版本异常", e);
            result.toFail("服务异常请联系值班研发查看");
            throw e;
        }finally {
            cluster.del(SWITCH_NEW_VERSION_KEY);
        }
        return result;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionConfJsfServiceImpl.handle", mState = {JProEnum.TP, JProEnum.FunctionError})
    public void handle(Request request, Response response) throws Throwable {
        try {
            if(cluster.set(SWITCH_NEW_VERSION_KEY, "1", 20, TimeUnit.MINUTES, false)){
                collectBoxFlowDirectionConfService.switchNewVersion();
            }
        }catch (Exception e){
            log.error("定时切换集包版本异常", e);
            throw e;
        }finally {
            cluster.del(SWITCH_NEW_VERSION_KEY);
        }
        
    }
    @Override
    public Result<List<CollectBoxFlowInfoDto>> selectAllCollectBoxFlowInfo(){
        Result<List<CollectBoxFlowInfoDto>> result = new Result<>();
        result.toSuccess();
        try {
            List<CollectBoxFlowInfo> collectBoxFlowInfos = collectBoxFlowDirectionConfService.selectAllCollectBoxFlowInfo();
            if(CollectionUtils.isNotEmpty(collectBoxFlowInfos)){
                List<CollectBoxFlowInfoDto> dtos = new ArrayList<>(collectBoxFlowInfos.size());
                for(CollectBoxFlowInfo conf : collectBoxFlowInfos){
                    CollectBoxFlowInfoDto dto = new CollectBoxFlowInfoDto();
                    dtos.add(dto);
                    BeanUtils.copyProperties(conf, dto);
                    if(conf.getStatus().equals(CollectBoxFlowInfoStatusEnum.UNACTIVATED.getCode())){
                        Date todayFistTime = DateHelper.transTimeMinOfDate(conf.getCreateTime());
                        Date after3Day = DateHelper.addDays(todayFistTime, 3);
                        String after3DayStr = DateHelper.getDateOfyyMMdd2(after3Day);
                        dto.setEffectTime(after3DayStr + " 10:00");
                    }
                    dto.setStatusName(CollectBoxFlowInfoStatusEnum.getNameByCode(conf.getStatus()));
                }
                result.setData(dtos);
            }
           
        }catch (Exception e){
            result.toError("查询集包规则版本状态时，服务器异常，请稍后重试");
            log.error("查询集包规则版本状态时，服务器异常，请稍后重试");
        }
        return result;
    }

    @Override
    public Result<String> getCurrentVersion() {
        Result<String> result = new Result<>();
        result.toSuccess();
        try {
            String version = collectBoxFlowDirectionConfService.getCurrentVersion();
            result.setData(version);
        }catch (Exception e){
            log.error("集包规则查询当前版本号异常",e);
            result.toError("集包规则查询当前版本号异常,请稍后再试");
        }
        return result;
    }

    @Override
    public Result<Integer> updateCollectBoxFlowInfo(CollectBoxFlowInfo collectBoxFlowInfo) {
        Result<Integer> result = new Result<>();
        result.toSuccess();
        try {
            int num = collectBoxFlowDirectionConfService.updateCollectBoxFlowInfo(collectBoxFlowInfo);
            result.setData(num);
        }catch (Exception e){
            log.error("集包规则查询当前版本号异常",e);
            result.toError("集包规则查询当前版本号异常,请稍后再试");
        }
        return result;
    }

    /**
     * 工具类：批量更新集包流向配置
     * 更新内容：省区、枢纽
     * 更新规则：根据startSiteId\endSiteId分页查询省区、枢纽信息
     * @return 更新结果
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".CollectBoxFlowDirectionConfJsfServiceImpl.batchUpdateCollectBoxFlowDirectionConf", mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<Boolean> batchUpdateCollectBoxFlowDirectionConf() {
        Result<Boolean> result = new Result<>();
        try {
            int pageNumber = 1; // 页码
            int pageSize = 100; // 每页大小
            boolean hasMoreData = true;
            log.info("集包规则配置 updateConfig 开始");
            while (hasMoreData) {
                Pager<CollectBoxFlowDirectionConf> pager = new Pager<>(pageNumber, pageSize, 0L);
                // 分页查询集包配置
                Result<Pager<CollectBoxFlowDirectionConf>> pagerResult =
                    collectBoxFlowDirectionConfService.listByParamAndWhetherConfiged(pager, false);
                log.info("集包规则配置 updateConfig 分页信息-{}",JSON.toJSONString(pager));
                if (pagerResult.isSuccess()) {
                    Pager<CollectBoxFlowDirectionConf> pagerResultData = pagerResult.getData();
                    List<CollectBoxFlowDirectionConf> confList = pagerResultData.getData();
                    if (confList != null && !confList.isEmpty()) {
                        for (CollectBoxFlowDirectionConf conf : confList) {
                            // 执行更新逻辑
                            updateDirectionConf(conf);
                        }
                        pageNumber++; // 下一页
                    } else {
                        hasMoreData = false;
                    }
                }
            }
            log.info("集包规则配置 updateConfig 结束");
            result.setData(true);
        } catch (Exception e) {
            // 记录日志
            log.error("集包规则查询当前版本号异常",e);
            result.setData(false);
            result.toError("集包规则查询当前版本号异常,请稍后再试");

        }
        return result;
    }

    private void updateDirectionConf(CollectBoxFlowDirectionConf conf) {
        // 通过调用getBaseSiteBySiteId方法查询始发站点和目的站点信息
        BaseStaffSiteOrgDto startSiteInfo = basicPrimaryWS.getBaseSiteBySiteId(conf.getStartSiteId());
        BaseStaffSiteOrgDto endSiteInfo = basicPrimaryWS.getBaseSiteBySiteId(conf.getEndSiteId());
        // 如果始发站点信息和省区编码不为空，则设置始发省区编码和名称
        if(Objects.nonNull(startSiteInfo)&&null != startSiteInfo.getProvinceAgencyCode()){
            conf.setStartProvinceAgencyCode(startSiteInfo.getProvinceAgencyCode());
            conf.setStartProvinceAgencyName(startSiteInfo.getProvinceAgencyName());
        }
        // 如果始发站点信息和区域编码不为空，则设置始发区域枢纽编码和名称
        if(Objects.nonNull(startSiteInfo)&&null != endSiteInfo.getAreaCode()) {
            conf.setStartAreaHubCode(startSiteInfo.getAreaCode());
            conf.setStartAreaHubName(startSiteInfo.getAreaName());
        }
        // 如果目的站点信息和省区编码不为空，则设置目的省区编码和名称
        if(Objects.nonNull(endSiteInfo)&&null != endSiteInfo.getProvinceAgencyCode()) {
            conf.setEndProvinceAgencyCode(endSiteInfo.getProvinceAgencyCode());
            conf.setEndProvinceAgencyName(endSiteInfo.getProvinceAgencyName());
        }
        // 如果目的站点信息和区域编码不为空，则设置目的区域枢纽编码和名称
        if(Objects.nonNull(endSiteInfo)&&null != endSiteInfo.getAreaCode()) {
            conf.setEndAreaHubCode(endSiteInfo.getAreaCode());
            conf.setEndAreaHubName(endSiteInfo.getAreaName());
        }
        // 调用collectBoxFlowDirectionConfService的updateConfig方法更新配置信息
        collectBoxFlowDirectionConfService.updateConfig(conf);
    }
}
