package com.jdl.basic.provider.core.service.transferDp;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.transferDp.ConfigTransferDpSite;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteQo;

/**
 * 交接至德邦配置
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-11-23 17:29:43 周三
 */
public interface ConfigTransferDpBusinessService {

    /**
     * 查询匹配的配置记录
     * @return 分页数据
     * @author fanggang7
     * @time 2022-12-04 10:40:36 周日
     */
    Result<ConfigTransferDpSite> queryMatchConditionRecord(ConfigTransferDpSiteQo configTransferDpSiteQo);

}
