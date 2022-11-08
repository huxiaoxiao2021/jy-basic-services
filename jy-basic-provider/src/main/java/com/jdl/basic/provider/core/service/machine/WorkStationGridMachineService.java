package com.jdl.basic.provider.core.service.machine;

import com.jdl.basic.api.domain.machine.Machine;
import com.jdl.basic.api.domain.machine.WorkStationGridMachine;

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
     * 通过ref_grid_key更新
     * @param machine
     * @return
     */
    Integer deleteByRefGridKey(WorkStationGridMachine machine);

    /**
     * 获取网格工序绑定的自动化设备编码和类型
     * @param refGridKey
     * @return
     */
    List<Machine> getMachineListByRefGridKey(String refGridKey);
}
