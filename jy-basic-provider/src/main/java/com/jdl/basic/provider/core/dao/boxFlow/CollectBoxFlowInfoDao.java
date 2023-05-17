package com.jdl.basic.provider.core.dao.boxFlow;

import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowInfo;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollectBoxFlowInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(CollectBoxFlowInfo record);

    CollectBoxFlowInfo selectByPrimaryKey(Long id);

    List<CollectBoxFlowInfo> selectAll();

    int updateByPrimaryKey(CollectBoxFlowInfo boxFlowInfo);
    
    CollectBoxFlowInfo selectByVersion(String version);

    CollectBoxFlowInfo selectByCreateTimeAndStatus(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                   @Param("status")Integer status);

    int updateByPrimaryKeySelective(CollectBoxFlowInfo record);

}
