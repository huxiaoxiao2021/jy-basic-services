package com.jdl.basic.provider.core.dao.ncWhiteList;





import com.jdl.basic.api.domain.ncWhiteList.NCWhiteListQuery;
import com.jdl.basic.provider.core.po.NCWhiteList;

import java.util.List;


public interface NCWhiteListDao {

    int insert(NCWhiteList ncWhiteList);

    int updateByPK(NCWhiteList ncWhiteList);

    int deleteByPK(int id);

    List<NCWhiteList> queryByCondition(NCWhiteListQuery query);

    Long countByCondition(NCWhiteListQuery query);
}
