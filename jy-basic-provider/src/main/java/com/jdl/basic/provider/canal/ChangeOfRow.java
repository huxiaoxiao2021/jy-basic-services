package com.jdl.basic.provider.canal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : xumigen
 * @date : 2019/5/16
 */
public class ChangeOfRow  implements Serializable {

    public enum EventType {
        INSERT, UPDATE, DELETE
    }

    private EventType eventType;//增删改 insert update delete
    private String tableName;//实际表名
    private String tableAlias;//原始表名
    private String database;//数据库名
    private Long executeTime;//binlog里记录变更发生的时间戳
    private List<ChangeOfColumn> beforeChangeOfColumns = new ArrayList<ChangeOfColumn>();//字段信息
    private List<ChangeOfColumn> afterChangeOfColumns = new ArrayList<ChangeOfColumn>();//字段信息
    private String businessId;

    @Override
    public String toString() {
        return "ChangeOfRow{" +
                "eventType=" + eventType +
                ", tableName='" + tableName + '\'' +
                ", tableAlias='" + tableAlias + '\'' +
                ", database='" + database + '\'' +
                ", executeTime=" + (executeTime == null ? "" : new Date(executeTime).toLocaleString()) +
                ", beforeChangeOfColumns=" + beforeChangeOfColumns +
                ", afterChangeOfColumns=" + afterChangeOfColumns +
                ", businessId=" + businessId +
                '}';
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Long executeTime) {
        this.executeTime = executeTime;
    }

    public List<ChangeOfColumn> getBeforeChangeOfColumns() {
        return beforeChangeOfColumns;
    }

    public void setBeforeChangeOfColumns(List<ChangeOfColumn> beforeChangeOfColumns) {
        this.beforeChangeOfColumns = beforeChangeOfColumns;
    }

    public List<ChangeOfColumn> getAfterChangeOfColumns() {
        return afterChangeOfColumns;
    }

    public void setAfterChangeOfColumns(List<ChangeOfColumn> afterChangeOfColumns) {
        this.afterChangeOfColumns = afterChangeOfColumns;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}