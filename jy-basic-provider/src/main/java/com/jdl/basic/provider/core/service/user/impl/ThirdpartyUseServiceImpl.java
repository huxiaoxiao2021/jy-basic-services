package com.jdl.basic.provider.core.service.user.impl;

import com.jdl.basic.api.domain.user.JyThirdpartyUser;
import com.jdl.basic.api.domain.user.ReserveTaskDetailAgg;
import com.jdl.basic.api.domain.user.ReserveTaskDetailAggQuery;
import com.jdl.basic.provider.core.dao.user.JyThirdpartyUserDao;
import com.jdl.basic.provider.core.service.user.ThirdpartyUseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ThirdpartyUseServiceImpl implements ThirdpartyUseService {

    @Autowired
    JyThirdpartyUserDao jyThirdpartyUserDao;

    @Override
    public int batchInsert(List<JyThirdpartyUser> jyThirdpartyUserList) {
        List<JyThirdpartyUser> list=new ArrayList<>();
        if(jyThirdpartyUserList !=null && jyThirdpartyUserList.size() >0){
            for (JyThirdpartyUser jyThirdpartyUser : jyThirdpartyUserList) {
                String userCode=jyThirdpartyUser.getUserCode();
                String tbid=jyThirdpartyUser.getTaskDetailBizId();
                JyThirdpartyUser query=new JyThirdpartyUser();
                query.setUserCode(userCode);
                query.setTaskDetailBizId(tbid);
                List<JyThirdpartyUser> list1=jyThirdpartyUserDao.queryForRepeat(query);
                list.addAll(list1);
            }
        }

        List<JyThirdpartyUser> afterRemoveDuplicate = jyThirdpartyUserList.stream()
                .filter(scheduleDetail -> {
                    boolean flag = true;
                    for (JyThirdpartyUser duplicate : list) {
                        if (scheduleDetail.getUserCode().equals(duplicate.getUserCode()) && scheduleDetail.getTaskDetailBizId().equals(duplicate.getTaskDetailBizId()) ) {
                            flag = false;
                        }
                    }
                    return flag;
                }).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(afterRemoveDuplicate)){
            return  1;
        }
        return jyThirdpartyUserDao.batchInsert(afterRemoveDuplicate);
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

    @Override
    public JyThirdpartyUser queryTpUserByUserCode(JyThirdpartyUser jyThirdpartyUser) {
        return jyThirdpartyUserDao.queryTpUserByUserCode(jyThirdpartyUser);
    }
}
