package com.bob.tensquare.base.Controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理类
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public Result exception(Exception e){
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
