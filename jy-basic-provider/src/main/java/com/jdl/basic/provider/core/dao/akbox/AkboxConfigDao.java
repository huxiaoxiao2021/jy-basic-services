package com.jdl.basic.provider.core.dao.akbox;


import com.jdl.basic.api.domain.akbox.AkboxConfig;
import com.jdl.basic.api.domain.akbox.AkboxConfigQuery;

import java.util.List;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 11:19
 * @Description:
 */
public interface AkboxConfigDao {

    /**
     *新增
     * @param
     * @return
     */
    int insert(AkboxConfig akboxConfig);


    /**
     *根据主键获取详情
     * @param
     * @return
     */
    AkboxConfig selectById(Long id);

    AkboxConfig selectBySiteCode(Long siteCode);

    /**
     *根据主键更新集箱包裹配置信息
     * @param
     * @return
     */
    int updateById(AkboxConfig akboxConfig);

    int updateBySiteCode(AkboxConfig akboxConfig);


    /**
     *批量插入
     * @param
     * @return
     */
    int batchInsert(List<AkboxConfig> akboxConfigList);

    /**
     *根据条件查询
     * @param
     * @return
     */
    List<AkboxConfig> queryByCondition(AkboxConfigQuery query);

}
