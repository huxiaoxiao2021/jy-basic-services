package com.jdl.basic.provider.core.dao.boxFlow;


import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.provider.core.dao.boxFlow.query.CollectBoxFlowDirectionConfQuery;
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


    List<CollectBoxFlowDirectionConf> selectByStartSiteIdAndEndSiteIds(CollectBoxFlowDirectionConfQuery query);
    
    int deleteByVersion(@Param("version") String version, @Param("deleteCount") Integer deleteCount);

    /**
     * 刷数-分页查询
     * @param startId
     * @return
     */
    List<CollectBoxFlowDirectionConf> brushQueryAllByPage(Integer startId);

    /**
     * 刷数-批量更新
     * @param list
     * @return
     */
    Integer brushUpdateById(List<CollectBoxFlowDirectionConf> list);
    List<CollectBoxFlowDirectionConf> selectPageById(@Param("id") Long id, @Param("collectClaim")Integer collectClaim,
                                                     @Param("version") String version);

}
