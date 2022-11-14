package com.jdl.basic.provider.core.service.machine.impl;

import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.machine.Machine;
import com.jdl.basic.api.domain.machine.WorkStationGridMachine;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.machine.WorkStationGridMachineDao;
import com.jdl.basic.provider.core.service.machine.WorkStationGridMachineService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    public Boolean batchInsert(List<WorkStationGridMachine> machines) {
        return workStationGridMachineDao.batchInsert(machines) > 0;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridMachineServiceImpl.deleteByRefGridKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Boolean deleteByRefGridKey(WorkStationGridMachine machine) {
        return workStationGridMachineDao.deleteByRefGridKey(machine) > 0;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".WorkStationGridMachineServiceImpl.getMachineListByRefGridKey", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public HashMap<String, List<Machine>> getMachineListByRefGridKey(List<WorkStationGrid> grids) {
        if (CollectionUtils.isEmpty(grids)) {
            return null;
        }
        HashMap<String, List<Machine>> result = new HashMap<>();
        for (WorkStationGrid grid : grids) {
            result.put(grid.getBusinessKey(), new ArrayList<>());
        }
        List<WorkStationGridMachine> machineList = workStationGridMachineDao.getMachineListByRefGridKey(grids);
        for (WorkStationGridMachine gridMachine : machineList) {
            List<Machine> list = result.get(gridMachine.getRefGridKey());
            Machine machine = new Machine();
            machine.setMachineCode(gridMachine.getMachineCode());
            machine.setMachineTypeCode(gridMachine.getMachineTypeCode());
            list.add(machine);
        }
        return result;
    }

}
