package com.jdl.basic.provider.utils;

import com.jd.dms.java.utils.core.annotation.EsColumn;
import com.jd.dms.java.utils.core.annotation.EsId;
import com.jdl.basic.common.utils.ByteUtil;
import com.jdl.basic.common.utils.DateUtil;
import com.jdl.basic.common.utils.MathUtil;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author : xumigen
 * @date : 2019/5/15
 */
public class EsBeanPropertyUtil {
    public static final String ES_PRIMARY_KEY = "_id";

    public static <T> Map<String,Object> objConvertMapFilterNull(T obj)throws IllegalArgumentException, IllegalAccessException{
        return objConvertMap(obj,Boolean.TRUE);
    }

    public static <T> Map<String,Object> objConvertMapNoFilterNull(T obj)throws IllegalArgumentException, IllegalAccessException{
        return objConvertMap(obj,Boolean.FALSE);
    }

    /**
     * bean 转换 map<filed,fieldValue>
     * @param obj 对象
     * @param isFilterNull 是否过滤null字段；true 过滤null字段，false 不过滤
     * @param <T> 泛型
     * @return map
     * @throws Exception 异常
     */
    private static <T> Map<String,Object> objConvertMap(T obj,boolean isFilterNull)throws IllegalArgumentException, IllegalAccessException{
        Map<String,Object> result = new HashMap<>();
        Field[] filelds = obj.getClass().getDeclaredFields();
        for(Field item : filelds){
            item.setAccessible(true);
            Object value = item.get(obj);
            if(isFilterNull && value == null){
                continue;
            }
            mappingFieldValue(result, item, value);
            EsId esId = item.getAnnotation(EsId.class);
            if(esId != null){
                result.put(ES_PRIMARY_KEY,value);
            }
        }
        return result;
    }

    private static void mappingFieldValue(Map<String, Object> result, Field item, Object value) {
        EsColumn esColumn = item.getAnnotation(EsColumn.class);
        if(esColumn != null){
            result.put(esColumn.fieldName(),value);
        }
    }

    public static <T> T mapConvertBean(Map<String,Object> objectMap, Class<T> clazz)throws InstantiationException,IllegalAccessException{
        if(objectMap == null){
            return null;
        }
        Field[] filelds = clazz.getDeclaredFields();
        T object = clazz.newInstance();
        for(Field item : filelds){
            EsColumn annotation = item.getAnnotation(EsColumn.class);
            item.setAccessible(true);
            if(annotation != null){
                String esField = annotation.fieldName();
                Object value = objectMap.get(esField);
                setField(item,value,object);
            }
        }
        return object;
    }

    private static <T> void setField(Field field,Object fieldValue,T object) throws IllegalAccessException{
        if(fieldValue == null){
            return;
        }
        Class clz = field.getType();
        String clzName = clz.getSimpleName();
        switch(clzName) {
            case "String":
                String strValue = String.valueOf(fieldValue);
                field.set(object, strValue);
                break;
            case "Integer":
                Integer intValue = MathUtil.objToInt(fieldValue);
                field.set(object, intValue);
                break;
            case "Double":
                Double doubleValue = MathUtil.objToDoubleOrZero(fieldValue);
                field.set(object, doubleValue);
                break;
            case "Float":
                Float floatValue = MathUtil.objToFloatOrZero(fieldValue);
                field.set(object, floatValue);
                break;
            case "Long":
                Long longValue = MathUtil.objToZeroLong(fieldValue);
                field.set(object, longValue);
                break;
            case "Date":
                Date dateValue;
                if(fieldValue instanceof Date){
                    dateValue = (Date)fieldValue;
                }else{
                    DateTimeFormat dateTimeFormat = field.getAnnotation(DateTimeFormat.class);
                    if(dateTimeFormat != null){
                        dateValue = DateUtil.parse(String.valueOf(fieldValue), dateTimeFormat.pattern());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dateValue);
                        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
                        dateValue = calendar.getTime();
                    }else{
                        dateValue = DateUtil.parse(String.valueOf(fieldValue), DateUtil.FORMAT_DATE_TIME);
                    }
                }
                field.set(object, dateValue);
                break;
            case "Byte":
                Byte byteValue = ByteUtil.objToByte(fieldValue);
                field.set(object, byteValue);
                break;
            default: throw new IllegalAccessException("不支持的类型"+clzName);
        }
    }

    //获得值为空的属性名
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set emptyNames = new HashSet();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return (String[]) emptyNames.toArray(result);
    }

}
