package com.jdl.basic.provider.core.dao.boxFlow;


import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectBoxFlowDirectionConfDao {
    int deleteByPrimaryKey(Long id);

    int insert(CollectBoxFlowDirectionConf record);

    int insertSelective(CollectBoxFlowDirectionConf record);

    CollectBoxFlowDirectionConf selectByPrimaryKey(Long id);

    List<CollectBoxFlowDirectionConf> select(CollectBoxFlowDirectionConf collectBoxFlowDirectionConf);
    List<CollectBoxFlowDirectionConf> selectConfiged(CollectBoxFlowDirectionConf collectBoxFlowDirectionConf);

    int updateByPrimaryKeySelective(CollectBoxFlowDirectionConf record);

    int updateByPrimaryKey(CollectBoxFlowDirectionConf record);

    int updateStartSiteNameById(@Param("ids") List<Long> ids, @Param("newStartSiteName") String newStartSiteName);

    int updateEndSiteNameById(@Param("ids") List<Long> ids, @Param("newEndSiteName") String newEndSiteName);

    int updateBoxReceiveNameById(@Param("ids") List<Long> ids, @Param("newBoxReceiveName") String newBoxReceiveName);

    List<CollectBoxFlowDirectionConf> selectByParamAndWhetherConfiged(@Param("conf") CollectBoxFlowDirectionConf collectBoxFlowDirectionConf, @Param("configed") Boolean configed);

}