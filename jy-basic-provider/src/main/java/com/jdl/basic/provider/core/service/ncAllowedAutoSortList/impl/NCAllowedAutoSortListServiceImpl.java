package com.jdl.basic.provider.core.service.ncAllowedAutoSortList.impl;

import com.jdl.basic.api.domain.ncAllowedAutoSortList.NCAllowedAutoSortListQuery;
import com.jdl.basic.provider.core.dao.ncAllowedAutoSortList.NCAllowedAutoSortListDao;
import com.jdl.basic.provider.core.dao.ncAllowedAutoSortList.NCAllowedAutoSortRuleDao;
import com.jdl.basic.provider.core.po.NCAllowedAutoSortList;
import com.jdl.basic.provider.core.po.NCAllowedAutoSortRule;
import com.jdl.basic.provider.core.service.ncAllowedAutoSortList.NCAllowedAutoSortListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NCAllowedAutoSortListServiceImpl implements NCAllowedAutoSortListService {

    @Autowired
    private NCAllowedAutoSortListDao ncAllowedAutoSortListDao;

    @Autowired
    private NCAllowedAutoSortRuleDao ncAllowedAutoSortRuleDao;

    @Override
    public List<NCAllowedAutoSortList> queryListByCondition(NCAllowedAutoSortListQuery query) {
        query.setOffset();
        return ncAllowedAutoSortListDao.queryByCondition(query);
    }

    @Override
    public List<NCAllowedAutoSortRule> queryRuleByRefId(int refId) {
         return ncAllowedAutoSortRuleDao.queryByRefId(refId);
    }

    @Override
    public int countByCondition(NCAllowedAutoSortListQuery query) {
        return ncAllowedAutoSortListDao.countByCondition(query).intValue();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,rollbackFor = {Exception.class})
    public int add(NCAllowedAutoSortList ncAllowedAutoSortList, List<NCAllowedAutoSortRule> rules) {
        updateRuleDetail(ncAllowedAutoSortList, rules);
        int inserted = ncAllowedAutoSortListDao.insert(ncAllowedAutoSortList);

        if (Objects.nonNull(rules) && rules.size() > 0) {
            rules.forEach(e->e.setRefId(ncAllowedAutoSortList.getId()));
            ncAllowedAutoSortRuleDao.insert(rules);
        }
        return inserted;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,rollbackFor = {Exception.class})
    public int modify(NCAllowedAutoSortList ncAllowedAutoSortList, List<NCAllowedAutoSortRule> rules) {
        if (Objects.nonNull(rules) && rules.size() > 0) {
            ncAllowedAutoSortRuleDao.deleteByRefId(ncAllowedAutoSortList.getId());
            rules.forEach(e->e.setRefId(ncAllowedAutoSortList.getId()));
            ncAllowedAutoSortRuleDao.insert(rules);
        } else {
            ncAllowedAutoSortRuleDao.deleteByRefId(ncAllowedAutoSortList.getId());
        }

        updateRuleDetail(ncAllowedAutoSortList, rules);
        int inserted = ncAllowedAutoSortListDao.updateByPK(ncAllowedAutoSortList);
        return inserted;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor = {Exception.class})
    public int removeById(int id) {
        int deleted = ncAllowedAutoSortListDao.deleteByPK(id);
        ncAllowedAutoSortRuleDao.deleteByRefId(id);
        return deleted;
    }

    private void updateRuleDetail(NCAllowedAutoSortList ncAllowedAutoSortList, List<NCAllowedAutoSortRule> rules) {
        if (Objects.isNull(rules) || rules.size() == 0) {
            ncAllowedAutoSortList.setRuleDetail("");
            return;
        }
        String ruleDetail = generateRuleDetail(rules);
        ncAllowedAutoSortList.setRuleDetail(ruleDetail);
    }

    private String generateRuleDetail(List<NCAllowedAutoSortRule> rules) {
        return rules.stream()
                .sorted(Comparator.comparing(NCAllowedAutoSortRule::getQuotaName).thenComparing(NCAllowedAutoSortRule::getLtValue))
                .map(e->e.toString()).collect(Collectors.joining(";"));
    }
}
