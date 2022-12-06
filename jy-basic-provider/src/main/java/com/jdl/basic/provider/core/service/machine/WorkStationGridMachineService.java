package com.jdl.basic.provider.core.service.machine;

import com.jdl.basic.api.domain.machine.Machine;
import com.jdl.basic.api.domain.machine.WorkStationGridMachine;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;

import java.util.HashMap;
import java.util.List;

/**
 * @author liwenji
 * @date 2022-10-27 9:45
 */
public interface WorkStationGridMachineService {

    /**
     * 新增
     * @param machine
     * @return
     */
    Integer insert(WorkStationGridMachine machine);

    /**
     * 批量新增
     * @param machine
     * @return
     */
    Boolean batchInsert(List<WorkStationGridMachine> machine);


    /**
     * 通过ref_grid_key更新
     * @param machine
     * @return
     */
    Boolean deleteByRefGridKey(WorkStationGridMachine machine);

    /**
     * 获取网格工序绑定的自动化设备编码和类型
     * @param grids
     * @return
     */
    HashMap<String,List<Machine>> getMachineListByRefGridKey(List<WorkStationGrid> grids);
}
