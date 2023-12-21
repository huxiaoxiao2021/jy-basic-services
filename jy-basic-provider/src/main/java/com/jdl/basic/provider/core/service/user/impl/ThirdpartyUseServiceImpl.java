package com.jdl.basic.provider.core.service.user.impl;

import com.jdl.basic.api.domain.user.JyThirdpartyUser;
import com.jdl.basic.api.domain.user.ReserveTaskDetailAgg;
import com.jdl.basic.api.domain.user.ReserveTaskDetailAggQuery;
import com.jdl.basic.provider.core.dao.user.JyThirdpartyUserDao;
import com.jdl.basic.provider.core.service.user.ThirdpartyUseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ThirdpartyUseServiceImpl implements ThirdpartyUseService {

    @Autowired
    JyThirdpartyUserDao jyThirdpartyUserDao;

    @Override
    public int batchInsert(List<JyThirdpartyUser> jyThirdpartyUserList) {
        return jyThirdpartyUserDao.batchInsert(jyThirdpartyUserList);
    }

    @Override
    public List<JyThirdpartyUser> queryJyThirdpartyUserByDetailBizId(String taskDetailBizId) {
        return jyThirdpartyUserDao.queryJyThirdpartyUserByDetailBizId(taskDetailBizId);
    }

    @Override
    public List<JyThirdpartyUser> queryJyThirdpartyUserByTaskBizId(String taskBizId) {
        return jyThirdpartyUserDao.queryJyThirdpartyUserByTaskBizId(taskBizId);
    }

    @Override
    public int add(JyThirdpartyUser jyThirdpartyUser) {
        return jyThirdpartyUserDao.insert(jyThirdpartyUser);
    }

    @Override
    public List<JyThirdpartyUser> queryJyThirdpartyUserByCondition(JyThirdpartyUser query) {
        return jyThirdpartyUserDao.queryJyThirdpartyUserByCondition(query);
    }

    @Override
    public int updateJyThirdpartyUserYn(JyThirdpartyUser query) {
        return jyThirdpartyUserDao.updateJyThirdpartyUserYn(query);
    }

    @Override
    public Long countTpUserByTaskDetail(JyThirdpartyUser jyThirdpartyUser) {
        return jyThirdpartyUserDao.countTpUserByTaskDetail(jyThirdpartyUser);
    }

    @Override
    public List<ReserveTaskDetailAgg> countTpUserGroupByNature(ReserveTaskDetailAggQuery query) {
        return jyThirdpartyUserDao.countTpUserGroupByNature(query);
    }
}
