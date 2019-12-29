package com.itheima.exception;

/**
 * @user: Eric
 * @date: 2019/12/16
 * @description:
 * 自定义异常：场景：终止已知不符合业务逻辑操作的代码的继续执行
 */
public class MyException extends RuntimeException {
    public MyException(String message){
        super(message);
    }
}
