package com.jdl.basic.provider.canal;

import java.io.Serializable;
import java.util.List;

/**
 * 行数据
 *
 * @author wangqing
 * @since 15-8-25 上午11:35
 */
public class CanalRow implements Serializable {

    private static final long serialVersionUID = 1L;

    private String eventType;//增删改 insert update delete
    private String tableName;//实际表名
    private String originTableName;//原始表名
    private String database;//数据库名
    private Long executeTime;//binlog里记录变更发生的时间戳
    private List<CanalColumn> columnList;//字段信息

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOriginTableName() {
        return originTableName;
    }

    public void setOriginTableName(String originTableName) {
        this.originTableName = originTableName;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public List<CanalColumn> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<CanalColumn> columnList) {
        this.columnList = columnList;
    }


    public void setExecuteTime(Long executeTime) {
        this.executeTime = executeTime;
    }


    public Long getExecuteTime() {
        return executeTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CanalRow{");
        sb.append("eventType='").append(eventType).append('\'');
        sb.append(", tableName='").append(tableName).append('\'');
        sb.append(", originTableName='").append(originTableName).append('\'');
        sb.append(", database='").append(database).append('\'');
        sb.append(", executeTime=").append(executeTime);
        sb.append(", columnList=").append(columnList);
        sb.append('}');
        return sb.toString();
    }

    public String toSimpleString(){
        final StringBuilder sb = new StringBuilder("CanalRow{");
        sb.append("eventType='").append(eventType).append('\'');
        sb.append(", tableName='").append(tableName).append('\'');
        sb.append(", originTableName='").append(originTableName).append('\'');
        sb.append(", database='").append(database).append('\'');
        sb.append(", executeTime=").append(executeTime);
        sb.append(", columnList=").append(columnList.size());
        sb.append('}');
        return sb.toString();
    }

    public boolean changed() {
        boolean changed = false;
        for (CanalColumn column : getColumnList()) {
            if (column.isUpdate()) {
                changed = true;
                break;
            }
        }
        return changed;
    }
}
