package com.jdl.basic.provider.core.service.transferDp.impl;

import com.alibaba.fastjson.JSON;
import com.jd.dms.java.utils.sdk.base.Result;
import com.jd.dms.java.utils.sdk.constants.ResultCodeConstant;
import com.jdl.basic.api.domain.transferDp.ConfigTransferDpSite;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteMatchQo;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteQo;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.core.dao.transferDp.ConfigTransferDpSiteDao;
import com.jdl.basic.provider.core.service.transferDp.ConfigTransferDpBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    /**
     * 查询匹配的配置记录
     *
     * @param configTransferDpSiteMatchQo 查询入参
     * @return 匹配的数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    @Override
    public Result<ConfigTransferDpSite> queryMatchConditionRecord(ConfigTransferDpSiteMatchQo configTransferDpSiteMatchQo) {
        log.info("ConfigTransferDpBusinessServiceImpl.queryMatchConditionRecord param {}", JSON.toJSONString(configTransferDpSiteMatchQo));
        final Result<ConfigTransferDpSite> result = Result.success();
        try {
            final Result<Void> checkResult = this.checkParam4QueryMatchConditionRecord(configTransferDpSiteMatchQo);
            if (!checkResult.isSuccess()) {
                log.warn("ConfigTransferDpBusinessServiceImpl.queryMatchConditionRecord checkParam warn {} {}", JsonHelper.toJSONString(checkResult), JSON.toJSONString(configTransferDpSiteMatchQo));
                return result.toFail(checkResult.getMessage(), checkResult.getCode());
            }
            // todo 主动缓存，通过内存计算
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
