package com.jdl.basic.provider.dto;

import lombok.Data;

import java.util.List;

/**
 * @author liwenji
 * @date 2022-12-04 11:40
 */
@Data
public class SortCrossModifyDto {
    
    private String businessId;
    
    private String database;
    
    private String eventType;
    
    private String executeTime;
    
    private String tableName;
    
    private List<ColumnRecord> afterChangeOfColumns;
    
    private List<ColumnRecord> beforeChangeOfColumns;
}
