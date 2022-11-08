package com.jdl.basic.provider.machine;

import com.jdl.basic.api.domain.machine.Machine;
import com.jdl.basic.api.domain.machine.WorkStationGridMachine;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.service.machine.WorkStationGridMachineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author liwenji
 * @date 2022-10-27 10:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkStationGridMachineServiceTest {
    
    @Autowired
    WorkStationGridMachineService workStationGridMachineService;
    
    @Test
    public void insert() {
        WorkStationGridMachine machine = new WorkStationGridMachine();
        machine.setMachineCode("BJMJQFJ6-FF-SORT-66003");
        machine.setMachineTypeCode("SORTING-MACHINE");
        machine.setCreateUser("liwenji3");
        machine.setRefGridKey("CDGX00000109002");
        workStationGridMachineService.insert(machine);
    }
    
    @Test
    public void deleteByRefGridKey() {
        WorkStationGridMachine machine = new WorkStationGridMachine();
        machine.setUpdateUser("liwenji3");
        machine.setRefGridKey("CDGX00000109002");
        workStationGridMachineService.deleteByRefGridKey(machine);
    }
    
    @Test
    public void getMachineListByRefGridKey() {
        List<Machine> machines = workStationGridMachineService.getMachineListByRefGridKey("CDGX00000109002");
        System.out.println(JsonHelper.toJSONString(machines));
    }
    
}
