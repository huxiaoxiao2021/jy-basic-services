package com.jdl.basic.provider.core.service.transferDp.impl;

import com.alibaba.fastjson.JSON;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.dms.java.utils.sdk.constants.ResultCodeConstant;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.transferDp.ConfigTransferDpSite;
import com.jdl.basic.api.dto.enums.ConfigStatusEnum;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteMatchQo;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteQo;
import com.jdl.basic.common.contants.CacheKeyConstants;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.config.cache.CacheService;
import com.jdl.basic.provider.core.dao.transferDp.ConfigTransferDpSiteDao;
import com.jdl.basic.provider.core.service.transferDp.ConfigTransferDpBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 交接至德邦配置
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-11-23 17:29:43 周三
 */
@Slf4j
@Service
public class ConfigTransferDpBusinessServiceImpl implements ConfigTransferDpBusinessService {

    @Resource
    private ConfigTransferDpSiteDao configTransferDpSiteDao;

    @Autowired
    @Qualifier("jimdbCacheService")
    private CacheService cacheService;

    /**
     * 查询匹配的配置记录，不过滤生效时间，由接口调用方自行判断
     *
     * @param configTransferDpSiteMatchQo 查询入参
     * @return 匹配的数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + "ConfigTransferDpBusinessServiceImpl.queryMatchConditionRecord", jAppName = Constants.UMP_APP_NAME, mState = {JProEnum.TP, JProEnum.FunctionError})
    public Result<ConfigTransferDpSite> queryMatchConditionRecord(ConfigTransferDpSiteMatchQo configTransferDpSiteMatchQo) {
        log.info("ConfigTransferDpBusinessServiceImpl.queryMatchConditionRecord param {}", JSON.toJSONString(configTransferDpSiteMatchQo));
        final Result<ConfigTransferDpSite> result = Result.success();
        try {
            final Result<Void> checkResult = this.checkParam4QueryMatchConditionRecord(configTransferDpSiteMatchQo);
            if (!checkResult.isSuccess()) {
                log.warn("ConfigTransferDpBusinessServiceImpl.queryMatchConditionRecord checkParam warn {} {}", JsonHelper.toJSONString(checkResult), JSON.toJSONString(configTransferDpSiteMatchQo));
                return result.toFail(checkResult.getMessage(), checkResult.getCode());
            }
            String cacheKey = String.format(CacheKeyConstants.CACHE_KEY_CONFIG_TRANSFER_DP, configTransferDpSiteMatchQo.getHandoverSiteCode(), configTransferDpSiteMatchQo.getPreSortSiteCode());
            final String existVal = cacheService.get(cacheKey);
            if(existVal != null){
                if(StringUtils.isEmpty(existVal)){
                    return result;
                } else {
                    return result.setData(JsonHelper.toObject(existVal, ConfigTransferDpSite.class));
                }
            }

            ConfigTransferDpSiteQo configTransferDpSiteQo = new ConfigTransferDpSiteQo();
            configTransferDpSiteQo.setHandoverSiteCode(configTransferDpSiteMatchQo.getHandoverSiteCode());
            configTransferDpSiteQo.setPreSortSiteCode(configTransferDpSiteMatchQo.getPreSortSiteCode());
            configTransferDpSiteQo.setConfigStatus(ConfigStatusEnum.ENABLE.getCode());
            final long currentTimeMillis = System.currentTimeMillis();
            // Date currentDate = new Date();
            // configTransferDpSiteQo.setEffectiveStartTime(currentDate);
            // configTransferDpSiteQo.setEffectiveStopTime(currentDate);
            configTransferDpSiteQo.setYn(Constants.YN_YES);
            final ConfigTransferDpSite configTransferDpSite = configTransferDpSiteDao.selectOne(configTransferDpSiteQo);
            boolean matchFlag = false;
            if(configTransferDpSite != null){
                /*if (configTransferDpSite.getEffectiveStartTime().getTime() <= currentTimeMillis && configTransferDpSite.getEffectiveStopTime().getTime() >= currentTimeMillis) {
                    matchFlag = true;
                }*/
                matchFlag = true;
            }
            if(matchFlag){
                cacheService.setEx(cacheKey, JsonHelper.toJSONString(configTransferDpSite), CacheKeyConstants.CACHE_CONFIG_TRANSFER_DP_TIME_EXPIRE, TimeUnit.MINUTES);
            } else {
                cacheService.setEx(cacheKey, Constants.EMPTY_FILL, CacheKeyConstants.CACHE_CONFIG_TRANSFER_DP_TIME_EXPIRE, TimeUnit.MINUTES);
            }
            result.setData(configTransferDpSite);
        } catch (Exception e) {
            log.error("ConfigTransferDpBusinessServiceImpl.queryMatchConditionRecord error ", e);
            result.toFail("查询异常");
        }
        return result;
    }

    private Result<Void> checkParam4QueryMatchConditionRecord(ConfigTransferDpSiteMatchQo configTransferDpSiteMatchQo) {
        Result<Void> result = Result.success();
        if (configTransferDpSiteMatchQo == null) {
            return result.toFail("参数错误，参数不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        if (configTransferDpSiteMatchQo.getHandoverSiteCode() == null) {
            return result.toFail("参数错误，handoverSiteCode不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        if (configTransferDpSiteMatchQo.getPreSortSiteCode() == null) {
            return result.toFail("参数错误，preSortSiteCode不能为空", ResultCodeConstant.ILLEGAL_ARGUMENT);
        }
        return result;
    }
}
