package com.jdl.basic.provider.core.service.boxFlow;


import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowInfo;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;

import java.util.List;


public interface ICollectBoxFlowDirectionConfService {

    Result<CollectBoxFlowDirectionConf> selectById(Long id);

    Result<Boolean> newConfig(CollectBoxFlowDirectionConf conf);


    Result<Boolean> updateConfig(CollectBoxFlowDirectionConf conf);
    /**
     * 根据条件和是否已经配置查询
     *
     * @param pager
     * @param configed 是否已经配置，1：已经配置，0：未配置，null：全部
     * @return
     */
    Result<Pager<CollectBoxFlowDirectionConf>> listByParamAndWhetherConfiged(Pager<CollectBoxFlowDirectionConf> pager, Boolean configed);

    Result<Boolean> updateOrNewConfig(CollectBoxFlowDirectionConf conf);

    int deleteByVersion(String version, Integer deleteCount);

    void switchNewVersion() throws Exception;

    Result<Boolean> rollbackVersion();

    List<CollectBoxFlowInfo> selectAllCollectBoxFlowInfo();

    String getCurrentVersion();

    int updateCollectBoxFlowInfo(CollectBoxFlowInfo collectBoxFlowInfo);

    Result<Pager<CollectBoxFlowDirectionConf>> listCollectBoxFlowDirectionConf(Pager<CollectBoxFlowDirectionConf> pager);

    Result<Boolean> updateCollectBoxFlowDirectionConf(CollectBoxFlowDirectionConf conf);
}
