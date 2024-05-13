package com.jdl.basic.provider.core.dao.ncAllowedAutoSortList;





import com.jdl.basic.api.domain.ncAllowedAutoSortList.NCAllowedAutoSortListQuery;
import com.jdl.basic.provider.core.po.NCAllowedAutoSortList;

import java.util.List;


public interface NCAllowedAutoSortListDao {

    int insert(NCAllowedAutoSortList ncAllowedAutoSortList);

    int updateByPK(NCAllowedAutoSortList ncAllowedAutoSortList);

    int deleteByPK(int id);

    List<NCAllowedAutoSortList> queryByCondition(NCAllowedAutoSortListQuery query);

    Long countByCondition(NCAllowedAutoSortListQuery query);
}
