package com.jdl.basic.api.domain.cross;

import lombok.Data;

import java.util.List;

/**
 * @author liwenji
 * @date 2022-11-15 17:58
 */
@Data
public class TableTrolleyJsfResp {

    private Integer totalPage;

    private List<TableTrolleyJsfDto> tableTrolleyDtoJsfList;
    
}
