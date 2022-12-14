package com.jdl.basic.api.domain.machine;

import lombok.Data;

/**
 * @author liwenji
 * @date 2022-10-27 8:58
 */
@Data
public class Machine {

    /**
     * 自动化设备编码
     */
    private String machineCode;

    /**
     * 自动化设备类型编码        
     */
    private String machineTypeCode;
}
