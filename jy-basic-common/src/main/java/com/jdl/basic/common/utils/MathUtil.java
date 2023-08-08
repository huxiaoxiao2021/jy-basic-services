package com.jdl.basic.common.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : xumigen
 * @date : 2019/5/15
 */
public class MathUtil {

    private static final Pattern PATTERN_BIGDECIMAL = Pattern.compile("^((\\+|-)?\\d+)(\\.\\d*)?");
    public static long objToZeroLong(Object value) {
        return value == null ? 0 : Long.parseLong(value.toString());
    }

    public static int objToInt(Object value) {
        if(value == null || "".equals(value)){
            return 0;
        }
        if(value instanceof String){
            return Integer.parseInt((String)value);
        }
        return Integer.parseInt(value.toString());
    }

    public static float objToFloatOrZero(Object value) {
        if(value == null || "".equals(value)){
            return 0;
        }
        if(value instanceof Float){
            return (float) value;
        }
        return Float.parseFloat(String.valueOf(value));
    }

    public static Float objToFloatOrNull(Object value) {
        if(value == null || "".equals(value)){
            return null;
        }
        if(value instanceof Float){
            return (Float)value;
        }
        if(!isBigDecimal((String)value)){
            return null;
        }
        return Float.valueOf(String.valueOf(value));
    }

    public static Float strToFloat(String value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        if(!isBigDecimal(value)){
            return null;
        }
        return Float.parseFloat(value);
    }

    public static double objToDoubleOrZero(Object value) {
        if(value == null || "".equals(value)){
            return 0;
        }
        if(value instanceof Double){
            return (Double)value;
        }
        return Double.valueOf((String)value);
    }

    public static Double objToDoubleOrNull(Object value) {
        if(value == null || "".equals(value)){
            return null;
        }
        if(value instanceof Double){
            return (Double)value;
        }
        if(!isBigDecimal((String)value)){
            return null;
        }
        return Double.valueOf((String)value);
    }

    public static Double strToDouble(String value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        if(!isBigDecimal(value)){
            return null;
        }
        return Double.parseDouble(value);
    }

    /**
     * 去掉小数部分取整，也就是正数取左边，负数取右边，相当于向原点靠近的方向取整\
     * 1.1->1   1.5->1   1.8->1   -1.1->-1   -1.5->-1   -1.8>-1
     * @param dividend 被除数
     * @param molecule 分子
     * @return 结果
     */
    public static Double divideDown(Double dividend, Integer molecule) {
        if(dividend == null || molecule == null){
            return null;
        }
        if(molecule == 0){
            return null;
        }

        BigDecimal bigDividend = new BigDecimal(dividend);
        BigDecimal bigMolecule = new BigDecimal(molecule);
        return bigDividend.divide(bigMolecule,2, RoundingMode.DOWN).doubleValue();
    }

    /**
     * 判断字符串是否为数值类型,包含整数和浮点数
     * eg:+1,+1.,-1,-1.,+1.0,-1.0,+1,0.0,001,009,001.0
     * @param numberStr 字符串
     * @return true 是数字，false不是
     */
    public static boolean isBigDecimal(String numberStr) {
        if(null==numberStr){
            return false;
        }
        Matcher matcher = PATTERN_BIGDECIMAL.matcher(numberStr);
        return matcher.matches();
    }

    public static void main(String args[]) {
        System.out.println(objToDoubleOrNull("222.5"));
        System.out.println(objToFloatOrZero(new Double("222.5")));

        Double a = 0.03;
        Double b = 0.01;
        Double c = 0.06;
        System.out.println(a + b);
        System.out.println(a - b);
        System.out.println(a + b + c);
        System.out.println(BigDecimal.valueOf(a).add(BigDecimal.valueOf(b)).add(BigDecimal.valueOf(c)).doubleValue());
    }
}
