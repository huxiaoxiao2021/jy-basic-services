package com.jdl.basic.provider.core.service.ncWhitelist.impl;

import com.jdl.basic.api.domain.ncWhiteList.NCWhiteListQuery;
import com.jdl.basic.provider.core.dao.ncWhiteList.NCWhiteListDao;
import com.jdl.basic.provider.core.dao.ncWhiteList.NCWhiteRuleDao;
import com.jdl.basic.provider.core.po.NCWhiteList;
import com.jdl.basic.provider.core.po.NCWhiteRule;
import com.jdl.basic.provider.core.service.ncWhitelist.NCWhiteListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NCWhiteListServiceImpl implements NCWhiteListService {

    @Autowired
    private NCWhiteListDao ncWhiteListDao;

    @Autowired
    private NCWhiteRuleDao ncWhiteRuleDao;

    @Override
    public List<NCWhiteList> queryWhiteListByCondition(NCWhiteListQuery query) {
        query.setOffset(query.getOffset());
        return ncWhiteListDao.queryByCondition(query);
    }

    @Override
    public List<NCWhiteRule> queryRuleByRefId(int refId) {
         return ncWhiteRuleDao.queryByRefId(refId);
    }

    @Override
    public int countByCondition(NCWhiteListQuery query) {
        return ncWhiteListDao.countByCondition(query).intValue();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor = {Exception.class})
    public int add(NCWhiteList ncWhiteList, List<NCWhiteRule> rules) {
        updateRuleDetail(ncWhiteList,rules);
        int id = ncWhiteListDao.insert(ncWhiteList);

        rules.forEach(e->e.setRefId(id));
        ncWhiteRuleDao.insert(rules);
        return id;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor = {Exception.class})
    public int modify(NCWhiteList ncWhiteList, List<NCWhiteRule> rules) {

        ncWhiteRuleDao.deleteByRefId(ncWhiteList.getId());
        rules.forEach(e->e.setRefId(ncWhiteList.getId()));
        ncWhiteRuleDao.insert(rules);

        updateRuleDetail(ncWhiteList,rules);
        int inserted = ncWhiteListDao.updateByPK(ncWhiteList);
        return inserted;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor = {Exception.class})
    public int removeById(int id) {
        int deleted = ncWhiteListDao.deleteByPK(id);
        ncWhiteRuleDao.deleteByRefId(id);
        return deleted;
    }

    private void updateRuleDetail(NCWhiteList ncWhiteList, List<NCWhiteRule> rules) {
        String ruleDetail = generateRuleDetail(rules);
        ncWhiteList.setRuleDetail(ruleDetail);
    }

    private String generateRuleDetail(List<NCWhiteRule> rules) {
        return rules.stream()
                .sorted(Comparator.comparing(NCWhiteRule::getQuotaName).thenComparing(NCWhiteRule::getLtValue))
                .map(e->e.toString()).collect(Collectors.joining(";"));
    }
}
