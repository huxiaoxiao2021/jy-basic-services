package com.jdl.basic.provider.canal;

import java.io.Serializable;

/**
 * Created by wangjinsi on 2016/2/17.
 */
public class ChangeOfColumn implements Serializable {

    private String name;//字段名
    private String value;//字段值
    private boolean update;//是否变更

    /**
     * 对应的JDBC类型
     * @see java.sql.Types
     */
    private int sqlType;
    private boolean isKey;//是否是主键
    private boolean isNull;//标识是否为空
    private int index;  //字段下标
    private int length;//对应数据对象原始长度
    private String mysqlType;

    @Override
    public String toString() {
        return "ChangeOfColumn{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", update=" + update +
                ", sqlType=" + sqlType +
                ", isKey=" + isKey +
                ", isNull=" + isNull +
                ", index=" + index +
                ", length=" + length +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public int getSqlType() {
        return sqlType;
    }

    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean key) {
        isKey = key;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getMysqlType() {
        return mysqlType;
    }

    public void setMysqlType(String mysqlType) {
        this.mysqlType = mysqlType;
    }
}
