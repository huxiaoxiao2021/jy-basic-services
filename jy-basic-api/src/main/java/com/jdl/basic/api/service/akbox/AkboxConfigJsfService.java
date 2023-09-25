package com.jdl.basic.api.service.akbox;

import com.jdl.basic.api.domain.akbox.AkboxConfig;
import com.jdl.basic.api.domain.akbox.AkboxConfigQuery;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;

import java.util.List;

public interface AkboxConfigJsfService {

    /**
     * 保存 同站点只能保存一次
     * @param akboxConfig
     * @return
     */
    Result<Integer> save(AkboxConfig akboxConfig);

    /**
     * 匹配导入 根据siteCode查找 相同替换 错误信息在message里
     * @param akboxConfigList
     * @return
     */
    Result<Integer> batchSave(List<AkboxConfig> akboxConfigList);

    /**
     * 根据id跟新数据
     * @param akboxConfig
     * @return
     */
    Result<Integer> update(AkboxConfig akboxConfig);

    /**
     * 根据 省区 枢纽 站点 查询 根据时间倒序查询
     * @param query
     * @return
     */
    Result<Pager<AkboxConfig>> queryPageByCondition(AkboxConfigQuery query);

    Result<AkboxConfig> queryBySiteCode(Integer siteCode);
}
