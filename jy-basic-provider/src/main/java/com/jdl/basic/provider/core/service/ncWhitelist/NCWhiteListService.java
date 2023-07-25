package com.jdl.basic.provider.core.service.ncWhitelist;

import com.jdl.basic.api.domain.ncWhiteList.NCWhiteListQuery;
import com.jdl.basic.provider.core.po.NCWhiteList;
import com.jdl.basic.provider.core.po.NCWhiteRule;


import java.util.List;

public interface NCWhiteListService {

    public List<NCWhiteList> queryWhiteListByCondition(NCWhiteListQuery query);

    public List<NCWhiteRule> queryRuleByRefId(int refId);

    public int countByCondition(NCWhiteListQuery query);

    public int add(NCWhiteList ncWhiteList, List<NCWhiteRule> rules);

    public int modify(NCWhiteList ncWhiteList, List<NCWhiteRule> rules);

    public int removeById(int id);

}
