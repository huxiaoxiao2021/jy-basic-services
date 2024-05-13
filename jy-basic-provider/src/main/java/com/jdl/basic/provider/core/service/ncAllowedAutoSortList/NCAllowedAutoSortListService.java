package com.jdl.basic.provider.core.service.ncAllowedAutoSortList;

import com.jdl.basic.api.domain.ncAllowedAutoSortList.NCAllowedAutoSortListQuery;
import com.jdl.basic.api.domain.ncWhiteList.NCWhiteListQuery;
import com.jdl.basic.provider.core.po.NCAllowedAutoSortList;
import com.jdl.basic.provider.core.po.NCAllowedAutoSortRule;
import com.jdl.basic.provider.core.po.NCWhiteList;
import com.jdl.basic.provider.core.po.NCWhiteRule;

import java.util.List;

public interface NCAllowedAutoSortListService {

    public List<NCAllowedAutoSortList> queryListByCondition(NCAllowedAutoSortListQuery query);

    public List<NCAllowedAutoSortRule> queryRuleByRefId(int refId);

    public int countByCondition(NCAllowedAutoSortListQuery query);

    public int add(NCAllowedAutoSortList ncAllowedAutoSortList, List<NCAllowedAutoSortRule> rules);

    public int modify(NCAllowedAutoSortList ncAllowedAutoSortList, List<NCAllowedAutoSortRule> rules);

    public int removeById(int id);

}
