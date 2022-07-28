package com.jdl.basic.api.response;

import com.jdl.basic.api.enums.ServiceResponseSubCodeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class JDResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 成功编码  **/
    public static final int CODE_SUCCESS = 1;

    /** 失败编码  **/
    public static final int CODE_FAIL = -1;

    /** 异常编码  **/
    public static final int CODE_EXCEPTION = 0;

    /** 警告编码  **/
    public static final int CODE_WARN = 2;

    /**部分失败**/
    public static final int HALF_FAIL = -2;


    public static final String MSG_SUCCESS ="请求成功!";

    public static final String MSG_FAIL ="请求失败!";

    public static final String MSG_EXCEPTION ="请求异常!";

    private T data;

    private int code;

    private String message;

    private String subCode;

    public static <T> JDResponse<T> writeSuccessMessage() {
        return writeResponseMessage(CODE_SUCCESS, ServiceResponseSubCodeEnum.SUCCESS.getSubCode(), ServiceResponseSubCodeEnum.SUCCESS.getValue(), null);
    }

    public static <T> JDResponse<T> writeSuccessMessage(T data) {
        return writeResponseMessage(CODE_SUCCESS, ServiceResponseSubCodeEnum.SUCCESS.getSubCode(), ServiceResponseSubCodeEnum.SUCCESS.getValue(), data);
    }

    public static <T> JDResponse<T> writeMessage(Integer code, String subCode, String msg) {
        return writeResponseMessage(code, subCode, msg, null);
    }

    public static <T> JDResponse<T> writeMessage(Integer code, String subCode, String msg, T data) {
        return writeResponseMessage(code, subCode, msg, data);
    }
    public static <T> JDResponse<T> writeFailMessage(String msg) {
        return writeResponseMessage(CODE_FAIL, null, msg, null);
    }

    public Boolean isSuccess() {
        return Integer.valueOf(CODE_SUCCESS).equals(this.code);
    }

    public static <T> JDResponse<T> writeResponseMessage(Integer code, String subCode, String message, T data) {
        JDResponse<T> result = new JDResponse<T>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        result.setSubCode(subCode);
        return result;
    }

    public static <T> JDResponse<T> writeExceptionMessage(String msg) {
        return JDResponse.writeMessage(CODE_EXCEPTION, null, msg);
    }

}
