package com.jdl.basic.provider.core.dao.ncAllowedAutoSortList;





import com.jdl.basic.provider.core.po.NCAllowedAutoSortRule;

import java.util.List;


public interface NCAllowedAutoSortRuleDao {

    int insert(List<NCAllowedAutoSortRule> rules);

    int deleteByRefId(int refId);

    List<NCAllowedAutoSortRule> queryByRefId(int refId);
}
