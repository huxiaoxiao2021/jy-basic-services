package com.jdl.basic.provider.core.provider.transferDp;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.transferDp.ConfigTransferDpSite;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteMatchQo;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteQo;
import com.jdl.basic.api.service.transferDp.ConfigTransferDpBusinessApi;
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
public class ConfigTransferDpBusinessApiImpl implements ConfigTransferDpBusinessApi {

    @Resource
    private ConfigTransferDpBusinessService configTransferDpBusinessService;

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
        return configTransferDpBusinessService.queryMatchConditionRecord(configTransferDpSiteMatchQo);
    }
}
