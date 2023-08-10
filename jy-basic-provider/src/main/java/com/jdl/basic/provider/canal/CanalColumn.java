package com.jdl.basic.provider.canal;

import java.io.Serializable;

/**
 * 字段信息
 *
 * @author wangqing
 * @since 15-8-25 下午1:57
 */
public class CanalColumn implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;//字段名
    private String value;//字段值
    private boolean update;//是否变更
    private int sqlType;//字段java中类型@see java.sql.Types
    private boolean isKey;//是否是主键
    private boolean isNull;//标识是否为空
    private int index;  //字段下标
    private int length;//对应数据对象原始长度


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


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CanalColumn{");
        sb.append("name='").append(name).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", update=").append(update);
        sb.append(", sqlType=").append(sqlType);
        sb.append(", isKey=").append(isKey);
        sb.append(", isNull=").append(isNull);
        sb.append(", index=").append(index);
        sb.append(", length=").append(length);
        sb.append('}');
        return sb.toString();
    }
}
