package com.itheima.controller;

import com.itheima.entity.Result;
import com.itheima.exception.MyException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/16
 * @description:
 */
// 拦截controller抛出的异常
@RestControllerAdvice
public class MyExceptionHandler {

    //@ExceptionHandler 来捕获异常
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException my){
        my.printStackTrace();
        return new Result(false, my.getMessage());
    }

    /**
     * 捕获较难失败异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        // 获取哪些属性没有验证通过,验证失败
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuffer sb = new StringBuffer();
        for (FieldError error : fieldErrors) {
            sb.append(error.getField() + ":" + error.getDefaultMessage() + ";");
        }
        return new Result(false, sb.toString());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception my){
        my.printStackTrace();
        return new Result(false, "发生未知异常，请联系管理员");
    }
}
