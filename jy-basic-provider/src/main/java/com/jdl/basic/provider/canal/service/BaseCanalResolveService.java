package com.jdl.basic.provider.canal.service;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.provider.canal.ChangeOfColumn;
import com.jdl.basic.provider.canal.ChangeOfRow;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 处理binlog抽象类
 * Created by chenxin26 on 2017/8/31.
 */
public abstract class BaseCanalResolveService<T> implements CanalResolveService, CanalRowResolveService {
    private static final Logger logger = LoggerFactory.getLogger(BaseCanalResolveService.class);

    protected String tableName;

    public BaseCanalResolveService(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public ChangeOfRow formatCanalMessage(String jsonMessage) throws Exception {
        if(StringUtils.isEmpty(jsonMessage)){
            logger.error("【canal解析】表【{}】传入字符串为空",new Object[]{tableName});
            return null;
        }
        return JSON.parseObject(jsonMessage, ChangeOfRow.class);
    }

    public void processCanalEvent(String jsonMessage) throws Exception {
        ChangeOfRow row = formatCanalMessage(jsonMessage);
        if(row==null){
            logger.error("【canal解析】表【{}】反序列化失败，字符串:{}",new Object[]{tableName,jsonMessage});
            return ;
        }
        doService(row);
    }

    public void doService(ChangeOfRow row) throws Exception {
        if(StringUtils.isEmpty(row.getTableName())){
            logger.error("【canal解析】表【{}】非设定的表名，实际表名为空",new Object[]{tableName});
            return;
        }

        if((StringUtils.isNotEmpty(tableName) && !row.getTableName().contains(tableName))){
            logger.error("【canal解析】表【{}】非设定的表名，实际表名【{}】",new Object[]{tableName,row.getTableName()});
            return;
        }

        ChangeOfRow.EventType eventType = row.getEventType();
        if(eventType==null){
            logger.error("【canal解析】表【{}】,解析后数据库事件为空",new Object[]{tableName});
            return;
        }


        T before =  reductionDataBaseRow(row, ColumnSelector.before);
        T after =  reductionDataBaseRow(row, ColumnSelector.after);

        if (before == null && after==null) {
            logger.error("【canal解析】表【{}】,还原行记录失败, before 与 after 同时为空！row：{}",new Object[]{getTableName(),row.toString()});
            return;
        }

        switch (eventType){

            case INSERT:
                processInsertEvent(after);
                break;
            case UPDATE:
                processUpdateEvent(before,after);
                break;
            case DELETE:
                processDeleteEvent(after);
                break;
            default:
                processDefault(before,after);
                break;
        }

    }

    protected T reductionDataBaseRow(ChangeOfRow row,ColumnSelector column){
        if(row==null || column==null){
            return null;
        }
        T entity;
        if(column== ColumnSelector.before){
            entity = reductionDataBaseRow(row.getBeforeChangeOfColumns());
        }else {
            entity = reductionDataBaseRow(row.getAfterChangeOfColumns());
        }
        return entity;
    }

    protected abstract T reductionDataBaseRow(List<ChangeOfColumn> columns );

    protected abstract void processInsertEvent(T entity);

    protected abstract void processUpdateEvent(T before,T after);

    protected abstract void processDeleteEvent(T entity);

    protected abstract void processDefault(T before,T after);

    protected enum ColumnSelector {
        before,
        after
    }

    public String getTableName() {
        return tableName;
    }

}
