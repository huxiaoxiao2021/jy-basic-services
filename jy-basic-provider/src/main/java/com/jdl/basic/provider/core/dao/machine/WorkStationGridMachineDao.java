package com.jdl.basic.provider.core.dao.machine;

import com.jdl.basic.api.domain.machine.Machine;
import com.jdl.basic.api.domain.machine.WorkStationGridMachine;

import java.util.List;

/**
 * @author liwenji
 * @date 2022-10-27 9:11
 */
public interface WorkStationGridMachineDao {

    /**
     * 新增
     * @param machine
     * @return
     */
    int insert(WorkStationGridMachine machine);

    /**
     * 通过refGridKey删除数据
     * @param machine
     * @return
     */
    int deleteByRefGridKey(WorkStationGridMachine machine);

    /**
     * 获取网格工序绑定的自动化设备编码和类型
     * @param refGridKey
     * @return
     */
    List<Machine> getMachineListByRefGridKey(String refGridKey);
}
