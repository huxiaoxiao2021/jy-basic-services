package com.jdl.basic.provider.core.dao.ncWhiteList;





import com.jdl.basic.provider.core.po.NCWhiteList;
import com.jdl.basic.provider.core.po.NCWhiteRule;

import java.util.List;


public interface NCWhiteRuleDao {

    int insert(List<NCWhiteRule> rules);

    int deleteByRefId(int refId);

    List<NCWhiteRule> queryByRefId(int refId);
}
