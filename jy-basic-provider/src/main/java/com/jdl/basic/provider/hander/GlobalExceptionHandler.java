package com.jdl.basic.provider.hander;

import com.jdl.basic.api.response.JDResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/29 14:28
 * @Description:
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BindException.class)
    public JDResponse validationExceptionHandler(BindException e) {
        JDResponse response = new JDResponse();
        BindingResult bindingResult = e.getBindingResult();
        String errorMesssage = "";
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + "!";
        }
        response.setCode(JDResponse.CODE_EXCEPTION);
        response.setMessage(errorMesssage);
        return response;
    }

}
