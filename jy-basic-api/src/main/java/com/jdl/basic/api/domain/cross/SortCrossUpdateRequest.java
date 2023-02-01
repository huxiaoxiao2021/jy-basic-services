package com.jdl.basic.api.domain.cross;

import lombok.Data;

import java.util.List;

/**
 * @author liwenji
 * @date 2022-11-09 15:14
 */
@Data
public class SortCrossUpdateRequest {
    
    private List<Long> ids;
    
    private Integer enableFlag;
}
