package com.jdl.basic.ommon.exception;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/26 18:04
 * @Description: 业务异常类
 */
public class BuinessException extends RuntimeException{
    public BuinessException(String message) {
        super(message);
    }
}
