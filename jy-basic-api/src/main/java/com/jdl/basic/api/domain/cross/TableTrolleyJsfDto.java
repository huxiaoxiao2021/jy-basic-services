package com.jdl.basic.api.domain.cross;

import lombok.Data;

import java.io.Serializable;

@Data
public class TableTrolleyJsfDto implements Serializable {
    private Integer endSiteId;
    private String endSiteName;
    /**
     * 滑道编号
     */
    private String crossCode;
    /**
     * 笼车编号
     */
    private String tableTrolleyCode;
    
}
