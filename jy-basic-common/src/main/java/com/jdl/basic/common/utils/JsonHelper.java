package com.jdl.basic.common.utils;


import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;


public class JsonHelper {

    private final static Logger log = LoggerFactory.getLogger(JsonHelper.class);
    private static final String DATE_FORMAT_MS = "yyyy-MM-dd HH:mm:ss.SSS";
    ;



    /**
     * jacjson序列化器-日期格式带毫秒
     */
    private static ObjectMapper objectMapperMs = null;




    static {
        //毫秒格式的初始化
        objectMapperMs = new ObjectMapper();
        //序列化对象时：空对象不会出现在json中
        objectMapperMs.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
        //反序列化时：忽略未知属性（默认为true，会抛出异常）
        objectMapperMs.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapperMs.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapperMs.configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_MS);
    }



    /**
     * 支持日期格式为yyyy-MM-dd HH:mm:ss.SSS的序列化方法
     *
     * @param obj
     * @return
     */
    public static String toJsonMs(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return objectMapperMs.writeValueAsString(obj);
        } catch (Exception e) {
            JsonHelper.log.error("序列化JSON发生异常", e);
        }
        return null;
    }

    /**
     * 支持日期格式为yyyy-MM-dd HH:mm:ss.SSS的反序列化方法
     *
     * @param json
     * @param responseType
     * @return
     */

}