package com.jdl.basic.provider.core.service.attBlackList.impl;


import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.attBlackList.AttendanceBlackList;
import com.jdl.basic.api.domain.attBlackList.AttendanceBlackListCondition;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.attBlackList.AttendanceBlackListDao;
import com.jdl.basic.provider.core.service.attBlackList.AttendanceBlackListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AttendanceBlackListServiceImpl implements AttendanceBlackListService {

    @Autowired
    private AttendanceBlackListDao attendanceBlackListDao;


    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".AttendanceBlackListServiceImpl.add", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public int add(AttendanceBlackList attendanceBlackList) {
        int inserted = attendanceBlackListDao.insert(attendanceBlackList);
        return  inserted;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".AttendanceBlackListServiceImpl.modify", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public int modify(AttendanceBlackList attendanceBlackList) {
        int inserted = attendanceBlackListDao.updateById(attendanceBlackList);
        return inserted;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".AttendanceBlackListServiceImpl.removeById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public int removeById(int id) {
        int deleted =attendanceBlackListDao.deleteById(id);
        return  deleted;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".AttendanceBlackListServiceImpl.queryByCondition", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public List<AttendanceBlackList> queryByCondition(AttendanceBlackListCondition query) {
        query.setOffset();
        return attendanceBlackListDao.queryByCondition(query);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".AttendanceBlackListServiceImpl.queryByUserCode", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public AttendanceBlackList queryByUserCode(String userCode) {
        return attendanceBlackListDao.queryByUserCode(userCode);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".AttendanceBlackListServiceImpl.countByCondition", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public int countByCondition(AttendanceBlackListCondition query) {
        return attendanceBlackListDao.countByCondition(query).intValue();
    }

    @Override
    public int checkCountCondition(AttendanceBlackListCondition query) {
       return attendanceBlackListDao.checkCountCondition(query).intValue();
    }
}
