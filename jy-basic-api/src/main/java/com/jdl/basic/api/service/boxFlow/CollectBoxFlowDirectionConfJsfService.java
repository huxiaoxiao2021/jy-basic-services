package com.jdl.basic.api.service.boxFlow;


import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;


public interface CollectBoxFlowDirectionConfJsfService {

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

}
