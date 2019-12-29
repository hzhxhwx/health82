package com.itheima.service;

import com.itheima.entity.Result;
import com.itheima.exception.MyException;
import com.itheima.pojo.Order;

import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/23
 * @description:
 */
public interface OrderService {
    /**
     * 提交预约服务
     * @param paramMap
     * @return
     */
    Result submit(Map<String,String> paramMap) throws MyException;

    /**
     * 查询预约信息
     * @param id
     * @return
     */
    Map<String,Object> findById(int id);
}
