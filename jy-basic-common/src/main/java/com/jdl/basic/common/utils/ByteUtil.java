package com.jdl.basic.common.utils;

/**
 * @author : xumigen
 * @date : 2019/5/15
 */
public class ByteUtil {

    public static Byte objToByte(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Byte){
            return (Byte)value;
        }
        return null;
    }

    public static void main(String args[]) {
        Integer dd = 1;
        System.out.println(objToByte(dd));
    }
}
