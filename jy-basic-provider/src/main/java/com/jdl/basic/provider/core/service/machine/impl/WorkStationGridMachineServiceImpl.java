package com.jdl.basic.provider.core.service.machine.impl;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.machine.Machine;
import com.jdl.basic.api.domain.machine.WorkStationGridMachine;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.machine.WorkStationGridMachineDao;
import com.jdl.basic.provider.core.service.machine.WorkStationGridMachineService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liwenji
 * @date 2022-10-27 9:46
 */
@Service
public class WorkStationGridMachineServiceImpl implements WorkStationGridMachineService {
    
    @Autowired
    private WorkStationGridMachineDao workStationGridMachineDao;
    
    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridMachineServiceImpl.insert", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Integer insert(WorkStationGridMachine machine) {
        return workStationGridMachineDao.insert(machine);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridMachineServiceImpl.deleteByRefGridKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Integer deleteByRefGridKey(WorkStationGridMachine machine) {
        return workStationGridMachineDao.deleteByRefGridKey(machine);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridMachineServiceImpl.getMachineListByRefGridKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public List<Machine> getMachineListByRefGridKey(String refGridKey) {
        if (StringUtils.isEmpty(refGridKey)){
            return null;
        }
        return workStationGridMachineDao.getMachineListByRefGridKey(refGridKey);
    }

}
