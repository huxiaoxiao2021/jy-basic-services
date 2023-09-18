package com.jdl.basic.api.domain.boxFlow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectBoxRouteCheckDto {
    private Integer startSiteId;
    private  String startSiteName;
    private Integer endSiteId;
    private String endSiteName;
    private Integer boxReceiveId;
    private String boxReceiveName;
    private String problemTypeDesc;
}
