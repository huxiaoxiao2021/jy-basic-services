package com.jdl.basic.rpc.exception;

/**
 * rpc 异常
 * @author wangdianzhuang
 * @date 2019/7/25
 */
public class JYBasicRpcException extends RuntimeException {

    public JYBasicRpcException() {
        super();
    }

    public JYBasicRpcException(String message) {
        super(message);
    }

    public JYBasicRpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public JYBasicRpcException(Throwable cause) {
        super(cause);
    }

    protected JYBasicRpcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
