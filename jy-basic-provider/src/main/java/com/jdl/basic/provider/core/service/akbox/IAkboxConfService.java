package com.jdl.basic.provider.core.service.akbox;


import com.jdl.basic.api.domain.akbox.AkboxConfig;
import com.jdl.basic.api.domain.akbox.AkboxConfigQuery;
import com.jdl.basic.common.utils.Pager;

import java.util.List;


public interface IAkboxConfService {

    /**
     * 保存 只保存当前站点一条数据
     * @param akboxConfig
     * @return
     */
    int save(AkboxConfig akboxConfig);

    int batchSave(List<AkboxConfig> akboxConfigList);

    /**
     * 更新
     * @param akboxConfig
     * @return
     */
    int update(AkboxConfig akboxConfig);

    int updateBySiteCode(AkboxConfig akboxConfig);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    Pager<AkboxConfig> queryPageByCondition(Pager<AkboxConfigQuery> query);

    AkboxConfig selectBySiteCode(Long siteCode);


}
